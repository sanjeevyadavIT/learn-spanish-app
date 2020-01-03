package com.betatech.learnspanish.util

import com.betatech.learnspanish.data.model.db.*

object TestUtil {

    const val EXERCISE_ID = "abc123"
    const val LESSON_ID = "lesson1"

    fun createExercises(isCompleted: Boolean): List<Exercise> = listOf(
        Exercise(
            id = EXERCISE_ID,
            exerciseNumber = 1,
            title = "Spanish Alphabet",
            description = "Spanish alphabet pronunciation",
            isCompleted = isCompleted
        )
    )

    private fun createExamples(): Examples = Examples(
        listOf(
            Example(
                id = "123",
                sentence = "Some spanish sentence",
                translation = "English translation"
            )
        )
    )

    fun createLessons(): List<Lesson> = listOf(
        Lesson(
            id = LESSON_ID,
            exerciseId = EXERCISE_ID,
            lessonNumber = "1",
            phrase = "a",
            phonetics = "a",
            translation = "a",
            description = "spanish alphabet",
            examples = createExamples()
        )
    )

    fun createQuizzes(): List<Quiz> = listOf(
        Quiz(
            id="QuizId123",
            exerciseId = EXERCISE_ID,
            questionNumber = 1,
            questionType = "mcq",
            question = "What is the question",
            hint = "question hint",
            correctAnswer = "Correct Answer",
            options = createOptions()
        )
    )

    private fun createOptions(): Options = Options(
        listOf(
            "Option One",
            "Option Two",
            "Option Three"
        )
    )
}