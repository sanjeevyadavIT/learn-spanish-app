package com.betatech.learnspanish.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.dao.LessonDao
import com.betatech.learnspanish.data.local.db.dao.QuizDao
import com.betatech.learnspanish.util.LiveDataTestUtil
import com.betatech.learnspanish.util.TestUtil
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuizDaoTest {
    private lateinit var quizDao: QuizDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        quizDao = db.quizDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    private fun saveExerciseAndQuizInDb(): Int {
        val exercises = TestUtil.createExercises(false)
        val quizzes = TestUtil.createQuizzes()

        db.exerciseDao().addAll(exercises)
        val quizRowIds = quizDao.addAll(quizzes)

        assertThat(quizRowIds.size, equalTo(quizzes.size))
        return quizRowIds.size
    }

    @Test
    fun saveLessonAndRead() {
        // Setup
        val totalQuizSaved = saveExerciseAndQuizInDb()

        // Exercise
        val quizzes =
            LiveDataTestUtil.getValue(quizDao.getQuizzesBy(exerciseId = TestUtil.EXERCISE_ID))

        // Verify
        assertThat(quizzes.size, equalTo(totalQuizSaved))
    }

}