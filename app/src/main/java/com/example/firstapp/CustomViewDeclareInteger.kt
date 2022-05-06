package com.example.firstapp


import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.firstapp.databinding.ViewDeclareIntegerBinding

class CustomViewDeclareInteger @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewDeclareIntegerBinding.inflate(LayoutInflater.from(context), this)


    init {
        selfBlockDelete()
    }

    private fun selfBlockDelete() {
        binding.deleteBlock.setOnClickListener {


        }
    }

}


