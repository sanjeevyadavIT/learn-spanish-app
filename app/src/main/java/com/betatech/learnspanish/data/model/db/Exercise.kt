package com.betatech.learnspanish.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [Exercise] class represent one exercise,
 * which will contain 5 to 10 [Lesson]
 * each lesson comprising one spanish word
 * to learn.
 *
 * After reading all the lessons, user can
 * take [Question] to complete the exercise,
 * and unlock next exercise and earn points.
 *
 * @param id             id of the exercise
 * @param exerciseNumber serial number of the exercise
 * @param title          title of the exercise
 * @param description    description of the exercise
 * @param isCompleted    whether the exercise has been completed or not
 */
@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "exercise_number")
    val exerciseNumber: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)