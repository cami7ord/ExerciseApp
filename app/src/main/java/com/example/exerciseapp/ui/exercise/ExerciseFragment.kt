package com.example.exerciseapp.ui.exercise

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.example.exerciseapp.R
import com.example.exerciseapp.databinding.FragmentExerciseBinding
import com.example.exerciseapp.domain.model.Exercise
import com.example.exerciseapp.ui.viewmodel.ExerciseViewModel
import com.example.exerciseapp.ui.viewmodel.ExerciseViewModel.OneTimeEvent.FinishRoutine
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ExerciseFragment : Fragment(R.layout.fragment_exercise) {

    private lateinit var binding: FragmentExerciseBinding

    private val parentFragmentViewModel by lazy {
        requireParentFragment().getViewModel<ExerciseViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExerciseBinding.bind(view)

        binding.btnCancel.setOnClickListener {
            parentFragmentViewModel.finishRoutine()
        }

        binding.imageFavorite.setOnClickListener {
            parentFragmentViewModel.toggleFavoriteFeaturedExercise()
        }

        lifecycleScope.launchWhenStarted {
            parentFragmentViewModel.loadRoutine()
        }

        //One time events
        lifecycleScope.launchWhenStarted {
            parentFragmentViewModel.eventFlow.collect { event ->
                when(event) {
                    is FinishRoutine -> {
                        findNavController().navigateUp()
                    }
                }
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        parentFragmentViewModel.exercise.observe(viewLifecycleOwner) { exercise ->
            updateExerciseUI(exercise)
        }
    }

    private fun updateExerciseUI(exercise: Exercise?) {
        exercise?.let {
            (activity as AppCompatActivity).supportActionBar?.title = exercise.name

            val imgResource =
                if (exercise.isFavorite) R.drawable.ic_baseline_star_24
                else R.drawable.ic_baseline_star_outline_24

            binding.imageFavorite.setImageResource(imgResource)

            Glide
                .with(this)
                .load(exercise.imageUrl)
                .transition(GenericTransitionOptions.with(R.anim.nav_default_enter_anim))
                .into(binding.imageCover)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}
