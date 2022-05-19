package algorithms

import android.view.View
import com.example.firstapp.CodingActivity

class DataClass {
    var name: MutableList<String> = mutableListOf()
    var value: MutableList<Double> = mutableListOf()

    private fun findValue(nameVar : String): Int {
        for(i in 0..name.size-1) {
            if (name[i] == nameVar) {
                return i
            }
        }
        return -1
    }

    private fun calc(input: String): String {
        val rpn = invertStack(toRPN(input))
        val result = Stack()
        var msgError = ""

        try {
            while(!rpn.isEmpty()) {
                val item = rpn.pop()
                if(!item.isEmpty()) {
                    if(item[0].isDigit() || (item[0] == '.')) {
                        result.push(item)
                        continue
                    }

                    val num2 = result.pop().toDouble()
                    val num1 = result.pop().toDouble()

                    if(num2 == 0.0) {

                        break
                    }

                    @Suppress("IMPLICIT_CAST_TO_ANY")
                    result.push(when(item[0]) {
                        '+' -> num1+num2
                        '-' -> num1-num2
                        '*' -> num1*num2
                        '/' -> num1/num2
                        '%' -> num1%num2
                        else -> 0
                    }.toString())
                }
            }
        } catch (e: Exception) {

        }

        return if(msgError.isEmpty()) {
            result.pop()
        } else {
            msgError
        }
    }

    private fun toRPN(expr: String): Stack {
        val result = Stack()
        val stack = SymbolsStack()
        var buffer = ""

        for(ch: Char in expr) {
            if(ch.isDigit() || (ch == '.')) {
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

    fun declaration(nameVars : String) {
        var temp = ""
        for(i in nameVars.indices) {
            if (nameVars[i] == ',') {
                if (findValue(temp) == -1) {
                    name.add(temp)
                    value.add(0.0)
                }
                temp = ""
            }
            else if (nameVars[i] == ' ') {
                continue
            }
            else if (i == nameVars.length-1){
                temp += nameVars[i]
                if (findValue(temp) == -1) {
                    name.add(temp);
                    value.add(0.0)
                }
            }
            else {
                temp += nameVars[i]
            }
        }
    }

    private fun varReplacement(input : String) : String {
        var s = input
        for(i in 0..name.size-1) {
            s = Regex(name[i]).replace(s, value[i].toString())
        }
        s = Regex(" ").replace(s, "")
        return s
    }

    fun assignment(nameVar : String, valueVar : String) {
        var newValueVar = varReplacement(valueVar);
        var finalValue = calc(newValueVar);
        value[findValue(nameVar)] = finalValue.toDouble()
    }


    fun output(nameVars : String): MutableList<Double> {
        var temp = ""
        var values : MutableList<Double> = mutableListOf()
        for(i in nameVars.indices) {
            if (nameVars[i] == ',') {
                values.add(value[findValue(temp)])
                temp = ""
            }
            else if (nameVars[i] == ' ') {
                continue
            }
            else if (i == nameVars.length-1){
                temp += nameVars[i]
                values.add(value[findValue(temp)])
            }
            else {
                temp += nameVars[i]
            }
        }
        return values // он вернет массив значений введеных переменных типа Double, но некоторые
        // например, будут x = 24.0, вам нужно сделать проверку
        // if (x.toInt().toDouble() == x) x = x.toInt()
        // чтобы пользователю уже вышло без точки x = 24
    }
}

class Queue {
    var queue: MutableList<MutableList<String>> = mutableListOf()
    var size : Int = 0

    fun push(element : MutableList<String>) {
        queue.add(element)
        size += 1
    }

    fun size(): Int {
        return size
    }
}

fun main(dataSet: MutableList<View>) {
    var codingActivity = CodingActivity()
    var DataSet = dataSet

    var q = Queue()
    q.queue = codingActivity.getAndConvertData(DataSet)

    for(i in 0..q.queue.size - 1) {
        println(q.queue[i])
    }

    var data = DataClass()
    //var temp1 : MutableList<String> = mutableListOf()
    //temp1.add("0")
    //temp1.add("x, y")
    //q.push(temp1)
    //var temp2: MutableList<String> = mutableListOf()
    //temp2.add("1")
    //temp2.add("x")
    //temp2.add("12")
    //q.push(temp2)
    //var temp3: MutableList<String> = mutableListOf()
    //temp3.add("1")
    //temp3.add("y")
    //temp3.add("6")
    //q.push(temp3)
    //var temp4 : MutableList<String> = mutableListOf()
    //temp4.add("0")
    //temp4.add("z")
    //q.push(temp4)
    //var temp5 : MutableList<String> = mutableListOf()
    //temp5.add("1")
    //temp5.add("z")
    //temp5.add("x-y+1000.12")
    //q.push(temp5)
    //var temp6 : MutableList<String> = mutableListOf()
    //temp6.add("0")
    //temp6.add("z")
    //q.push(temp6)
    for(i in 0..q.size()-1) {
        var temp: MutableList<String> = mutableListOf()
        temp = q.queue[i]
        when {
            temp[0] == "" -> { // пустой блок
                continue
            }
            temp[0] == "Declare" -> { // обозначение типа блока объявления переменной
                data.declaration(temp[2])
            }
            temp[0] == "AssignmentOperator" -> { // обозначение блока присваивания
                data.assignment(temp[2], temp[3])
            }
            temp[0] == "Output" -> { // обозначение блока вывода
                data.output(temp[2])
            }
        }
    }
}


