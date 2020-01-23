package com.betatech.learnspanish.data

import androidx.lifecycle.LiveData
import com.betatech.learnspanish.data.local.db.DbHelper
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Question
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AppRepository (
    private val dbHelper: DbHelper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    override suspend fun insertExercises(exercises: List<Exercise>): List<Long> = withContext(ioDispatcher){
        dbHelper.insertExercises(exercises)
    }

    override fun getExercises(): LiveData<List<Exercise>> =
        dbHelper.getExercises()

    override suspend fun insertLessons(lessons: List<Lesson>): List<Long> = withContext(ioDispatcher) {
        dbHelper.insertLessons(lessons)
    }

    override fun getLessonsByExerciseId(exerciseId: String): LiveData<List<Lesson>> =
        dbHelper.getLessonsByExerciseId(exerciseId)

    override suspend fun insertQuestions(questions: List<Question>): List<Long> =
        withContext(ioDispatcher) {
            dbHelper.insertQuestions(questions)
    }

    override fun getQuestionsByExerciseId(exerciseId: String): LiveData<List<Question>> =
        dbHelper.getQuestionsByExerciseId(exerciseId)
}