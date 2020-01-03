package com.betatech.learnspanish.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.betatech.learnspanish.data.model.db.Exercise

@Dao
interface ExerciseDao : BaseDao<Exercise>{

    @Query("SELECT * FROM exercises")
    fun getAll() : LiveData<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    fun getById(id: String): Exercise
}