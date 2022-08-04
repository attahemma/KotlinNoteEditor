package com.itech.kotlinnoteeditor.ui.courses

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.itech.kotlinnoteeditor.R

class CoursesViewModel : ViewModel() {
    fun saveState(outState: Bundle) {
        outState.putInt(navDrawerDisplaySelectionName, navDrawerDisplaySelection)
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelection = savedInstanceState.getInt(navDrawerDisplaySelectionName)
    }

    var navDrawerDisplaySelection = R.id.nav_courses;
    val navDrawerDisplaySelectionName = "com.itech.kotlinnoteeditor.ui.courses.COURSES"
}