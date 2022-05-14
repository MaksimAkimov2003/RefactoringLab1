package com.example.firstapp

import android.R
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firstapp.databinding.ActivityCodingBinding
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.example.firstapp.CustomViews.arithmeticOperations
import com.example.firstapp.CustomViews.assignmentOperator
import com.example.firstapp.CustomViews.declareInteger
import com.google.android.material.internal.ViewUtils.dpToPx
import kotlinx.android.synthetic.main.activity_coding.*
import kotlinx.android.synthetic.main.toolbar.*

class CodingActivity : AppCompatActivity(){
    private val buttonVarDragMessage = "buttonVar Added"

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

        //ВОТ ЗДЕСЬ АНДРЕЙ ПИШИ КОД ГЛАВНОЕ ДРОП НЕ ТРОГАЙ, ВООБЩЕ НЕ УДАЛЯЙ НИ СТРОЧКИ МОЕЙ
//        binding.deleteBlock.setOnClickListener {
//            val addVarBlock1 = binding.addVarBlock1
//            addVarBlock1.removeAllViews()
//        }
//
//        binding.deleteBlock2.setOnClickListener {
//            val addVarBlock2 = binding.addVarBlock2
//            addVarBlock2.removeAllViews()
//        }
//
//        binding.deleteBlock3.setOnClickListener {
//            val addVarBlock3 = binding.addVarBlock3
//            addVarBlock3.removeAllViews()
//        }
//
        binding.apply {
            icon_menu.setOnClickListener{
                mainLayout.openDrawer(GravityCompat.START)
            }
        }


        mainLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent))
    }

    private fun startingAddViews(view: View) {
        binding.helper.addView(view)
        attachViewDragListener(view)
    }

    private fun variablesAddedSet() {
        val declareIntegerView = declareInteger(this)
        startingAddViews(declareIntegerView)
        val param = declareIntegerView.layoutParams as ConstraintLayout.LayoutParams
        val a = binding.helper.height
        val b = binding.helper.width
        param.setMargins(0,a / 2,b,a / 2)

        declareIntegerView.layoutParams = param
        declareIntegerView.setTag("VariablesType")


//        val assignmentOperatorView = assignmentOperator(this)
//        assignmentOperatorView.setTag("VariablesType")
//        startingAddViews(assignmentOperatorView)
//
//        val arithmeticOperationsView = arithmeticOperations(this)
//        arithmeticOperationsView.setTag("VariablesType")
//        startingAddViews(arithmeticOperationsView)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    private val addDragListener = View.OnDragListener { view, dragEvent ->
        val draggableItem = dragEvent.localState as View

        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
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
                    println("draggedData $draggedData")
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


