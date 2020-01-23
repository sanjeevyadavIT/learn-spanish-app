package com.betatech.learnspanish.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.betatech.learnspanish.data.model.db.Question

@Dao
interface QuestionDao : BaseDao<Question> {

    /**
     * Select all questions from the questions table,
     * by their exerciseId
     *
     * @return list of questions wrapped in LiveData
     */
    @Query("SELECT * FROM questions WHERE exerciseId = :exerciseId")
    fun getQuestionsByExerciseId(exerciseId: String): LiveData<List<Question>>
}