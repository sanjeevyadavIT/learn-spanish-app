package com.betatech.learnspanish.data.local.db

import android.content.Context
import androidx.room.*
import com.betatech.learnspanish.data.local.db.dao.ExerciseDao
import com.betatech.learnspanish.data.local.db.dao.LessonDao
import com.betatech.learnspanish.data.local.db.dao.QuizDao
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Quiz
import com.betatech.learnspanish.helper.Converters


/**
 * The Room Database that contains the [Exercise], [Lesson], [Quiz] tables.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(
    entities = [Exercise::class, Lesson::class, Quiz::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao

    abstract fun lessonDao(): LessonDao

    abstract fun quizDao(): QuizDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "learn_spanish_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return INSTANCE
            }
        }
    }
}