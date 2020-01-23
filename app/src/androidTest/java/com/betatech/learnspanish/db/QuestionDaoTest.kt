package com.betatech.learnspanish.db

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.dao.QuestionDao
import com.betatech.learnspanish.util.LiveDataTestUtil
import com.betatech.learnspanish.util.TestUtil
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuestionDaoTest {
    private lateinit var quizDao: QuestionDao
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
        quizDao = db.questionDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    private fun saveExerciseAndQuizInDb(): Int {
        val exercises = TestUtil.createExercises(false)
        val quizzes = TestUtil.createQuiz()

        db.exerciseDao().insertAll(exercises)
        val quizRowIds = quizDao.insertAll(quizzes)

        assertThat(quizRowIds.size, equalTo(quizzes.size))
        return quizRowIds.size
    }

    @Test
    fun saveLessonAndRead() {
        // Setup
        val totalQuizSaved = saveExerciseAndQuizInDb()

        // Exercise
        val quizzes =
            LiveDataTestUtil.getValue(quizDao.getQuestionsByExerciseId(exerciseId = TestUtil.EXERCISE_ID))

        // Verify
        assertThat(quizzes.size, equalTo(totalQuizSaved))
    }

    /**
     * This test, tests that foreign key constraint
     * is properly followed
     */
    @Test(expected = SQLiteConstraintException::class)
    fun insertionFailsWhenNoExercisePresent(){
        // Setup
        val quizzes = TestUtil.createQuiz()

        // Exercise
        quizDao.insertAll(quizzes)
    }

}