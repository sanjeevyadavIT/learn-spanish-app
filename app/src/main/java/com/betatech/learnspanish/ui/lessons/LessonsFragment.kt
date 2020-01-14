package com.betatech.learnspanish.ui.lessons


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs

import com.betatech.learnspanish.R
import com.betatech.learnspanish.databinding.FragmentLessonsBinding
import com.betatech.learnspanish.helper.getViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class LessonsFragment : Fragment() {

    private val viewModel by viewModels<LessonsViewModel>{ getViewModelFactory(args.exerciseId) }

    private lateinit var dataBinding: FragmentLessonsBinding

    private val args: LessonsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentLessonsBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}
