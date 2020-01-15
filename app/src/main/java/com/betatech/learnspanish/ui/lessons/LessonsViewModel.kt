package com.betatech.learnspanish.ui.lessons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.betatech.learnspanish.data.Repository
import com.betatech.learnspanish.data.model.db.Lesson

class LessonsViewModel(
    private val repository: Repository,
    private val exerciseId: String?
) : ViewModel() {

    val lessons = repository.getLessonsByExerciseId(exerciseId ?: "")

    private var _lesson = MutableLiveData<Lesson>()
    val lesson: LiveData<Lesson> = _lesson
    private var currentLessonIndex = -1

    private var _isPreviousButtonVisible = MutableLiveData<Boolean>(false)
    var isPreviousButtonVisible: LiveData<Boolean> = _isPreviousButtonVisible

    private var _isNextButtonVisible = MutableLiveData<Boolean>(false)
    var isNextButtonVisible: LiveData<Boolean> = _isNextButtonVisible

    private var _isQuizButtonEnabled = MutableLiveData<Boolean>(false)
    var isQuizButtonEnabled: LiveData<Boolean> = _isQuizButtonEnabled

    /**
     * Once, lessons for particular exercise is
     * fetched from DB, set first lesson for viewing
     *
     * This code should only run once in fragment lifecycle
     */
    fun setupLesson() {
        if (_lesson.value == null) {
            when (lessons.value?.size ?: 0) {
                0 -> {
                    // no lessons available
                    //TODO: show text on screen
                }
                1 -> {
                    _lesson.value = lessons.value?.get(0)
                    currentLessonIndex = 0
                    _isQuizButtonEnabled.value = true
                }
                else -> {
                    _lesson.value = lessons.value?.get(0)
                    currentLessonIndex = 0
                    _isNextButtonVisible.value = true
                }
            }
        }
    }

    fun nextLesson() {
        if (currentLessonIndex < ((lessons.value?.size ?: 0) - 1)) {
            currentLessonIndex++
            _lesson.value = lessons.value?.get(currentLessonIndex)
            _isPreviousButtonVisible.value = true
            if (currentLessonIndex == ((lessons.value?.size ?: -1) - 1)) {
                _isNextButtonVisible.value = false
                _isQuizButtonEnabled.value = true
            }
        }
    }

    fun previousLesson() {
        if (currentLessonIndex > 0) {
            currentLessonIndex--
            _lesson.value = lessons.value?.get(currentLessonIndex)
            _isNextButtonVisible.value = true
            if (currentLessonIndex == 0) {
                _isPreviousButtonVisible.value = false
            }
        }
    }
}