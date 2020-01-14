package com.betatech.learnspanish.ui.exercises

import androidx.lifecycle.ViewModel
import com.betatech.learnspanish.data.Repository

/**
 * ViewModel for the exercise list screen
 */
class ExercisesViewModel(
    private val repository: Repository
) : ViewModel() {

    val exercises = repository.getExercises()
}