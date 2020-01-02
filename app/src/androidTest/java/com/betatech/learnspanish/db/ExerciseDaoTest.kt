package com.betatech.learnspanish.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.dao.ExerciseDao
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.util.LiveDataTestUtil.getValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

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
        val exerciseList = listOf<Exercise>(
            Exercise(
                id="abc123",
                exerciseNumber = 1,
                title = "Spanish Alphabet",
                description = "Spanish alphabet pronunciation"
            ),
            Exercise(
                id="abc1234",
                exerciseNumber = 2,
                title = "Spanish Pronoun",
                description = "He, She, I, You, They, We"
            )
        )

        // Exercise
        val rowIds = exerciseDao.addAll(exerciseList)
        val responseFromDb = getValue(exerciseDao.getAll())

        // Verify
        assert(rowIds.size == 2) // insert successful
        assert(responseFromDb.size == 2) // retrieve successful
        assert(responseFromDb[0].title == "Spanish Alphabet")
    }
}