package com.betatech.learnspanish.data.model.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.betatech.learnspanish.helper.Converters

/**
 * [Lesson] class contain one spanish word [Lesson.phrase]
 * and it's translation [Lesson.translation] to learn
 */
@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("exerciseId"),
            onDelete = CASCADE
        )
    ]
)
data class Lesson(
    @PrimaryKey
    val id: String,
    val exerciseId: String,
    @ColumnInfo(name = "lesson_number")
    val lessonNumber: String,
    val phrase: String,
    val phonetics: String,
    val translation: String,
    val description: String,
    val examples: Examples
)

/**
 * Room cannot store List directly hence creating this middle pojo class
 * link: https://stackoverflow.com/a/44615752/4859873
 */
data class Examples(
    val data: List<Example>
)

data class Example(
    val id: String,
    val sentence: String,
    val translation: String
)