package com.betatech.learnspanish.data.local.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.betatech.learnspanish.data.local.db.dao.ExerciseDao
import com.betatech.learnspanish.data.local.db.dao.LessonDao
import com.betatech.learnspanish.data.local.db.dao.QuizDao
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.data.model.db.Lesson
import com.betatech.learnspanish.data.model.db.Quiz
import com.betatech.learnspanish.helper.Converters
import com.betatech.learnspanish.helper.ParseJsonDataFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors


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
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Executors.newSingleThreadExecutor().execute {
                                    instance?.let {db ->
                                        val (exercises, lessons, quizzes) = ParseJsonDataFile.parse(context)

                                        db.exerciseDao().insertAll(exercises)
                                        db.lessonDao().insertAll(lessons)
                                        db.quizDao().insertAll(quizzes)
                                    }
                                }
                            }
                        })
                        .build()
                    INSTANCE = instance
                }
                return INSTANCE
            }
        }
    }
}