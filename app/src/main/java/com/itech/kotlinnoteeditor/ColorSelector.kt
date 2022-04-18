package com.itech.kotlinnoteeditor

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.color_selector.view.*


class ColorSelector @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val listOfColors = listOf(Color.BLUE, Color.RED, Color.GREEN)
    private var selectedColorIndex = 0

    init {
        orientation = LinearLayout.HORIZONTAL
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector, this)

        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        colorSelectorArrowLeft.setOnClickListener { selectPreviousColor() }

        colorSelectorArrowRight.setOnClickListener { selectNextColor() }

        colorEnabled.setOnCheckedChangeListener{ buttonView, isChecked -> broadcastColor(setSelectedColor()) }
    }

    private fun selectNextColor() {
        if (selectedColorIndex == listOfColors.lastIndex){
            selectedColorIndex = 0
        }else{
            selectedColorIndex++
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor(setSelectedColor())
    }

    private fun selectPreviousColor() {
        if (selectedColorIndex == 0){
            selectedColorIndex = listOfColors.lastIndex
        }else{
            selectedColorIndex--
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor(setSelectedColor())
    }

    private fun setSelectedColor() : Int {
        return if (colorEnabled.isChecked)
            listOfColors[selectedColorIndex]
        else
            Color.TRANSPARENT
    }

    private var colorSelectedListener: ColorSelectorListener? = null

    interface ColorSelectorListener {
        fun onColorSelected(color: Int)
    }

    fun setColorSelectListener(listener: ColorSelectorListener){
        this.colorSelectedListener = listener
    }

    private fun broadcastColor(color: Int){
        this.colorSelectedListener?.onColorSelected(color)
    }

}