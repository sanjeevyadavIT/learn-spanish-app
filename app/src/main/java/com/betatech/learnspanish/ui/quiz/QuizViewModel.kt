package com.betatech.learnspanish.ui.quiz

import androidx.lifecycle.ViewModel
import com.betatech.learnspanish.data.Repository

class QuizViewModel(
    repository: Repository,
    exerciseId: String?
) : ViewModel() {

    val quizzess = repository.getQuizzesByExerciseId(exerciseId ?: "")
}