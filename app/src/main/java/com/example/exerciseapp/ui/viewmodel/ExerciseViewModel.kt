package com.example.exerciseapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exerciseapp.domain.model.Exercise
import com.example.exerciseapp.domain.repository.ExerciseRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ExerciseViewModel (
    private val repository: ExerciseRepository
) : ViewModel() {

    val exercises = MutableLiveData<List<Exercise>>().apply { value = emptyList() }
    val exercise = MutableLiveData<Exercise?>()

    private val eventChannel = Channel<OneTimeEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun loadExercises() {
        viewModelScope.launch {
            repository.getExercises().collect { items ->
                exercises.postValue(items)
            }
        }
    }

    fun toggleFavoriteExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.setFavoriteExercise(exercise.id, !exercise.isFavorite)
        }
    }

    suspend fun loadRoutine() {
        repository.getExercises().collect { items ->
            items.forEach {
                exercise.postValue(it)
                delay(5000L)
            }
            eventChannel.send(OneTimeEvent.FinishRoutine)
        }
    }

    fun finishRoutine() = viewModelScope.launch {
        eventChannel.send(OneTimeEvent.FinishRoutine)
    }

    fun toggleFavoriteFeaturedExercise() {
        viewModelScope.launch {
            val featuredExercise = exercise.value
            featuredExercise?.let {
                repository.setFavoriteExercise(featuredExercise.id, !featuredExercise.isFavorite)
                val updated = repository.getExerciseById(featuredExercise.id)
                exercise.postValue(updated)
            }
        }
    }

    sealed class OneTimeEvent {
        object FinishRoutine: OneTimeEvent()
    }
}
