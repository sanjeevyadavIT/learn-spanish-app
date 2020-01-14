package com.betatech.learnspanish.ui.lessons


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.betatech.learnspanish.R
import com.betatech.learnspanish.databinding.FragmentLessonsBinding

/**
 * A simple [Fragment] subclass.
 */
class LessonsFragment : Fragment() {

    private lateinit var dataBinding: FragmentLessonsBinding

    private lateinit var viewModel: LessonsViewModel

    private val args: LessonsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentLessonsBinding.inflate(inflater, container, false).apply {
//            viewmodel = viewModel
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.tvDummyText.text = args.exerciseId
    }


}
