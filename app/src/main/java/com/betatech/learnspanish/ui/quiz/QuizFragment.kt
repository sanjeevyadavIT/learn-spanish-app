package com.betatech.learnspanish.ui.quiz


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.betatech.learnspanish.R
import com.betatech.learnspanish.databinding.FragmentQuizBinding
import com.betatech.learnspanish.helper.getViewModelFactory
import com.betatech.learnspanish.ui.quiz.result.ResultViewModel

/**
 * Fragment to allow user to take quiz
 */
class QuizFragment : Fragment() {

    private lateinit var dataBinding: FragmentQuizBinding

    private val args: QuizFragmentArgs by navArgs()

    private val viewModel by viewModels<QuizViewModel> { getViewModelFactory(args.exerciseId) }

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
        setupBackPressHandler()
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
                QuizViewModel.QuizState.COMPLETE -> openResultFragment(ResultViewModel.QuizResult.SUCCESS)
                QuizViewModel.QuizState.FAIL -> openResultFragment(ResultViewModel.QuizResult.FAILED)
                else -> {
                    // do nothing
                }
            }
        })
    }

    private fun setupBackPressHandler() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showCloseQuizDialog()
        }
    }

    private fun openResultFragment(quizResult: ResultViewModel.QuizResult) {
        val action = QuizFragmentDirections.actionQuizFragmentToResultFragment(
            lifeLeft = viewModel.lifeLeft.value ?: 0,
            exerciseId = args.exerciseId,
            quizResult = quizResult
        )
        findNavController().navigate(action)
    }

}
