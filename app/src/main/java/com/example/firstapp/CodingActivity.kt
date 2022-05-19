package com.example.firstapp


import algorithms.main
import algorithms.sortAllViewList
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.CustomViews.*
import com.example.firstapp.databinding.ActivityCodingBinding
import com.example.firstapp.databinding.RecyclerviewItemBinding
import kotlinx.android.synthetic.main.activity_coding.*
import kotlinx.android.synthetic.main.view_arithmetic_operations.view.*
import kotlinx.android.synthetic.main.view_arithmetic_operations.view.textNumber
import kotlinx.android.synthetic.main.view_assignment_operator.view.*
import kotlinx.android.synthetic.main.view_declare_integer.view.*
import kotlinx.android.synthetic.main.view_if_close.view.*
import kotlinx.android.synthetic.main.view_if_operator.view.*

val buttonVarDragMessage = "buttonVar Added"

// ниже - теги
val tagDeclareIntegerView = "Declare"
val tagArithmeticOperationsView = "ArithmeticOperations"
val tagAssignmentOperatorView = "AssignmentOperator"

// ниже - сообщения ошибок
val emptyEditField = "Неверно заполненное поле"
val repeatingNumbers = "Повторяется номер блока"

class CodingActivity : AppCompatActivity(){
    var allViews: MutableList<View> = mutableListOf()
    var number = 1

    private lateinit var binding: ActivityCodingBinding
    private lateinit var bindRecItem: RecyclerviewItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodingBinding.inflate(layoutInflater)
        bindRecItem = RecyclerviewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.dropArea.setOnDragListener(addDragListener)




//        binding.inputButton.setOnClickListener {
//            hideKeyboard(it)
//        }

        binding.button2.setOnClickListener{
//            main()
            getAndConvertData(allViews)
        }

        binding.apply {
            binding.toolbar.iconMenu.setOnClickListener{
                mainLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.mainLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent))

