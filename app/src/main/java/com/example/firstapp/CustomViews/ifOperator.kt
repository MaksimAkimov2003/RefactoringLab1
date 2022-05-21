package com.example.firstapp.CustomViews

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import com.example.firstapp.databinding.ViewIfOperatorBinding
import kotlinx.android.synthetic.main.view_output.view.*

class ifOperator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,

    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val bin4 = ViewIfOperatorBinding.inflate(LayoutInflater.from(context), this)


    init {
        hideKeyboard()

    }

    private fun hideKeyboard() {
        removeKeyboard.setOnClickListener {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

    }
}