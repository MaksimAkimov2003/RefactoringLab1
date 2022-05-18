package com.example.firstapp.CustomViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.MarginLayoutParams
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firstapp.databinding.ViewDeclareIntegerBinding
import com.example.firstapp.CodingActivity
import com.example.firstapp.MainActivity


class declareInteger @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val bin3 = ViewDeclareIntegerBinding.inflate(LayoutInflater.from(context), this)

    init {
        //selfBlockDelete()
    }

//    private fun selfBlockDelete() {
//        bin3.deleteBlock.setOnClickListener {
//            removeAllViews()
////            (context as? CodingActivity)?.removeViewInAllViewsList(it)
//        }
//    }






}
