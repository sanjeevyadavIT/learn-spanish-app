package com.betatech.learnspanish.helper

import android.app.PendingIntent.getActivity
import android.content.Context
import com.betatech.learnspanish.data.model.db.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object ParseJsonDataFile {

    fun parse(context: Context): Triple<List<Exercise>, List<Lesson>, List<Quiz>> {
        val exercises = mutableListOf<Exercise>()
        val lessons = mutableListOf<Lesson>()
        val quizzes = mutableListOf<Quiz>()

        try {
            val jsonObject = JSONObject(loadJsonFromAssets(context) ?: "")
            if (jsonObject.has("exercises")) {
                val exerciseJsonArray = jsonObject.getJSONArray("exercises")
                for (i in 0 until exerciseJsonArray.length()) {
                    val exerciseJsonObject = exerciseJsonArray.getJSONObject(i)
                    val exercise = Exercise(
                        id = exerciseJsonObject.getString("id"),
                        exerciseNumber = exerciseJsonObject.getInt("exercise_number"),
                        title = exerciseJsonObject.getString("title"),
                        description = exerciseJsonObject.getString("description"),
                        isCompleted = i == 0 // By default mark first one complete
                    )

                    parseLessonFromExercise(exerciseJsonObject, exercise, lessons)

                    parseQuizFromExercise(exerciseJsonObject, exercise, quizzes)

                    exercises.add(exercise)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        return Triple(
            exercises,
            lessons,
            quizzes
        )
    }

    private fun loadJsonFromAssets(context: Context): String? {
        val json: String?
        json = try {
            val `is`: InputStream = context.resources.openRawResource(
                context.resources.getIdentifier(
                    "learnspanish",
                    "raw",
                    context.packageName
                )
            )
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun parseLessonFromExercise(
        exerciseJsonObject: JSONObject,
        exercise: Exercise,
        lessons: MutableList<Lesson>
    ) {
        if (exerciseJsonObject.has("lessons")) {
            val lessonsJsonArray = exerciseJsonObject.getJSONArray("lessons")
            for (j in 0 until lessonsJsonArray.length()) {
                val lessonJsonObject = lessonsJsonArray.getJSONObject(j)
                val lesson = Lesson(
                    id = lessonJsonObject.getString("id"),
                    exerciseId = exercise.id,
                    lessonNumber = lessonJsonObject.getInt("lesson_number"),
                    phrase = lessonJsonObject.getString("phrase"),
                    phonetics = lessonJsonObject.getString("phonetics"),
                    translation = lessonJsonObject.getString("translation"),
                    description = lessonJsonObject.getString("description"),
                    examples = parseExampleFromLesson(lessonJsonObject.getJSONArray("examples"))
                )
                lessons.add(lesson)
            }
        }
    }

    private fun parseExampleFromLesson(exampleJsonArray: JSONArray): Examples {
        val examples = mutableListOf<Example>()
        for (i in 0 until exampleJsonArray.length()) {
            val exampleJsonObject = exampleJsonArray.getJSONObject(i)
            val example = Example(
                id = exampleJsonObject.getString("id"),
                sentence = exampleJsonObject.getString("sentence"),
                translation = exampleJsonObject.getString("translation")
            )
            examples.add(example)
        }

        return Examples(examples)
    }

    private fun parseQuizFromExercise(
        exerciseJsonObject: JSONObject,
        exercise: Exercise,
        quizzes: MutableList<Quiz>
    ) {
        if (exerciseJsonObject.has("quiz")) {
            val quizJsonArray = exerciseJsonObject.getJSONArray("quiz")
            for (k in 0 until quizJsonArray.length()) {
                val quizJsonObject = quizJsonArray.getJSONObject(k)
                val quiz = Quiz(
                    id = quizJsonObject.getString("id"),
                    exerciseId = exercise.id,
                    questionNumber = quizJsonObject.getInt("question_number"),
                    question = quizJsonObject.getString("question"),
                    questionType = quizJsonObject.getString("type"),
                    hint = quizJsonObject.getString("hint"),
                    correctAnswer = quizJsonObject.getString("correct_answer"),
                    options = parseOptionsFromQuiz(quizJsonObject.getJSONArray("options"))
                )
                quizzes.add(quiz)
            }
        }
    }

    private fun parseOptionsFromQuiz(optionsJsonArray: JSONArray): Options {
        val options = mutableListOf<String>()
        for (i in 0 until optionsJsonArray.length()) {
            options.add(optionsJsonArray.getString(i))
        }
        return Options(options)
    }
}