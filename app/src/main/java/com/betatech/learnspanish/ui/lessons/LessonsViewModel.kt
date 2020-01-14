package com.betatech.learnspanish.ui.lessons

import androidx.lifecycle.ViewModel
import com.betatech.learnspanish.data.Repository

class LessonsViewModel(
    val repository: Repository,
    val exerciseId: String?
): ViewModel(){

}