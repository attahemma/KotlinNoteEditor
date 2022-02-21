package com.itech.kotlinnoteeditor

class DataManager {
    val courses = HashMap<String,CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
    }

    private fun initializeCourses (){
        var course = CourseInfo("android_intents", "Android programming with Intents")
        courses.set(course.courseId, course)

        course = CourseInfo("android_async", "Android Async programming and services")
        courses.set(key = course.courseId, value = course)

        course = CourseInfo(title = "Java fundamentals: The java language", courseId = "java_lang")
        courses.set(course.courseId,course)
    }
}