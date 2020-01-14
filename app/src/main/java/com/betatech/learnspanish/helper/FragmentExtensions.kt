package com.betatech.learnspanish.helper

import androidx.fragment.app.Fragment
import com.betatech.learnspanish.App

fun Fragment.getViewModelFactory(
    exerciseId: String? = "-1"
): ViewModelFactory {
    val repository = (requireContext().applicationContext as App).repository
    return ViewModelFactory(repository, exerciseId)
}