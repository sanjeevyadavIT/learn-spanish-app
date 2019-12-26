package com.betatech.learnspanish.data.local.db

import androidx.lifecycle.LiveData
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Quiz

/**
 * Concrete implementation of a [DbHelper].
 */
class AppDbHelper(
    private val appDatabase: AppDatabase
) : DbHelper {
    override fun insertExercises(exerciseList: List<Exercise>): List<Long> =
        appDatabase.exerciseDao().addAll(exerciseList)

    override fun getExercises(): LiveData<List<Exercise>> =
        appDatabase.exerciseDao().getAll()

    override fun insertLessons(lessonList: List<Lesson>): List<Long> =
        appDatabase.lessonDao().addAll(lessonList)

    override fun getLessonsBy(exerciseId: String): LiveData<List<Lesson>> =
        appDatabase.lessonDao().getLessonsBy(exerciseId)

    override fun insertQuizzes(quizList: List<Quiz>): List<Long> =
        appDatabase.quizDao().addAll(quizList)

    override fun getQuizzesBy(exerciseId: String): LiveData<List<Quiz>> =
        appDatabase.quizDao().getQuizzesBy(exerciseId)

}