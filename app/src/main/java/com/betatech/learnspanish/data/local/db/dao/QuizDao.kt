package com.betatech.learnspanish.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.betatech.learnspanish.data.model.db.Quiz

@Dao
interface QuizDao: BaseDao<Quiz> {

    @Query("SELECT * FROM quizzes WHERE exerciseId = :exerciseId")
    fun getQuizzesBy(exerciseId: String): LiveData<List<Quiz>>
}