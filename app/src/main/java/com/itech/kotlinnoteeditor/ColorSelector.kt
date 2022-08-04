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

    private var listOfColors = listOf(Color.BLUE)
    private var selectedColorIndex = 0

    init {
        val typedArray = context?.obtainStyledAttributes(
            attrs, R.styleable.ColorSelector
        )
        listOfColors = typedArray?.getTextArray(R.styleable.ColorSelector_colors)!!.map {
            Color.parseColor(it.toString())
        }
        typedArray.recycle()
        orientation = LinearLayout.HORIZONTAL
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector, this)

        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        colorSelectorArrowLeft.setOnClickListener { selectPreviousColor() }

        colorSelectorArrowRight.setOnClickListener { selectNextColor() }

        colorEnabled.setOnCheckedChangeListener{ buttonView, isChecked -> broadcastColor() }
    }

    private fun selectNextColor() {
        if (selectedColorIndex == listOfColors.lastIndex){
            selectedColorIndex = 0
        }else{
            selectedColorIndex++
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor()
    }

    var selectedColorValue: Int = android.R.color.transparent
        set(value) {
            var index = listOfColors.indexOf(value)
            if (index == -1){
                colorEnabled.isChecked = false
                index = 0
            }else{
                colorEnabled.isChecked = true
            }
            selectedColorIndex = index
            selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        }


    private fun selectPreviousColor() {
        if (selectedColorIndex == 0){
            selectedColorIndex = listOfColors.lastIndex
        }else{
            selectedColorIndex--
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        broadcastColor()
    }

    private var colorSelectedListeners: ArrayList<(Int) -> Unit> = arrayListOf()

    fun addListener(function: (Int) -> Unit) {
        this.colorSelectedListeners.add(function)
    }

//    interface ColorSelectorListener {
//        fun onColorSelected(color: Int)
//    }
//
//    fun setColorSelectListener(listener: ColorSelectorListener){
//        this.colorSelectedListener = listener
//    }

    private fun broadcastColor(){
        val color = if (colorEnabled.isChecked)
            listOfColors[selectedColorIndex]
        else
            Color.TRANSPARENT
//        this.colorSelectedListener?.onColorSelected(color)
        this.colorSelectedListeners.forEach { function ->
            function(color)
        }
    }

}