package com.example.firstapp

import android.R
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.firstapp.databinding.ActivityCodingBinding
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_coding.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.activity_main.*

class CodingActivity : AppCompatActivity(){
    private val buttonVarDragMessage = "buttonVar Added"

    private lateinit var binding: ActivityCodingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachViewDragListener()

        binding.dropArea.setOnDragListener(addDragListener)

        binding.inputButton.setOnClickListener {
            hideKeyboard(it)
        }

        //ВОТ ЗДЕСЬ АНДРЕЙ ПИШИ КОД ГЛАВНОЕ ДРОП НЕ ТРОГАЙ, ВООБЩЕ НЕ УДАЛЯЙ НИ СТРОЧКИ МОЕЙ
        binding.deleteBlock.setOnClickListener {
            val addVarBlock1 = binding.addVarBlock1
            addVarBlock1.removeAllViews()
        }

        binding.deleteBlock2.setOnClickListener {
            val addVarBlock2 = binding.addVarBlock2
            addVarBlock2.removeAllViews()
        }

        binding.deleteBlock3.setOnClickListener {
            val addVarBlock3 = binding.addVarBlock3
            addVarBlock3.removeAllViews()
        }

        binding.apply {
            icon_menu.setOnClickListener{
                mainLayout.openDrawer(GravityCompat.START)
            }
        }

        mainLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));

        


    }

    private fun calc(input: String): String {
        val rpn = invertStack(toRPN(input))
        val result = Stack()
        var msgError = ""

        try {
            while (!rpn.isEmpty()) {
                val item = rpn.pop()
                if (item.isNotEmpty()) {
                    if (item[0].isDigit()) {
                        result.push(item)
                        continue
                    }

                    val num2 = result.pop().toDouble()
                    val num1 = result.pop().toDouble()

                    if (num2 == 0.0) {
                        msgError = resources.getString(R.string.msg_error_zero)
                        break
                    }

                    @Suppress("IMPLICIT_CAST_TO_ANY")
                    result.push(
                        when (item[0]) {
                            '+' -> num1 + num2
                            '-' -> num1 - num2
                            '*' -> num1 * num2
                            '/' -> num1 / num2
                            '%' -> num1 % num2
                            else -> 0
                        }.toString()
                    )
                }
            }
        } catch (e: Exception) {
            msgError = resources.getString(R.string.msg_error_unknown)
        }

        return msgError.ifEmpty {
            result.pop()
        }
    }

    private fun toRPN(expr: String): Stack {
        val result = Stack()
        val stack = SymbolsStack()
        var buffer = ""

        for(ch: Char in expr) {
            if(ch.isDigit()) {
                buffer += ch.toString()
                continue
            }

            result.push(buffer)
            buffer = ""
            if(stack.isEmpty() || ch == '(') {
                stack.push(ch)
            } else if(ch == ')') {
                loop@while(!stack.isEmpty()) {
                    val last = stack.pop()
                    when(last) {
                        '(' -> break@loop
                        else -> result.push(last.toString())
                    }
                }
            } else {
                if(stack.comparePriority(ch)) {
                    stack.push(ch)
                } else {
                    while(!stack.isEmpty()) {
                        if(!stack.comparePriority(ch)) {
                            result.push(stack.pop().toString())
                        } else {
                            break
                        }
                    }
                    stack.push(ch)
                }
            }
        }

        result.push(buffer)
        while(!stack.isEmpty()) {
            result.push(stack.pop().toString())
        }
        return result
    }

    private fun invertStack(stack: Stack): Stack {
        val result = Stack()
        while(!stack.isEmpty()) result.push(stack.pop())
        return result
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
                parent.removeView(draggableItem)

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

    private fun attachViewDragListener() {
        binding.addVarBlock1.setOnLongClickListener { view: View ->

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

        binding.addVarBlock2.setOnLongClickListener { view: View ->

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

        binding.addVarBlock3.setOnLongClickListener { view: View ->

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


