package com.betatech.learnspanish.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.dao.ExerciseDao
import com.betatech.learnspanish.util.LiveDataTestUtil.getValue
import com.betatech.learnspanish.util.TestUtil
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExerciseDaoTest {
    private lateinit var exerciseDao: ExerciseDao
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
        exerciseDao = db.exerciseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveExerciseListAndReadInList() {
        // Setup
        val exercises = TestUtil.createExercises(false)

        // Exercise
        val rowIds = exerciseDao.insertAll(exercises)
        val responseFromDb = getValue(exerciseDao.getExercises())

        // Verify
        assertThat(rowIds.size, equalTo(exercises.size)) // insert successful
        assertThat(responseFromDb.size, equalTo(exercises.size)) // retrieve successful
        assertThat(responseFromDb[0].id, equalTo(TestUtil.EXERCISE_ID))
    }

    /**
     * update this test to unlock an exercise
     */
    /* @Test
     fun updateExercise() {
         // Setup
         val input = TestUtil.createExercises(false)

         exerciseDao.insertAll(input)
         val temp = exerciseDao.getExerciseById(TestUtil.EXERCISE_ID)
         assertThat(temp!!.isUnlocked, equalTo(false))

         // Exercise
         val rowId = exerciseDao.update(
             Exercise(
                 id=TestUtil.EXERCISE_ID,
                 exerciseNumber = 1,
                 title = "Spanish Alphabet",
                 description = "Spanish alphabet pronunciation",
                 isUnlocked = true
         ))
         val responseFromDb = exerciseDao.getExerciseById(TestUtil.EXERCISE_ID)

         // Verify
         assertThat(rowId, equalTo(1))
         assertThat(responseFromDb!!.id, equalTo(TestUtil.EXERCISE_ID))
         assertThat(responseFromDb!!.isCompleted, equalTo(true))
     }*/
}