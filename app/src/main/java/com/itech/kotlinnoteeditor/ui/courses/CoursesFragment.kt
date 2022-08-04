package com.itech.kotlinnoteeditor.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.itech.kotlinnoteeditor.CourseRVAdapter
import com.itech.kotlinnoteeditor.DataManager
import com.itech.kotlinnoteeditor.databinding.FragmentGalleryBinding

class CoursesFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this).get(CoursesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val galleryViewModel =
//            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (savedInstanceState != null)
            viewModel.restoreState(savedInstanceState)

        binding.rvCourses.layoutManager = GridLayoutManager(context,2)
        binding.rvCourses.adapter = CourseRVAdapter(requireContext(), DataManager.courses.values.toList())

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.let { viewModel.saveState(it) }
    }

    override fun onResume() {
        super.onResume()
        binding.rvCourses.adapter!!.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}