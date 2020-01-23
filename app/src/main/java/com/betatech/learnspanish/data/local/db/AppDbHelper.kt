package com.betatech.learnspanish.data.local.db

import androidx.lifecycle.LiveData
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Question

/**
 * Concrete implementation of a [DbHelper].
 */
class AppDbHelper private constructor(
    private val appDatabase: AppDatabase
) : DbHelper {

    companion object {

        // For Singleton instantiation
        @Volatile
        private var INSTANCE: AppDbHelper? = null

        fun getInstance(appDatabase: AppDatabase) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppDbHelper(appDatabase).also { INSTANCE = it }
            }
    }

    override suspend fun insertExercises(exercises: List<Exercise>): List<Long> =
        appDatabase.exerciseDao().insertAll(exercises)

    override fun getExercises(): LiveData<List<Exercise>> =
        appDatabase.exerciseDao().getExercises()


    override suspend fun insertLessons(lessons: List<Lesson>): List<Long> =
        appDatabase.lessonDao().insertAll(lessons)


    override fun getLessonsByExerciseId(exerciseId: String): LiveData<List<Lesson>> =
        appDatabase.lessonDao().getLessonsByExerciseId(exerciseId)

    override suspend fun insertQuestions(questions: List<Question>): List<Long> =
        appDatabase.questionDao().insertAll(questions)

    override fun getQuestionsByExerciseId(exerciseId: String): LiveData<List<Question>> =
        appDatabase.questionDao().getQuestionsByExerciseId(exerciseId)

}