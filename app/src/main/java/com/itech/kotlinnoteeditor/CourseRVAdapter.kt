package com.itech.kotlinnoteeditor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class CourseRVAdapter (private val context: Context, private val course_items: List<CourseInfo>) : RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseTitleTv: TextView = itemView.findViewById<TextView>(R.id.tv_title)
        var coursePosition = 0
        init {
            itemView.setOnClickListener {
                Snackbar.make(it, course_items[coursePosition].title, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.courselist_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = course_items[position]
        holder.coursePosition = position
        holder.courseTitleTv.text = course.title
    }

    override fun getItemCount(): Int {
        return course_items.size
    }
}