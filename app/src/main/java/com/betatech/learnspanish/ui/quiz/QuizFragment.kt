package com.betatech.learnspanish.ui.quiz


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.betatech.learnspanish.databinding.FragmentQuizBinding
import com.betatech.learnspanish.helper.getViewModelFactory
import com.betatech.learnspanish.ui.lessons.LessonsFragmentArgs

/**
 * A simple [Fragment] subclass.
 */
class QuizFragment : Fragment() {

    private val viewModel by viewModels<QuizViewModel> { getViewModelFactory(args.exerciseId) }

    private lateinit var dataBinding: FragmentQuizBinding

    private val args: LessonsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentQuizBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

}