        initData()
        setRecyclerView()

    }

    val obectList = ArrayList<Obectsi>()

    private fun setRecyclerView() {
        binding.recyclerView.adapter = CustomRecyclerAdapter(obectList)
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initData() {

        obectList.add(
            Obectsi(
                "Variables"
            ))
        obectList.add(
            Obectsi(
                "Operations"

            ))
        obectList.add(
            Obectsi(
                "Conditions"

            ))
        obectList.add(
            Obectsi(
                "Input|Output"
            ))
    }

    fun getAndConvertData(allViews: MutableList<View>) {
        sortAllViewList(allViews, 0, allViews.size - 1)

        var errorFlag = false
        var errorMessage: String

        var dataSet: MutableList<MutableList<String>> = mutableListOf()
        var previousView = allViews[0]
        var flag = false

        for (view in allViews) {
            var arrayForView: MutableList<String> = mutableListOf()

            if ((flag) && (view.textNumber.text.toString() == previousView.textNumber.text.toString())) {
                errorMessage = repeatingNumbers

                val mToast = Toast.makeText(this, errorMessage + " " + view.textNumber.text.toString(), Toast.LENGTH_LONG)
                mToast.setGravity(Gravity.TOP, 0, 0)
                mToast.show()

                errorFlag = true

                break

            }

            previousView = view
            flag = true

            arrayForView.add(view.getTag().toString())
            arrayForView.add(view.textNumber.text.toString())

            if (view.getTag() == tagDeclareIntegerView) {
                """[A-z]([0-9]|[A-z])*""".toRegex()
                var myString = view.inputValueDeclare.text.toString()

                if (!myString.matches(Regex("""[A-z]([0-9]|[A-z])*"""))) {
                    errorMessage = emptyEditField

                    val mToast = Toast.makeText(this, errorMessage + " " + view.textNumber.text.toString(), Toast.LENGTH_LONG)
                    mToast.setGravity(Gravity.TOP, 0, 0)
                    mToast.show()

                    errorFlag = true

                    break
                }

                arrayForView.add(view.inputValueDeclare.text.toString())
            }

            if (view.getTag() == tagArithmeticOperationsView) {
                arrayForView.add(view.arifOperations2.text.toString())
            }

            if (view.getTag() == tagAssignmentOperatorView) {
                """[A-z]([0-9]|[A-z])*""".toRegex()

                var myString1 = view.variableOfBlock.text.toString()
                var myString2 = view.valueOfBlock.text.toString()

                if ((!myString1.matches(Regex("""[A-z]([0-9]|[A-z])*"""))) || (!myString2.matches(Regex("""([A-z]([0-9]|[A-z])*)|([0-9])""")))) {
                    errorMessage = emptyEditField

                    val mToast = Toast.makeText(this, errorMessage + " " + view.textNumber.text.toString(), Toast.LENGTH_LONG)
                    mToast.setGravity(Gravity.TOP, 0, 0)
                    mToast.show()

                    errorFlag = true

                    break
                }


                arrayForView.add(view.variableOfBlock.text.toString())
                arrayForView.add(view.valueOfBlock.text.toString())


            }

            dataSet.add(arrayForView)
        }

        if (!errorFlag) {
            var answer: MutableList<String> = mutableListOf()
            var allVariables: MutableList<String> = mutableListOf()

//            main(dataSet)
            val mToast = Toast.makeText(this, "Заебись", Toast.LENGTH_LONG)
            mToast.setGravity(Gravity.TOP, 0, 0)
            mToast.show()
        }


    }

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
                val dropPlace = view as ConstraintLayout

                if (parent != binding.dropArea) {
                    parent.removeAllViews()
                    allViews.add(draggableItem)

                    val str = number.toString(10)
                    draggableItem.textNumber.setText(str)

                    if (draggableItem.getTag() == tagDeclareIntegerView){
                        draggableItem.deleteBlock1.setOnClickListener {
                            removeViewInAllViewsList(draggableItem)
                        }
                    }

                    if (draggableItem.getTag() == tagArithmeticOperationsView){
                        draggableItem.deleteBlock2.setOnClickListener {
                            removeViewInAllViewsList(draggableItem)
                        }
                    }

                    if (draggableItem.getTag() == tagAssignmentOperatorView){
                        draggableItem.deleteBlock3.setOnClickListener {
                            removeViewInAllViewsList(draggableItem)
                        }
                    }

                    number++
                }

                if (parent == binding.dropArea) {
                    parent.removeView(draggableItem)
                }

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

    private fun removeViewInAllViewsList(someView: View) {
        val viewNumberValueString = someView.textNumber.text.toString()
        val viewNumberValueInt = viewNumberValueString.toInt()

        allViews.remove(someView)
        binding.dropArea.removeView(someView)

        for (i in 0..allViews.size - 1) {
            var currentNumber = allViews[i].textNumber.text
            var currentNumberString = currentNumber.toString()
            var currentNumberInt = currentNumberString.toInt()

            if (currentNumberInt >= viewNumberValueInt) {
                currentNumberInt -= 1
                currentNumberString = currentNumberInt.toString()
                allViews[i].textNumber.setText(currentNumberString)
            }

        }
        number -= 1

    }

}

