package com.example.firstapp.CustomViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firstapp.databinding.ViewIfCloseBinding

class ifClose @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val bin7 = ViewIfCloseBinding.inflate(LayoutInflater.from(context), this)

//    init {
//
//    }
}