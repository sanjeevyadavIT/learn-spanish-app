package com.betatech.learnspanish.ui.exercises


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.betatech.learnspanish.R
import com.betatech.learnspanish.databinding.FragmentExercisesBinding
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
        setHasOptionsMenu(true)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupLiveObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                openSettingsFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    private fun openSettingsFragment() {
        val action = ExercisesFragmentDirections.actionExercisesFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

}
