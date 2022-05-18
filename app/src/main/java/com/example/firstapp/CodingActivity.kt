package com.example.firstapp


import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.CustomViews.*
import com.example.firstapp.databinding.ActivityCodingBinding
import com.example.firstapp.databinding.RecyclerviewItemBinding
import kotlinx.android.synthetic.main.view_arithmetic_operations.view.*

val buttonVarDragMessage = "buttonVar Added"
val tagDeclareIntegerView = "DeclareInteger"
val tagArithmeticOperationsView = "ArithmeticOperations"
val tagAssignmentOperatorView = "AssignmentOperator"
class CodingActivity : AppCompatActivity(){




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


        binding.apply {
            binding.toolbar.iconMenu.setOnClickListener{
                mainLayout.openDrawer(GravityCompat.START)
            }
        }




        binding.mainLayout.setScrimColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent))


        //Создание ресайклера
        initData()
        setRecyclerView()



        //Добавление customView на констрэинты





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


    fun startingAddViews(view: View, params : ConstraintLayout.LayoutParams, tag : String) {
        view.setTag(tag)
        bindRecItem.expandConstraint1.addView(view,params)
        attachViewDragListener(view)
    }

    fun VarAddedSet() {



        val declareIntegerView = declareInteger(this)

        val paramsDeclareIntegerView : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
            ConstraintLayout.LayoutParams.WRAP_CONTENT //height
        )


        paramsDeclareIntegerView.topToTop =  bindRecItem.expandConstraint1.id
        paramsDeclareIntegerView.leftToLeft = bindRecItem.expandConstraint1.id



        startingAddViews(declareIntegerView, paramsDeclareIntegerView, tagDeclareIntegerView)

    }




//    private fun OperAddedSet() {
//        val declareIntegerView = declareInteger(this)
//
//        val paramsDeclareIntegerView : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
//            ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
//            ConstraintLayout.LayoutParams.WRAP_CONTENT //height
//        )
//
//        val titl = listOf("Variables", "Operations", "Conditions", "Input|Output")
//
//        val RecAdptr = CustomRecyclerAdapter(titl)
//        val recConstraint = RecAdptr.getConstraint(0)
//
//
//        paramsDeclareIntegerView.topToTop =   .id
//                paramsDeclareIntegerView.leftToLeft = binding.helper.id
//
//
//
//        paramsDeclareIntegerView.topMargin = 100
//        paramsDeclareIntegerView.leftMargin = 0
//
//        startingAddViews(declareIntegerView, paramsDeclareIntegerView, tagDeclareIntegerView, constr)
//
//
//        val assignmentOperatorView = assignmentOperator(this)
//
//        val paramsAssignmentOperatorView : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
//            ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
//            ConstraintLayout.LayoutParams.WRAP_CONTENT //height
//        )
//
////        paramsAssignmentOperatorView.topToTop = binding.helper.id
////        paramsAssignmentOperatorView.leftToLeft = binding.helper.id
//
//        paramsAssignmentOperatorView.topToTop = constr.id
//        paramsAssignmentOperatorView.leftToLeft = constr.id
//
//        paramsAssignmentOperatorView.topMargin = 400
//        paramsAssignmentOperatorView.leftMargin = 0
//
//        startingAddViews(assignmentOperatorView, paramsAssignmentOperatorView, tagAssignmentOperatorView, constr)
//
//
//        val arithmeticOperationsView = arithmeticOperations(this)
//
//        val paramsArithmeticOperationsView : ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
//            ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
//            ConstraintLayout.LayoutParams.WRAP_CONTENT //height
//        )
//
////        paramsArithmeticOperationsView.topToTop = binding.helper.id
////        paramsArithmeticOperationsView.leftToLeft = binding.helper.id
//
//        paramsArithmeticOperationsView.topToTop = constr.id
//        paramsArithmeticOperationsView.leftToLeft = constr.id
//
//        paramsArithmeticOperationsView.topMargin = 700
//        paramsArithmeticOperationsView.leftMargin = 0
//
//        startingAddViews(arithmeticOperationsView, paramsArithmeticOperationsView, tagArithmeticOperationsView, constr)
//    }


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

                if (parent != binding.dropArea) {
                    parent.removeAllViews()

                }

                parent.removeView(draggableItem)


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

//                val whileView = whileLoop(holder.itemView.context)
//
//                val paramsWhileView: ConstraintLayout.LayoutParams =
//                    ConstraintLayout.LayoutParams(
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
//                    )
//
//                paramsWhileView.topToTop = holder.expendConOperations.id
//                paramsWhileView.leftToLeft = holder.expendConOperations.id
//
//                paramsWhileView.topMargin = 700
//                paramsWhileView.leftMargin = 0



                holder.expendConOperations.addView(declareArithmeticView, paramsDeclareArithmeticView)
                attachViewDragListener(declareArithmeticView)

                holder.expendConOperations.addView(assignmentOperatorView, paramsDeclareAssignOperatorView)
                attachViewDragListener(assignmentOperatorView)

//                holder.expendConOperations.addView(whileView, paramsWhileView)
//                attachViewDragListener(whileView)



            }
            2 ->{
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConConditions.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConInputOutput.visibility = View.GONE
                holder.expendConOperations.visibility = View.GONE

//                val declareIfView = ifOperator(holder.itemView.context)
//
//                val paramsDeclareIfView: ConstraintLayout.LayoutParams =
//                    ConstraintLayout.LayoutParams(
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
//                    )
//
//                paramsDeclareIfView.topToTop = holder.expendConConditions.id
//                paramsDeclareIfView.leftToLeft = holder.expendConConditions.id
//
//                paramsDeclareIfView.topMargin = 100
//                paramsDeclareIfView.leftMargin = 0
//
//
//
//                holder.expendConConditions.addView(declareIfView, paramsDeclareIfView)
//                attachViewDragListener(declareIfView)

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
                attachViewDragListener(declareArithmeticView)

                holder.expendConConditions.addView(assignmentOperatorView, paramsDeclareAssignOperatorView)
                attachViewDragListener(assignmentOperatorView)

            }
            3 ->{
                val isExpandeble: Boolean = names[position].expandeble
                holder.expendConInputOutput.visibility = if (isExpandeble) View.VISIBLE else View.GONE
                holder.expendConVar.visibility = View.GONE
                holder.expendConOperations.visibility = View.GONE
                holder.expendConConditions.visibility = View.GONE

//                val declareOutputView = output(holder.itemView.context)
//
//                val paramsDeclareOutputView: ConstraintLayout.LayoutParams =
//                    ConstraintLayout.LayoutParams(
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT, //width
//                        ConstraintLayout.LayoutParams.WRAP_CONTENT //height
//                    )
//
//                paramsDeclareOutputView.topToTop = holder.expendConInputOutput.id
//                paramsDeclareOutputView.leftToLeft = holder.expendConInputOutput.id
//
//                paramsDeclareOutputView.topMargin = 100
//                paramsDeclareOutputView.leftMargin = 0
//
//
//
//                holder.expendConInputOutput.addView(declareOutputView, paramsDeclareOutputView)
//                attachViewDragListener(declareOutputView)

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
                attachViewDragListener(declareArithmeticView)

                holder.expendConInputOutput.addView(assignmentOperatorView, paramsDeclareAssignOperatorView)
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


