package com.betatech.learnspanish.ui.exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.betatech.learnspanish.data.model.db.Exercise
import com.betatech.learnspanish.databinding.ExerciseItemBinding


class ExercisesAdapter(
    val itemClickListener: ListItemClickListener,
    var exercises: List<Exercise> = emptyList()
) : RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder =
        ExerciseViewHolder(
            ExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.bind(exercise)
    }

    fun refreshData(newData: List<Exercise>?) {
        newData?.apply {
            exercises = this
            notifyDataSetChanged()
        }
    }

    inner class ExerciseViewHolder(private val dataBinding: ExerciseItemBinding) :
        RecyclerView.ViewHolder(dataBinding.root), View.OnClickListener {

        init{
            dataBinding.root.setOnClickListener(this)
        }

        fun bind(exercise: Exercise) {
            dataBinding.exercise = exercise
            dataBinding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            itemClickListener.onListItemClick(exercises[adapterPosition].id)
        }


    }

    interface ListItemClickListener {
        fun onListItemClick(exerciseId: String)
    }
}