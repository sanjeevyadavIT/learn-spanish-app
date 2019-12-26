package com.betatech.learnspanish.data.local.db

import androidx.lifecycle.LiveData
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Quiz

/**
 * All database related function
 */
interface DbHelper{
    /**
     * Save all exercises in database
     *
     * @param exerciseList list of exercise which need to be saved
     * @return list of long which represent row id
     */
    fun insertExercises(exerciseList: List<Exercise>): List<Long>

    /**
     * Get all [Exercise]
     *
     * @return all exercises wrapped in LiveData
     */
    fun getExercises(): LiveData<List<Exercise>>

    /**
     * Save all lessons in database
     *
     * @param lessonList list of lesson which need to be saved
     * @return list of long which represent row id
     */
    fun insertLessons(lessonList: List<Lesson>): List<Long>

    /**
     * Get [Lesson] list which belong to a particular exercise
     *
     * @param exerciseId id of the exercise whose lessons need to find
     * @return lesson list wrapped in LiveData
     */
    fun getLessonsBy(exerciseId: String): LiveData<List<Lesson>>

    /**
     * Save all quizzes in database
     *
     * @param quizList list of quiz which need to be saved
     * @return list of long which represent row id
     */
    fun insertQuizzes(quizList: List<Quiz>): List<Long>

    /**
     * Get [Quiz] list which belong to a particular exercise
     *
     * @param exerciseId id of the exercise whose quizzes need to find
     * @return quiz list wrapped in LiveData
     */
    fun getQuizzesBy(exerciseId: String): LiveData<List<Quiz>>
}