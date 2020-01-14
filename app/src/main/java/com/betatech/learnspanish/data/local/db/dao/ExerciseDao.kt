package com.betatech.learnspanish.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.betatech.learnspanish.data.model.db.Exercise

/**
 * Data Access Object for the exercises table
 */
@Dao
interface ExerciseDao : BaseDao<Exercise>{

    /**
     * Select all exercises from the exercise table
     *
     * @return list of all exercise wrapped in LiveData
     */
    @Query("SELECT * FROM exercises")
    fun getExercises() : LiveData<List<Exercise>>

    /**
     * Select a exercise by id
     *
     * @param exerciseId the exercise id
     * @return the exercise with exerciseId
     */
    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: String): Exercise?
}