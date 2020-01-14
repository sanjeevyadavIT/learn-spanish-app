package com.betatech.learnspanish.ui.exercises


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.betatech.learnspanish.data.AppRepository
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.AppDbHelper
import com.betatech.learnspanish.databinding.FragmentExercisesBinding
import com.betatech.learnspanish.helper.ViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ExercisesFragment : Fragment(), ExercisesAdapter.ListItemClickListener {

    private lateinit var dataBinding: FragmentExercisesBinding
    private lateinit var viewModel: ExercisesViewModel
    private lateinit var exercisesAdapter: ExercisesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupViewModel()
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

    override fun onListItemClick(exerciseId: String) {
        openLessonsFragment(exerciseId)
    }

    private fun setupLiveObservers() {
        viewModel.exercises.observe(this, Observer {
            exercisesAdapter.refreshData(it)
        })
    }

    private fun setupListAdapter() {
        exercisesAdapter = ExercisesAdapter(this)
        dataBinding.exercisesList.adapter = exercisesAdapter
    }

    /**
     * TODO: Use Dependency injection to simplify this code
     */
    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val dbHelper = AppDbHelper.getInstance(AppDatabase.getInstance(application)!!)
        val repository = AppRepository.getInstance(dbHelper)
        val viewModelFactory = ViewModelFactory(repository)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(ExercisesViewModel::class.java)
    }

    private fun openLessonsFragment(exerciseId: String) {
        val action = ExercisesFragmentDirections.actionExercisesFragmentToLessonsFragment(exerciseId)
        findNavController().navigate(action)
    }

}
