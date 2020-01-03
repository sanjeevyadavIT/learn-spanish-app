package com.betatech.learnspanish.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.dao.LessonDao
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.util.LiveDataTestUtil.getValue
import com.betatech.learnspanish.util.TestUtil
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LessonDaoTest {
    private lateinit var lessonDao: LessonDao
    private lateinit var db: AppDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context =  ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        lessonDao = db.lessonDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    private fun saveExerciseAndLessonInDb(): Int{
        val exercises = TestUtil.createExercises(false)
        val lessons = TestUtil.createLessons()

        db.exerciseDao().addAll(exercises)
        val lessonsRowIds = lessonDao.addAll(lessons)

        assertThat(lessonsRowIds.size, equalTo(lessons.size))
        return lessonsRowIds.size
    }

    @Test
    fun saveLessonAndRead() {
        // Setup
        val totalLessonsSaved = saveExerciseAndLessonInDb()

        // Exercise
        val lessons = getValue(lessonDao.getLessonsBy(exerciseId = TestUtil.EXERCISE_ID))

        // Verify
        assertThat(lessons.size, equalTo(totalLessonsSaved))
    }

}