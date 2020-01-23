package com.betatech.learnspanish.ui.quiz


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.betatech.learnspanish.R
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
        setupClickListeners()
        setupLiveObservers()
    }

    /**
     * Some click listeners are directly defined in [QuizViewModel]
     */
    private fun setupClickListeners() {
        dataBinding.btnCloseQuiz.setOnClickListener {
            showCloseQuizDialog()
        }
    }

    private fun showCloseQuizDialog() {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.close_quiz_dialog_title)
                setMessage(R.string.close_quiz_dialog_message)
                setPositiveButton(R.string.quit) { _, _ ->
                    findNavController().popBackStack()
                }
                setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            }
            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    private fun setupLiveObservers() {
        viewModel.questions.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                viewModel.setupFirstQuestion()
            }
        })
        viewModel.quizState.observe(this, Observer { quizState ->
            when (quizState) {
                QuizViewModel.QuizState.NOT_ANSWERED -> dataBinding.mcqAnswerView.rgOptions.clearCheck()
                QuizViewModel.QuizState.COMPLETE -> findNavController().popBackStack()
                QuizViewModel.QuizState.FAIL -> findNavController().popBackStack()
                else -> {
                    // do nothing
                }
            }
        })
    }

}
