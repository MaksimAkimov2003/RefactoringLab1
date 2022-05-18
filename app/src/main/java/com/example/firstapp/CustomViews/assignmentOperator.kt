package com.example.firstapp.CustomViews

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.firstapp.CodingActivity
import com.example.firstapp.databinding.ViewAssignmentOperatorBinding

class assignmentOperator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val bin2 = ViewAssignmentOperatorBinding.inflate(LayoutInflater.from(context), this)


    init {
        //selfBlockDelete()

    }

//    private fun selfBlockDelete() {
//        bin2.deleteBlock.setOnClickListener {
//            removeAllViews()
//            //(context as? CodingActivity)?.removeViewInAllViewsList(it)
//        }
//    }
}
