package com.example.firstapp

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firstapp.databinding.ActivityCodingBinding

class CodingActivity : AppCompatActivity(){
    private val buttonVarDragMessage = "buttonVar Added"

    private lateinit var binding: ActivityCodingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachViewDragListener()

        binding.dropArea.setOnDragListener(addDragListener)

        //ВОТ ЗДЕСЬ АНДРЕЙ ПИШИ КОД ГЛАВНОЕ ДРОП НЕ ТРОГАЙ, ВООБЩЕ НЕ УДАЛЯЙ НИ СТРОЧКИ МОЕЙ
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

                draggableItem.x = dragEvent.x - (draggableItem.width / 2) - draggableItem.width
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

        binding.addAssignmentBlock.setOnLongClickListener { view: View ->

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


