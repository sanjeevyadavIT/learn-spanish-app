package com.betatech.learnspanish.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.betatech.learnspanish.data.model.db.Lesson

@Dao
interface LessonDao : BaseDao<Lesson>{

    @Query("SELECT * FROM lessons WHERE exerciseId = :exerciseId")
    fun getLessonsBy(exerciseId: String): LiveData<List<Lesson>>
}