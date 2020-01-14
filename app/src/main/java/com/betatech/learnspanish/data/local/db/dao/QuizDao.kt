package com.betatech.learnspanish.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.betatech.learnspanish.data.model.db.Quiz

@Dao
interface QuizDao: BaseDao<Quiz> {

    /**
     * Select all quizzes from the quizzes table,
     * by their exerciseId
     *
     * @return all quizzes wrapped in LiveData
     */
    @Query("SELECT * FROM quizzes WHERE exerciseId = :exerciseId")
    fun getQuizzesByExerciseId(exerciseId: String): LiveData<List<Quiz>>
}