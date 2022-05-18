package com.example.firstapp.CustomViews

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.firstapp.CodingActivity
import com.example.firstapp.databinding.ViewArithmeticOperationsBinding

class arithmeticOperations @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val bin1 = ViewArithmeticOperationsBinding.inflate(LayoutInflater.from(context), this)


    init {
        //selfBlockDelete()

    }

//    private fun selfBlockDelete() {
//        bin1.deleteBlock.setOnClickListener {
//            removeAllViews()
//            //(context as? CodingActivity)?.removeViewInAllViewsList(it)
//        }
//    }
}