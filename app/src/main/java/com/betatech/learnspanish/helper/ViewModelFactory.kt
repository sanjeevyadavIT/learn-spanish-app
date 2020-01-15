package com.betatech.learnspanish.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.ui.exercises.ExercisesViewModel
import com.betatech.learnspanish.ui.lessons.LessonsViewModel
import com.betatech.learnspanish.ui.quiz.QuizViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: Repository,
    private val exerciseId: String?
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(ExercisesViewModel::class.java) ->
                    ExercisesViewModel(repository)
                isAssignableFrom(LessonsViewModel::class.java) ->
                    LessonsViewModel(repository, exerciseId)
                isAssignableFrom(QuizViewModel::class.java) ->
                    QuizViewModel(repository, exerciseId)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}