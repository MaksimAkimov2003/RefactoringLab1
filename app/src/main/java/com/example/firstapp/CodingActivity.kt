package com.example.firstapp


import algorithms.main
import algorithms.sortAllViewList
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import kotlinx.android.synthetic.main.view_array_operator.view.*
import kotlinx.android.synthetic.main.view_assignment_operator.view.*
import kotlinx.android.synthetic.main.view_declare_integer.view.*
import kotlinx.android.synthetic.main.view_for_close.view.*
import kotlinx.android.synthetic.main.view_for_operator.view.*
import kotlinx.android.synthetic.main.view_if_close.view.*
import kotlinx.android.synthetic.main.view_if_operator.view.*
import kotlinx.android.synthetic.main.view_output.view.*

val buttonVarDragMessage = "buttonVar Added"

// ниже - теги
val tagDeclareIntegerView = "Declare"
val tagArithmeticOperationsView = "ArithmeticOperations"
val tagAssignmentOperatorView = "AssignmentOperator"
val tagIfOperatorView = "IF"
val tagIfCloseView = "IFend"
val tagArrayOperatorView = "CreateArray"
val tagForOperatorView = "ForLoop"
val tagForCloseView = "ForEnd"
val tagOutputView = "Output"

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
            getAndConvertData(allViews)
        }

        binding.apply {
            binding.toolbar.iconMenu.setOnClickListener{
                mainLayout.openDrawer(GravityCompat.START)
            }
        }

        binding.mainLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent))
        setRecyclerView()
    }

    val obectList = listOf(
        Obectsi("Variables"),
        Obectsi("Operations"),
        Obectsi("Conditions"),
        Obectsi("Input|Output")
    )

    private fun setRecyclerView() {
        binding.recyclerView.adapter = CustomRecyclerAdapter(obectList)
        binding.recyclerView.setHasFixedSize(true)
    }

    fun getAndConvertData(allViews: MutableList<View>) {
        var errorFlag = false
        var errorMessage: String

        if (allViews.size == 0) {
            errorFlag = true
            errorMessage = "Нет добавленных view"

            showToast(errorMessage)
        }

        else {
            sortAllViewList(allViews, 0, allViews.size - 1)

            var dataSet: MutableList<MutableList<String>> = mutableListOf()
            var previousView = allViews[0]
            var flag = false

            for (view in allViews) {
                var arrayForView: MutableList<String> = mutableListOf()

                if ((flag) && (view.textNumber.text.toString() == previousView.textNumber.text.toString())) {
                    errorMessage = repeatingNumbers

                    showToast(errorMessage + " " + view.textNumber.text.toString())

                    errorFlag = true

                    break

                }

                previousView = view
                flag = true

                arrayForView.add(view.getTag().toString())
                arrayForView.add(view.textNumber.text.toString())


                when(view.getTag()){
                    tagDeclareIntegerView->{
                        """(([A-z]([0-9]|[A-z])*)(\s{0,1},{1}\s{0,1}([A-z]([0-9]|[A-z])*))*)""".toRegex()
                        var myString = view.inputValueDeclare.text.toString()

                        if (!myString.matches(Regex("""(([A-z]([0-9]|[A-z])*)(\s{0,1},{1}\s{0,1}([A-z]([0-9]|[A-z])*))*)"""))) {
                            errorMessage = emptyEditField

                            showToast(errorMessage + " " + view.textNumber.text.toString())

                            errorFlag = true

                            break
                        }

                        arrayForView.add(view.inputValueDeclare.text.toString())
                    }
                    tagArithmeticOperationsView->{
                        arrayForView.add(view.arifOperations2.text.toString())
                    }
                    tagAssignmentOperatorView->{
                        """[A-z]([0-9]|[A-z])*""".toRegex()

                        var myString1 = view.variableOfBlock.text.toString()

                        if ((!myString1.matches(Regex("""[A-z]([0-9]|[A-z])*""")))) {
                            errorMessage = emptyEditField

                            showToast(errorMessage + " " + view.textNumber.text.toString())

                            errorFlag = true

                            break
                        }

                        arrayForView.add(view.variableOfBlock.text.toString())
                        arrayForView.add(view.valueOfBlock.text.toString())
                    }
                    tagIfOperatorView->{
                        arrayForView.addAll(listOf(view.secondCondition.text.toString(), view.spinnerForIf.selectedItem.toString(), view.firstCondition.text.toString()))
                    }
                    tagArrayOperatorView->{

                        """[A-z]([0-9]|[A-z])*""".toRegex()
                        var myString = view.nameOfArray.text.toString()

                        if (!myString.matches(Regex("""[A-z]([0-9]|[A-z])*"""))) {
                            errorMessage = emptyEditField

                            showToast(errorMessage + " " + view.textNumber.text.toString())

                            errorFlag = true

                            break
                        }

                        arrayForView.add(view.nameOfArray.text.toString())
                        arrayForView.add(view.arraySize.text.toString())
                    }
                    tagForOperatorView->{
                        """[A-z]([0-9]|[A-z])*""".toRegex()
                        var myString = view.forOperatorVariable.text.toString()

                        if (!myString.matches(Regex("""[A-z]([0-9]|[A-z])*"""))) {
                            errorMessage = emptyEditField

                            showToast(errorMessage + " " + view.textNumber.text.toString())

                            errorFlag = true

                            break
                        }

                        arrayForView.add(view.forOperatorVariable.text.toString())
                        arrayForView.add(view.forOperatorFrom.text.toString())
                        arrayForView.add(view.forOperatorTo.text.toString())
                    }
                    tagOutputView->{
                        """(([A-z]([0-9]|[A-z])*)(\s{0,1},{1}\s{0,1}([A-z]([0-9]|[A-z])*))*)""".toRegex()
                        var myString = view.variableOutput.text.toString()

                        if (!myString.matches(Regex("""(([A-z]([0-9]|[A-z])*)(\s{0,1},{1}\s{0,1}([A-z]([0-9]|[A-z])*))*)"""))) {
                            errorMessage = emptyEditField

                            showToast(errorMessage + " " + view.textNumber.text.toString())

                            errorFlag = true

                            break
                        }

                        arrayForView.add(view.variableOutput.text.toString())
                    }


                }

                dataSet.add(arrayForView)
            }

            if (!errorFlag) {

                var answer: MutableList<MutableList<String>> = mutableListOf()
                var allVariables: MutableList<MutableList<String>> = mutableListOf()

                main(dataSet, answer, allVariables)


                var answerString = ""
                var allVariablesString = ""

                for (i in 0..answer[0].size - 1){
                    var variable = answer[0][i]
                    var value = answer[1][i]

                    answerString += variable + " " + "=" + " " + value + "\n"

                }

                for (i in 0..allVariables[0].size - 1) {
                    var variable = allVariables[0][i]
                    var value = allVariables[1][i]

                    allVariablesString += variable + " " + "=" + " " + value + "\n"
                }
                this.showAppDialog(
                    title = "All variables:",
                    message = allVariablesString,
                    gravity = Gravity.TOP
                )

                this.showAppDialog(
                    title = "Answer:",
                    message = answerString
                )
            }

        }




    }

    private fun Context.showAppDialog(title: String, message: String, gravity: Int? = null) {
        val builder2 = AlertDialog.Builder(this)
        builder2.setTitle(title)
        builder2.setMessage(message)
        val alertDialog2: AlertDialog = builder2.create()
        alertDialog2.show()
        gravity?.let { alertDialog2.window?.setGravity(it) }
    }

    private fun showToast(errorMessage: String) {
        val mToast = Toast.makeText(this, errorMessage, Toast.LENGTH_LONG)
        mToast.setGravity(Gravity.TOP, 0, 0)
        mToast.show()
    }

    private val addDragListener = View.OnDragListener { view, dragEvent ->
        val draggableItem = dragEvent.localState as View

        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                onDragStarted(draggableItem)
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                onDragEntered()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                onDragExited(draggableItem, view)
                true
            }
            DragEvent.ACTION_DROP -> {
                onDrop(dragEvent, draggableItem, view)
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                onDragEnded(draggableItem, view)
                true
            }
            else -> {
                false
            }

        }
    }

    private fun onDragEnded(draggableItem: View, view: View) {
        draggableItem.visibility = View.VISIBLE
        view.invalidate()
    }

    private fun onDrop(
        dragEvent: DragEvent,
        draggableItem: View,
        view: View?
    ) {
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


            val button = when (draggableItem.getTag()) {
                tagDeclareIntegerView -> draggableItem.deleteBlock1
                tagArithmeticOperationsView -> draggableItem.deleteBlock2
                tagAssignmentOperatorView -> draggableItem.deleteBlock3
                tagIfOperatorView -> draggableItem.deleteBlock4
                tagIfCloseView -> draggableItem.deleteBlock5
                tagArrayOperatorView -> draggableItem.deleteBlock6
                tagForOperatorView -> draggableItem.deleteBlock7
                tagForCloseView -> draggableItem.deleteBlock8
                tagOutputView -> draggableItem.deleteBlock9
                else -> null
            }
            button?.setOnClickListener { removeViewInAllViewsList(draggableItem) }
            number++
        }

        if (parent == binding.dropArea) {
            parent.removeView(draggableItem)
        }

        dropPlace.addView(draggableItem)
    }

    private fun onDragExited(draggableItem: View, view: View) {
        binding.dropArea.alpha = 1.0f
        draggableItem.visibility = View.VISIBLE

        view.invalidate()
    }

    private fun onDragEntered() {
        binding.dropArea.alpha = 0.3f
    }

    private fun onDragStarted(draggableItem: View) {
        draggableItem.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToTop = binding.dropArea.id
            leftToLeft = binding.dropArea.id

            topMargin = 0
            leftMargin = 0
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
            0 -> { //variables
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


                val arrayOperatorView = arrayOperator(holder.itemView.context)

                val paramsArrayOperatorView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsArrayOperatorView.topToTop = holder.expendConVar.id
                paramsArrayOperatorView.leftToLeft = holder.expendConVar.id

                paramsArrayOperatorView.topMargin = 400
                paramsArrayOperatorView.leftMargin = 0

                holder.expendConVar.addView(arrayOperatorView, paramsArrayOperatorView)
                arrayOperatorView.setTag(tagArrayOperatorView)
                attachViewDragListener(arrayOperatorView)






            }
            1 -> { // Opetations
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConOperations.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConInputOutput.visibility = View.GONE
                holder.expendConConditions.visibility = View.GONE

                val assignmentOperatorView = assignmentOperator(holder.itemView.context)

                val paramsDeclareAssignOperatorView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsDeclareAssignOperatorView.topToTop = holder.expendConOperations.id
                paramsDeclareAssignOperatorView.leftToLeft = holder.expendConOperations.id

                paramsDeclareAssignOperatorView.topMargin = 100
                paramsDeclareAssignOperatorView.leftMargin = 0

                holder.expendConOperations.addView(assignmentOperatorView, paramsDeclareAssignOperatorView)
                assignmentOperatorView.setTag(tagAssignmentOperatorView)
                attachViewDragListener(assignmentOperatorView)


                val forOperatorView = forOperator(holder.itemView.context)

                val paramsForOperatorView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsForOperatorView.topToTop = holder.expendConOperations.id
                paramsForOperatorView.leftToLeft = holder.expendConOperations.id

                paramsForOperatorView.topMargin = 400
                paramsForOperatorView.leftMargin = 0

                holder.expendConOperations.addView(forOperatorView, paramsForOperatorView)
                forOperatorView.setTag(tagForOperatorView)
                attachViewDragListener(forOperatorView)


                val forCloseView = forClose(holder.itemView.context)

                val paramsForCloseView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsForCloseView.topToTop = holder.expendConOperations.id
                paramsForCloseView.leftToLeft = holder.expendConOperations.id

                paramsForCloseView.topMargin = 800
                paramsForCloseView.leftMargin = 0

                holder.expendConOperations.addView(forCloseView, paramsForCloseView)
                forCloseView.setTag(tagForCloseView)
                attachViewDragListener(forCloseView)

            }
            2 ->{ // Conditions
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConConditions.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConInputOutput.visibility = View.GONE
                holder.expendConOperations.visibility = View.GONE

                val ifOperatorView = ifOperator(holder.itemView.context)

                val paramsIfOperatorView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsIfOperatorView.topToTop =  holder.expendConConditions.id
                paramsIfOperatorView.leftToLeft =  holder.expendConConditions.id

                paramsIfOperatorView.topMargin = 280
                paramsIfOperatorView.leftMargin = 0

                holder.expendConConditions.addView(ifOperatorView, paramsIfOperatorView)
                ifOperatorView.setTag(tagIfOperatorView)
                attachViewDragListener(ifOperatorView)


                val ifCloseView = ifClose(holder.itemView.context)

                val paramsIfCloseView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsIfCloseView.topToTop =  ifOperatorView.id
                paramsIfCloseView.leftToLeft =  holder.expendConConditions.id

                paramsIfCloseView.topMargin = 1000
                paramsIfCloseView.leftMargin = 0

                holder.expendConConditions.addView(ifCloseView, paramsIfCloseView)
                ifCloseView.setTag(tagIfCloseView)
                attachViewDragListener(ifCloseView)


            }
            3 ->{ // Input/Output
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConInputOutput.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConOperations.visibility = View.GONE
                holder.expendConConditions.visibility = View.GONE

                val outputView = output(holder.itemView.context)

                val paramsOutputView: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
                    )

                paramsOutputView.topToTop = holder.expendConInputOutput.id
                paramsOutputView.leftToLeft = holder.expendConInputOutput.id

                paramsOutputView.topMargin = 10
                paramsOutputView.leftMargin = 0

                holder.expendConInputOutput.addView(outputView, paramsOutputView)
                outputView.setTag(tagOutputView)
                attachViewDragListener(outputView)



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


