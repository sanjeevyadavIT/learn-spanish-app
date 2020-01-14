package com.betatech.learnspanish.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.ui.exercises.ExercisesViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(ExercisesViewModel::class.java) ->
                    ExercisesViewModel(repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}