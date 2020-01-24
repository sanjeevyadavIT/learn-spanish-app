package com.betatech.learnspanish.helper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.ui.quiz.result.ResultViewModel

@Suppress("UNCHECKED_CAST")
class ResultViewModelFactory(
    private val repository: Repository,
    private val exerciseId: String?,
    private val quizResult: ResultViewModel.QuizResult,
    private val lifeLeft: Int
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(ResultViewModel::class.java) ->
                    ResultViewModel(
                        repository = repository,
                        exerciseId = exerciseId,
                        quizResult = quizResult,
                        lifeLeft = lifeLeft
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}