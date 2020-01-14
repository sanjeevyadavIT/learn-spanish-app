package com.betatech.learnspanish.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.betatech.learnspanish.data.model.db.Lesson

/**
 * Data Access Object for the lessons table
 */
@Dao
interface LessonDao : BaseDao<Lesson>{

    /**
     * Select all lessons from the lessons table,
     * by their exerciseId
     *
     * @return list of lesson wrapped in LiveData
     */
    @Query("SELECT * FROM lessons WHERE exerciseId = :exerciseId")
    fun getLessonsByExerciseId(exerciseId: String): LiveData<List<Lesson>>
}