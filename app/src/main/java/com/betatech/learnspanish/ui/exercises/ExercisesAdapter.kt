package com.betatech.learnspanish.ui.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.databinding.ExerciseItemBinding


class ExercisesAdapter(
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    private var exercises: List<Exercise>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder =
        ExerciseViewHolder(
            ExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = exercises?.size ?: 0

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        exercises?.get(position)?.let { exercise ->
            holder.bind(exercise)
        }
    }

    fun refreshData(newData: List<Exercise>?) {
        newData?.apply {
            exercises = this
            notifyDataSetChanged()
        }
    }

    inner class ExerciseViewHolder(private val dataBinding: ExerciseItemBinding) :
        RecyclerView.ViewHolder(dataBinding.root), View.OnClickListener {

        init {
            dataBinding.root.setOnClickListener(this)
        }

        fun bind(exercise: Exercise) {
            dataBinding.exercise = exercise
            dataBinding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            exercises?.apply {
                onClick(this[adapterPosition].id)
            }
        }
    }
}