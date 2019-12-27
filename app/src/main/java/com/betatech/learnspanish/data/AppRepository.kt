package com.betatech.learnspanish.data

import androidx.lifecycle.LiveData
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.DbHelper
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Quiz


class AppRepository private constructor(
    private val dbHelper: DbHelper
) : Repository {

    companion object {

        // For Singleton instantiation
        @Volatile private var INSTANCE: AppRepository? = null

        fun getInstance(dbHelper: DbHelper) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: AppRepository(dbHelper).also { INSTANCE = it }
            }
    }

    override fun insertExercises(exerciseList: List<Exercise>): List<Long> =
        dbHelper.insertExercises(exerciseList)

    override fun getExercises(): LiveData<List<Exercise>> =
        dbHelper.getExercises()

    override fun insertLessons(lessonList: List<Lesson>): List<Long> =
        dbHelper.insertLessons(lessonList)

    override fun getLessonsBy(exerciseId: String): LiveData<List<Lesson>> =
        dbHelper.getLessonsBy(exerciseId)

    override fun insertQuizzes(quizList: List<Quiz>): List<Long> =
        dbHelper.insertQuizzes(quizList)

    override fun getQuizzesBy(exerciseId: String): LiveData<List<Quiz>> =
        dbHelper.getQuizzesBy(exerciseId)
}