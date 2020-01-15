package com.betatech.learnspanish.ui.exercises


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.betatech.learnspanish.data.AppRepository
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.AppDbHelper
import com.betatech.learnspanish.databinding.FragmentExercisesBinding
import com.betatech.learnspanish.helper.ViewModelFactory
import com.betatech.learnspanish.helper.getViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ExercisesFragment : Fragment() {

    private val viewModel by viewModels<ExercisesViewModel> { getViewModelFactory() }

    private lateinit var dataBinding: FragmentExercisesBinding
    private lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentExercisesBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupLiveObservers()
    }

    private fun setupLiveObservers() {
        viewModel.exercises.observe(this, Observer {
            exercisesAdapter.refreshData(it)
        })
    }

    private fun setupListAdapter() {
        exercisesAdapter = ExercisesAdapter { exerciseId ->
            openLessonsFragment(exerciseId)
        }
        dataBinding.exercisesList.adapter = exercisesAdapter
    }

    private fun openLessonsFragment(exerciseId: String) {
        val action =
            ExercisesFragmentDirections.actionExercisesFragmentToLessonsFragment(exerciseId)
        findNavController().navigate(action)
    }

}
