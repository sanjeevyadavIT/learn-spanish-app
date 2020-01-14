package com.betatech.learnspanish.ui.exercises


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.betatech.learnspanish.R
import com.betatech.learnspanish.data.AppRepository
import com.betatech.learnspanish.data.local.db.AppDatabase
import com.betatech.learnspanish.data.local.db.AppDbHelper
import com.betatech.learnspanish.helper.ViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class ExercisesFragment : Fragment() {

    private lateinit var viewModel: ExercisesViewModel
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dbHelper = AppDbHelper.getInstance(AppDatabase.getInstance(application)!!)
        val respository = AppRepository.getInstance(dbHelper)
        val viewModelFactory = ViewModelFactory(respository)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExercisesViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_exercises, container, false)
        textView = rootView.findViewById(R.id.dummy_text)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.exercises.observe(this, Observer {
            var dummy =  "Exercises: \n"
            it.forEach { data ->
                dummy += data.title + "\n"
            }
            textView.text = dummy
        })
    }

}
