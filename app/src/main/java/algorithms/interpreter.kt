//package algorithms
//
//import android.R
//
//class DataClass {
//    var name: MutableList<String> = mutableListOf()
//    var value: MutableList<Int> = mutableListOf()
//
//    private fun findValue(nameVar : String): Int {
//        for(i in 0..name.size) {
//            if (name[i] == nameVar) {
//                return i
//            }
//        }
//        return -1
//    }
//
//    fun declaration(nameVars : String) {
//        var temp = ""
//        for(i in nameVars.indices) {
//            if (nameVars[i] == ',') {
//                name.add(temp)
//                value.add(0)
//                temp = ""
//            }
//            else if (nameVars[i] == ' ') {
//                continue
//            }
//            else if (i == nameVars.length-1){
//                temp += nameVars[i]
//                name.add(temp);
//                value.add(0)
//            }
//            else {
//                temp += nameVars[i]
//            }
//        }
//    }
//
//    fun assignment(nameVar : String, valueVar : Int) {
//                value[findValue(nameVar)] = valueVar
//    }
//
//    fun output(nameVars : String): MutableList<Int> {
//        var temp = ""
//        var values : MutableList<Int> = mutableListOf()
//        for(i in nameVars.indices) {
//            if (nameVars[i] == ',') {
//                values.add(value[findValue(temp)])
//                temp = ""
//            }
//            else if (nameVars[i] == ' ') {
//                continue
//            }
//            else if (i == nameVars.length-1){
//                temp += nameVars[i]
//                values.add(value[findValue(temp)])
//            }
//            else {
//                temp += nameVars[i]
//            }
//        }
//        return values
//    }
//}
//
//class Queue {
//    var queue: MutableList<MutableList<String>> = mutableListOf()
//
//    fun push(element : MutableList<String>) {
//        queue.add(element)
//    }
//
//    fun size(): Int {
//        return queue.size
//    }
//}
//
//fun main() {
//    var q = Queue()
//    var data = DataClass()
//
//    for(i in 0..q.size()) {
//        var temp: MutableList<String> = mutableListOf()
//        temp = q.queue[i]
//        if (temp[0] == "0") { // 0 - обозначение типа блока объявления переменной
//            data.declaration(temp[1])
//        }
//        if (temp[0] == "1") { // 1 - обозначение блока присваивания
//            data.assignment(temp[1], temp[2].toInt())
//        }
//        if (temp[0] == "2"){ // 2 - обозначение блока вывода
//            data.output(temp[1])
//        }
//    }
//}
//
//private fun calc(input: String): String {
//    val rpn = invertStack(toRPN(input))
//    val result = Stack()
//    var msgError = ""
//
//    try {
//        while(!rpn.isEmpty()) {
//            val item = rpn.pop()
//            if(!item.isEmpty()) {
//                if(item[0].isDigit()) {
//                    result.push(item)
//                    continue
//                }
//
//                val num2 = result.pop().toDouble()
//                val num1 = result.pop().toDouble()
//
//                if(num2 == 0.0) {
//                    msgError = resources.getString(R.string.msg_error_zero)
//                    break
//                }
//
//                @Suppress("IMPLICIT_CAST_TO_ANY")
//                result.push(when(item[0]) {
//                    '+' -> num1+num2
//                    '-' -> num1-num2
//                    '*' -> num1*num2
//                    '/' -> num1/num2
//                    '%' -> num1%num2
//                    else -> 0
//                }.toString())
//            }
//        }
//    } catch (e: Exception) {
//        msgError = resources.getString(R.string.msg_error_unknown)
//    }
//
//    return if(msgError.isEmpty()) {
//        result.pop()
//    } else {
//        msgError
//    }
//}
//
//private fun toRPN(expr: String): Stack {
//    val result = Stack()
//    val stack = SymbolsStack()
//    var buffer = ""
//
//    for(ch: Char in expr) {
//        if(ch.isDigit()) {
//            buffer += ch.toString()
//            continue
//        }
//
//        result.push(buffer)
//        buffer = ""
//        if(stack.isEmpty() || ch == '(') {
//            stack.push(ch)
//        } else if(ch == ')') {
//            loop@while(!stack.isEmpty()) {
//                val last = stack.pop()
//                when(last) {
//                    '(' -> break@loop
//                    else -> result.push(last.toString())
//                }
//            }
//        } else {
//            if(stack.comparePriority(ch)) {
//                stack.push(ch)
//            } else {
//                while(!stack.isEmpty()) {
//                    if(!stack.comparePriority(ch)) {
//                        result.push(stack.pop().toString())
//                    } else {
//                        break
//                    }
//                }
//                stack.push(ch)
//            }
//        }
//    }
//
//    result.push(buffer)
//    while(!stack.isEmpty()) {
//        result.push(stack.pop().toString())
//    }
//    return result
//}
//
//private fun invertStack(stack: Stack): Stack {
//    val result = Stack()
//    while(!stack.isEmpty()) result.push(stack.pop())
//    return result
//}
