package com.betatech.learnspanish.data.local.db

import androidx.lifecycle.LiveData
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Question

/**
 * All database related function
 */
interface DbHelper{
    /**
     * Save all exercises in database
     *
     * @param exercises list of exercise which need to be saved
     * @return list containing row IDs of rows inserted
     */
    suspend fun insertExercises(exercises: List<Exercise>): List<Long>

    /**
     * Get all [Exercise]
     *
     * @return list of exercise wrapped in LiveData
     */
    fun getExercises(): LiveData<List<Exercise>>

    /**
     * Save all lessons in database
     *
     * @param lessons list of lesson which need to be saved
     * @return list containing row IDs of rows inserted
     */
    suspend fun insertLessons(lessons: List<Lesson>): List<Long>

    /**
     * Get [Lesson] list which belong to a particular exercise
     *
     * @param exerciseId id of the exercise whose lessons need to find
     * @return lesson list wrapped in LiveData
     */
    fun getLessonsByExerciseId(exerciseId: String): LiveData<List<Lesson>>

    /**
     * Save all questions in database
     *
     * @param questions list of questions for quizzes which need to be saved
     * @return list containing row IDs of rows inserted
     */
    suspend fun insertQuestions(questions: List<Question>): List<Long>

    /**
     * Get [Question] list which belong to a particular exercise/quiz
     *
     * @param exerciseId id of the exercise whose questions need to find
     * @return list of questions wrapped in LiveData
     */
    fun getQuestionsByExerciseId(exerciseId: String): LiveData<List<Question>>
}