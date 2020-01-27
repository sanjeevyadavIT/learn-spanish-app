package com.betatech.learnspanish.ui.quiz.result

import androidx.lifecycle.ViewModel
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.ui.quiz.QuizViewModel

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

    companion object {
        const val XP_PER_QUIZ = 10
    }

    val xp = XP_PER_QUIZ - (QuizViewModel.LIFELINE_PER_QUIZ - lifeLeft)
    val totalXP = repository.getXp() + if (quizResult == QuizResult.SUCCESS) xp else 0

    init {
        if (quizResult == QuizResult.SUCCESS) {
            repository.addXp(xp)
        }

    }
}