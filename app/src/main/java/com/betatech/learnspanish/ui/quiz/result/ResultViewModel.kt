package com.betatech.learnspanish.ui.quiz.result

import androidx.lifecycle.ViewModel
import com.betatech.learnspanish.data.Repository

class ResultViewModel(
    repository: Repository,
    exerciseId: String?,
    val quizResult: QuizResult,
    lifeLeft: Int
) : ViewModel() {

    enum class QuizResult {
        SUCCESS, // Quiz was completed, show streaks and scores
        FAILED // Quiz wasn't completed, show failed message
    }
}