package com.example.exerciseapp.data.network.dto

import com.example.exerciseapp.domain.model.Exercise
import com.fasterxml.jackson.annotation.JsonProperty

data class ExerciseDto(
    val id: Long,
    val name: String,
    @JsonProperty("cover_image_url")
    val image: String,
    @JsonProperty("video_url")
    val video: String,
)

fun ExerciseDto.toModel(): Exercise {
    return Exercise(
        id = id,
        name = name,
        imageUrl = image,
    )
}
