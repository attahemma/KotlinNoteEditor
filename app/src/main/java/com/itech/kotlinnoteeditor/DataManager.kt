package com.itech.kotlinnoteeditor

object DataManager {
    val courses = HashMap<String,CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }

    private fun initializeNotes() {
        var notes = courses.get("android_intents")?.let {
            NoteInfo(it,"Introduction to Intents","Learning how to share content from app through intents action send")
        }
        this.notes.add(notes!!)

        notes = courses.get("java_lang")?.let {
            NoteInfo(it,"Learning Java Basics","Java is a class based, object oriented programming lang that's strongly typed")
        }
        this.notes.add(notes!!)

        notes = courses.get("android_async")?.let {
            NoteInfo(it,"Asynchronous Programming in Android","LiveData is actually asynchronous")
        }
        this.notes.add(notes!!)

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