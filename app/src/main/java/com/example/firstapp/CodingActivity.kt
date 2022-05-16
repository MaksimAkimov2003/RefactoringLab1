package com.example.firstapp

import android.R
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firstapp.databinding.ActivityCodingBinding
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.GravityCompat
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import androidx.core.view.updateLayoutParams
import com.example.firstapp.CustomViews.arithmeticOperations
import com.example.firstapp.CustomViews.assignmentOperator
import com.example.firstapp.CustomViews.declareInteger
import com.google.android.material.internal.ViewUtils.dpToPx
import kotlinx.android.synthetic.main.activity_coding.*
import kotlinx.android.synthetic.main.activity_coding.view.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_arithmetic_operations.*
import kotlinx.android.synthetic.main.view_assignment_operator.*
import kotlinx.android.synthetic.main.view_declare_integer.*
import kotlinx.android.synthetic.main.view_declare_integer.textNumber
import kotlinx.android.synthetic.main.view_declare_integer.view.*

class CodingActivity : AppCompatActivity(){
    private val buttonVarDragMessage = "buttonVar Added"

    private val tagDeclareIntegerView = "DeclareInteger"
    private val tagArithmeticOperationsView = "ArithmeticOperations"
    private val tagAssignmentOperatorView = "AssignmentOperator"

    var number = 1

    private lateinit var binding: ActivityCodingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        variablesAddedSet()

        binding.dropArea.setOnDragListener(addDragListener)

//        binding.inputButton.setOnClickListener {
//            hideKeyboard(it)
//        }


        binding.apply {
            icon_menu.setOnClickListener{
                mainLayout.openDrawer(GravityCompat.START)
            }
        }


        mainLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent))
    }

    private fun startingAddViews(view: View, params : ConstraintLayout.LayoutParams, tag : String) {
        view.setTag(tag)


        binding.helper.addView(view, params)
        attachViewDragListener(view)
    }

    private fun variablesAddedSet() {
        val declareIntegerView = declareInteger(this)

        val paramsDeclareIntegerView : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
            ConstraintLayout.LayoutParams.WRAP_CONTENT //height
        )


        paramsDeclareIntegerView.topToTop = binding.helper.id
        paramsDeclareIntegerView.leftToLeft = binding.helper.id

        paramsDeclareIntegerView.topMargin = 100
        paramsDeclareIntegerView.leftMargin = 0

        startingAddViews(declareIntegerView, paramsDeclareIntegerView, tagDeclareIntegerView)


        val assignmentOperatorView = assignmentOperator(this)

        val paramsAssignmentOperatorView : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
            ConstraintLayout.LayoutParams.WRAP_CONTENT //height
        )

        paramsAssignmentOperatorView.topToTop = binding.helper.id
        paramsAssignmentOperatorView.leftToLeft = binding.helper.id

        paramsAssignmentOperatorView.topMargin = 400
        paramsAssignmentOperatorView.leftMargin = 0

        startingAddViews(assignmentOperatorView, paramsAssignmentOperatorView, tagAssignmentOperatorView)


        val arithmeticOperationsView = arithmeticOperations(this)

        val paramsArithmeticOperationsView : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
            ConstraintLayout.LayoutParams.WRAP_CONTENT //height
        )

        paramsArithmeticOperationsView.topToTop = binding.helper.id
        paramsArithmeticOperationsView.leftToLeft = binding.helper.id

        paramsArithmeticOperationsView.topMargin = 700
        paramsArithmeticOperationsView.leftMargin = 0

        startingAddViews(arithmeticOperationsView, paramsArithmeticOperationsView, tagArithmeticOperationsView)
    }


//    private fun hideKeyboard(view: View) {
//        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
//    }

    private val addDragListener = View.OnDragListener { view, dragEvent ->
        val draggableItem = dragEvent.localState as View

        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                draggableItem.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    topToTop = binding.dropArea.id
                    leftToLeft = binding.dropArea.id

                    topMargin = 0
                    leftMargin = 0
                }
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                binding.dropArea.alpha = 0.3f
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                binding.dropArea.alpha = 1.0f
                draggableItem.visibility = View.VISIBLE

                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                binding.dropArea.alpha = 1.0f

                if (dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    val draggedData = dragEvent.clipData.getItemAt(0).text
                }

                draggableItem.x = dragEvent.x - (draggableItem.width / 2)
                draggableItem.y = dragEvent.y - (draggableItem.height / 2)

                val parent = draggableItem.parent as ConstraintLayout

                if (parent == binding.helper) {
                    parent.removeAllViews()
                    variablesAddedSet()
                }

                if (parent == binding.dropArea) {
                    parent.removeView(draggableItem)
                }

                val dropPlace = view as ConstraintLayout



                dropPlace.addView(draggableItem)
                val str = number.toString(10)
                draggableItem.textNumber.setText(str)
                number++

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggableItem.visibility = View.VISIBLE
                view.invalidate()

                true
            }
            else -> {
                false
            }

        }
    }

    private fun attachViewDragListener(someView: View) {

        someView.setOnLongClickListener { view: View ->

            val item = ClipData.Item(buttonVarDragMessage)
            val dataToDrag = ClipData(
                buttonVarDragMessage,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item
            )

            val maskShadow = MaskDragShadowBuilder(view)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                @Suppress("DEPRECATION")
                view.startDrag(dataToDrag, maskShadow, view, 0)
            }

            else {
                view.startDragAndDrop(dataToDrag, maskShadow, view, 0)
            }

            view.visibility = View.INVISIBLE

            true
        }

    }
}

private class MaskDragShadowBuilder(view: View) : View.DragShadowBuilder(view) {
    private val shadow = view

    override fun onProvideShadowMetrics(size: Point?, touch: Point?) {
        val width: Int = view.width
        val height: Int = view.height

        size?.set(width, height)
        touch?.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas?) {
        shadow.draw(canvas)
    }
}


