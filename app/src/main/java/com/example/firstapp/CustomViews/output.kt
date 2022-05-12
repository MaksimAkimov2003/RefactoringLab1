package com.example.firstapp.CustomViews

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.firstapp.databinding.ViewOutputBinding

class output @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewOutputBinding.inflate(LayoutInflater.from(context), this)


    init {
        selfBlockDelete()

    }

    private fun selfBlockDelete() {
        binding.deleteBlock.setOnClickListener {



        }
    }
}