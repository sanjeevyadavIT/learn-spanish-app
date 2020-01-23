package com.betatech.learnspanish.ui.quiz

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.betatech.learnspanish.R
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.data.model.db.Question
import java.util.*

/**
 * ViewModel for the quiz screen
 */
class QuizViewModel(
    repository: Repository,
    exerciseId: String?
) : ViewModel() {

    // Helper class to change UI depending upon the question state
    enum class QuizState {
        NOT_ANSWERED, // Question is being displayed, and user hasn't answered
        CORRECT_ANSWER, // User Answer is correct, show correct answer banner
        WRONG_ANSWER, // User answer is incorrect, show incorrect answer message along with correct answer
        FAIL, // When all life are lost, quiz will end
        COMPLETE // All question has been answered
    }

    val questions = repository.getQuestionsByExerciseId(exerciseId ?: "")

    private val _currentQuestionIndex = MutableLiveData(0)
    private val _numberOfQuestionAnswered = MutableLiveData(0)

    val question: LiveData<Question> =
        Transformations.map(_currentQuestionIndex, ::getCurrentQuestion)

    val progress: LiveData<Int> =
        Transformations.map(_numberOfQuestionAnswered, ::calculateProgress)

    private val _userAnswer = MutableLiveData("")
    val userAnswer: LiveData<String> = _userAnswer

    /**
     * User has 3 life, he/she can continue game till 3 incorrect answer
     * On forth incorrect answer, quiz will stop and have to played from
     * start.
     */
    private val _lifeLeft = MutableLiveData(3)
    val lifeLeft: LiveData<Int> = _lifeLeft

    private val _quizState = MutableLiveData<QuizState>(QuizState.NOT_ANSWERED)
    val quizState: LiveData<QuizState> = _quizState

    /**
     * Used to render HTML content in TextView that
     * display Question on screen.
     */
    fun formatHtml(string: String?): CharSequence = when {
        string == null -> ""
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> Html.fromHtml(
            string,
            Html.FROM_HTML_MODE_COMPACT
        )
        else -> Html.fromHtml(string)
    }


    /**
     * If question type == "mcq", then radio buttons will be shown,
     * to choose option from multiple options
     */
    fun onRadioButtonSelected(radioGroup: RadioGroup, id: Int) {
        when (id) {
            R.id.rb_option_one -> _userAnswer.value = question.value?.options?.data?.get(0) ?: ""
            R.id.rb_option_two -> _userAnswer.value = question.value?.options?.data?.get(1) ?: ""
            R.id.rb_option_three -> _userAnswer.value = question.value?.options?.data?.get(2) ?: ""
            R.id.rb_option_four -> _userAnswer.value = question.value?.options?.data?.get(3) ?: ""
        }
    }

    /**
     * If question type == "type", then edit text will be shown
     * to enter answer
     */
    fun onAnswerTyped(string: CharSequence, start: Int, before: Int, count: Int) {
        _userAnswer.value = string.toString()
    }

    // Click listener attached to Button(R.id.btn_check) in [fragment_quiz.xml]
    fun checkAnswer(view: View) {
        if (quizState.value == QuizState.NOT_ANSWERED) {
            if (_userAnswer.value ?: "" == "") return

            _numberOfQuestionAnswered.value = _numberOfQuestionAnswered.value?.plus(1)

            if (checkUserEnteredAnswer()) {
                handleCorrectAnswerState()
            } else {
                handleWrongAnswerState()
            }

        } else {
            prepareNextQuestion()
        }

    }

    /**
     * TODO(1): Remove multiple spaces between user entered answer, if there
     * TODO(2): Provide multiple possible answers to check against user entered answer
     *          example: user can enter "You cannot" or "You can't"
     *          example: user can forget a semicolon, punctuation or question mark
     */
    private fun checkUserEnteredAnswer(): Boolean {
        return _userAnswer.value?.toLowerCase(Locale.US) == question.value?.correctAnswer?.toLowerCase(
            Locale.US
        ) ?: "-1"
    }

    fun setupFirstQuestion() {
        if (question.value == null) {
            _currentQuestionIndex.value = 0
        }
    }

    /**
     * Calculate progress depending upon the number of question
     * answered.
     *
     * @return value between 0 to 100
     */
    private fun calculateProgress(questionIndex: Int): Int = when {
        questions.value?.size ?: 0 == 0 -> 0
        else -> (questionIndex * 100) / (questions.value?.size ?: 1)
    }

    private fun getCurrentQuestion(questionIndex: Int): Question? = when {
        questions.value?.size ?: 0 == 0 -> null
        else -> questions.value!![questionIndex]
    }

    /**
     * If user enter incorrect answer,
     * decrease life by one
     */
    private fun handleWrongAnswerState() {
        _quizState.value = QuizState.WRONG_ANSWER
        _lifeLeft.value = _lifeLeft.value?.minus(1)
    }

    private fun handleCorrectAnswerState() {
        _quizState.value = QuizState.CORRECT_ANSWER
    }

    /**
     * Check whether all questions has been answered or
     * all life has lost, in that case change [QuizState]
     * accordingly
     */
    private fun prepareNextQuestion() {
        if (_numberOfQuestionAnswered.value == questions.value?.size) {
            _quizState.value = QuizState.COMPLETE
            return
        }

        if (_lifeLeft.value == 0) {
            _quizState.value = QuizState.FAIL
            return
        }
        // Continue with next question
        showNextQuestion()
    }

    /**
     * NOTE: [_quizState] value be reset to first [QuizState.NOT_ANSWERED]
     * so that RadioGroup can clear check, and after that
     * [_currentQuestionIndex] should be increased
     */
    private fun showNextQuestion() {
        _quizState.value = QuizState.NOT_ANSWERED
        _userAnswer.value = ""
        _currentQuestionIndex.value = _currentQuestionIndex.value?.plus(1)
    }


}