fun attachViewDragListener(someView: View) {

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


class CustomRecyclerAdapter(private val names: List<Obectsi>):  RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>(){

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val nameBlocks: TextView = itemView.findViewById(R.id.mainTextView)
        var expendConVar: ConstraintLayout = itemView.findViewById(R.id.expandConstraint1)
        var expendConOperations: ConstraintLayout = itemView.findViewById(R.id.expandConstraint2)
        var expendConConditions: ConstraintLayout = itemView.findViewById(R.id.expandConstraint3)
        var expendConInputOutput: ConstraintLayout = itemView.findViewById(R.id.expandConstraint4)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearId)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemObects: Obectsi = names[position]
        holder.nameBlocks.text = itemObects.name

        when (position) {
            0 -> {
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConVar.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConOperations.visibility = View.GONE
                holder.expendConInputOutput.visibility = View.GONE
                holder.expendConConditions.visibility = View.GONE


                val declareIntegerView = declareInteger(holder.itemView.context)

                val paramsDeclareIntegerView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareIntegerView.topToTop = holder.expendConVar.id
                paramsDeclareIntegerView.leftToLeft = holder.expendConVar.id

                paramsDeclareIntegerView.topMargin = 100
                paramsDeclareIntegerView.leftMargin = 0


                holder.expendConVar.addView(declareIntegerView, paramsDeclareIntegerView)
                declareIntegerView.setTag(tagDeclareIntegerView)
                attachViewDragListener(declareIntegerView)



            }
            1 -> {
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConOperations.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConInputOutput.visibility = View.GONE
                holder.expendConConditions.visibility = View.GONE

                val declareArithmeticView = arithmeticOperations(holder.itemView.context)

                val paramsDeclareArithmeticView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareArithmeticView.topToTop = holder.expendConOperations.id
                paramsDeclareArithmeticView.leftToLeft = holder.expendConOperations.id

                paramsDeclareArithmeticView.topMargin = 100
                paramsDeclareArithmeticView.leftMargin = 0


                val assignmentOperatorView = assignmentOperator(holder.itemView.context)

                val paramsDeclareAssignOperatorView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareAssignOperatorView.topToTop = holder.expendConOperations.id
                paramsDeclareAssignOperatorView.leftToLeft = holder.expendConOperations.id

                paramsDeclareAssignOperatorView.topMargin = 400
                paramsDeclareAssignOperatorView.leftMargin = 0

                holder.expendConOperations.addView(declareArithmeticView, paramsDeclareArithmeticView)
                declareArithmeticView.setTag(tagArithmeticOperationsView)
                attachViewDragListener(declareArithmeticView)

                holder.expendConOperations.addView(assignmentOperatorView, paramsDeclareAssignOperatorView)
                assignmentOperatorView.setTag(tagAssignmentOperatorView)
                attachViewDragListener(assignmentOperatorView)




            }
            2 ->{
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConConditions.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConInputOutput.visibility = View.GONE
                holder.expendConOperations.visibility = View.GONE

                val declareArithmeticView = arithmeticOperations(holder.itemView.context)

                val paramsDeclareArithmeticView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareArithmeticView.topToTop =  holder.expendConConditions.id
                paramsDeclareArithmeticView.leftToLeft =  holder.expendConConditions.id

                paramsDeclareArithmeticView.topMargin = 100
                paramsDeclareArithmeticView.leftMargin = 0


                val assignmentOperatorView = assignmentOperator(holder.itemView.context)

                val paramsDeclareAssignOperatorView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareAssignOperatorView.topToTop =  holder.expendConConditions.id
                paramsDeclareAssignOperatorView.leftToLeft =  holder.expendConConditions.id

                paramsDeclareAssignOperatorView.topMargin = 400
                paramsDeclareAssignOperatorView.leftMargin = 0

                holder.expendConConditions.addView(declareArithmeticView, paramsDeclareArithmeticView)
                declareArithmeticView.setTag(tagArithmeticOperationsView)
                attachViewDragListener(declareArithmeticView)

                holder.expendConConditions.addView(assignmentOperatorView, paramsDeclareAssignOperatorView)
                assignmentOperatorView.setTag(tagAssignmentOperatorView)
                attachViewDragListener(assignmentOperatorView)

            }
            3 ->{
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConInputOutput.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConOperations.visibility = View.GONE
                holder.expendConConditions.visibility = View.GONE

                val declareArithmeticView = arithmeticOperations(holder.itemView.context)

                val paramsDeclareArithmeticView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareArithmeticView.topToTop = holder.expendConInputOutput.id
                paramsDeclareArithmeticView.leftToLeft = holder.expendConInputOutput.id

                paramsDeclareArithmeticView.topMargin = 100
                paramsDeclareArithmeticView.leftMargin = 0


                val assignmentOperatorView = assignmentOperator(holder.itemView.context)

                val paramsDeclareAssignOperatorView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareAssignOperatorView.topToTop = holder.expendConInputOutput.id
                paramsDeclareAssignOperatorView.leftToLeft = holder.expendConInputOutput.id

                paramsDeclareAssignOperatorView.topMargin = 400
                paramsDeclareAssignOperatorView.leftMargin = 0

                holder.expendConInputOutput.addView(declareArithmeticView, paramsDeclareArithmeticView)
                declareArithmeticView.setTag(tagArithmeticOperationsView)
                attachViewDragListener(declareArithmeticView)

                holder.expendConInputOutput.addView(assignmentOperatorView, paramsDeclareAssignOperatorView)
                assignmentOperatorView.setTag(tagAssignmentOperatorView)
                attachViewDragListener(assignmentOperatorView)


            }
        }

        holder.linearLayout.setOnClickListener {
            val obectises = names[position]
            obectises.expandeble = !obectises.expandeble
            notifyItemChanged(position)
        }

    }

    override fun getItemCount() = names.size

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


