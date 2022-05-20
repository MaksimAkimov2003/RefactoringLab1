package algorithms

import android.view.View
import com.example.firstapp.CodingActivity

const val ifCondition = "IF"
const val declare = "Declare"
const val assignmentOperator = "AssignmentOperator"
const val output = "Output"
const val ifEnd = "IFend"
const val forLoop = "ForLoop"
const val forEnd = "ForEnd"
const val createArray = "CreateArray"

class DataClass {
    var name: MutableList<String> = mutableListOf()
    var value: MutableList<Double> = mutableListOf()

    fun findValue(nameVar : String): Int {
        for(i in 0..name.size-1) {
            if (name[i] == nameVar) {
                return i
            }
        }
        return -1
    }

    fun calc(input: String): String {
        var s = Regex(" ").replace(input, "")
        val rpn = invertStack(toRPN(s))
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
                else {
                    value[findValue(temp)] = 0.0
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
                else {
                    value[findValue(temp)] = 0.0
                }
            }
            else {
                temp += nameVars[i]
            }
        }
    }

    fun varReplacement(input : String) : String {
        var s = input
        for (i in 0..name.size - 1) {
            if (name[i] != "") {
                s = Regex(name[i]).replace(s, value[i].toString())
            }
        }
        s = Regex(" ").replace(s, "")
        return s
    }

    fun assignment(nameVar : String, valueVar : String) {
        var newValueVar = varReplacement(valueVar);
        for(i in 0..5) {
            newValueVar = varReplacement(newValueVar);
        }
        var finalValue = calc(newValueVar)
        var nameV = nameVar
        if (findValue(nameV) == -1) {
            for (i in 0..name.size - 1) {
                if ((Regex(name[i]).find(nameV) != null) && (name[i] != "")) {
                    nameV = Regex(name[i]).replace(nameV, value[i].toInt().toString())
                    break
                }
            }
        }
        value[findValue(nameV)] = finalValue.toDouble()
    }

    fun deleteVar(nameVar: String) {
        value[findValue(nameVar)] = 0.0
        name[findValue(nameVar)] = ""
    }

    fun condition(firstBlock : String, cond : String, secBlock : String, index : Int, q: Queue): Int {
        var counterCycle = 1
        var indexOfEnd = 0
        for(i in index+1..q.queue.size-1) {
            if (q.queue[i][0] == ifCondition) {
                counterCycle += 1
            }
            if (q.queue[i][0] == ifEnd) {
                counterCycle -= 1
                if (counterCycle == 0) {
                    indexOfEnd = i
                    break
                }
            }
        }
        when {
            cond == "==" -> {
                if (calc(varReplacement(firstBlock)) != calc(varReplacement(secBlock))) {
                    return indexOfEnd
                }
            }
            cond == "!=" -> {
                if (calc(varReplacement(firstBlock)) == calc(varReplacement(secBlock))) {
                    return indexOfEnd
                }
            }
            cond == ">=" -> {
                if (calc(varReplacement(firstBlock)) < calc(varReplacement(secBlock))) {
                    return indexOfEnd
                }
            }
            cond == "<=" -> {
                if (calc(varReplacement(firstBlock)) > calc(varReplacement(secBlock))) {
                    return indexOfEnd
                }
            }
            cond == ">" -> {
                if (calc(varReplacement(firstBlock)) <= calc(varReplacement(secBlock))) {
                    return indexOfEnd
                }
            }
            cond == "<" -> {
                if (calc(varReplacement(firstBlock)) >= calc(varReplacement(secBlock))) {
                    return indexOfEnd
                }
            }
        }
        return -1
    }

    fun loop (q: Queue, index : Int) : Int {
        var counterCycle = 1
        var indexOfEnd = 0
        for(i in index+1..q.queue.size-1) {
            if (q.queue[i][0] == forLoop) {
                counterCycle += 1
            }
            if (q.queue[i][0] == forEnd) {
                counterCycle -= 1
                if (counterCycle == 0) {
                    indexOfEnd = i
                    break
                }
            }
        }
        return indexOfEnd
    }

    fun newArray(nameArray : String, countValues : String) {
        for(i in 0..countValues.toInt()-1) {
            var arrayElem = "$nameArray[$i]"
            declaration(arrayElem)
        }
    }

    fun output(nameVars : String, answer: MutableList<MutableList<String>> = mutableListOf(), allVariables: MutableList<MutableList<String>> = mutableListOf()) {
        var temp = ""
        for(i in nameVars.indices) {
            if (nameVars[i] == ',') {
                if (value[findValue(temp)].toInt().toDouble() == value[findValue(temp)]) {
                    answer[1].add(value[findValue(temp)].toInt().toString())
                }
                else {
                    answer[1].add(value[findValue(temp)].toString())
                }
                answer[0].add(name[findValue(temp)])
                temp = ""
            }
            else if (nameVars[i] == ' ') {
                continue
            }
            else if (i == nameVars.length-1){
                temp += nameVars[i]
                if (value[findValue(temp)].toInt().toDouble() == value[findValue(temp)]) {
                    answer[1].add(value[findValue(temp)].toInt().toString())
                }
                else {
                    answer[1].add(value[findValue(temp)].toString())
                }
                answer[0].add(name[findValue(temp)])
            }
            else {
                temp += nameVars[i]
            }
        }
        answer
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

    fun paste(new: MutableList<MutableList<String>> = mutableListOf(), index : Int) {
        var newArray : MutableList<MutableList<String>> = mutableListOf()
        for(i in 0..index-1) {
            newArray.add(queue[i])
        }
        var count = 0
        for(i in index..index+new.size-1) {
            newArray.add(new[count])
            count++
        }
        count = index
        for(i in index+new.size..queue.size+new.size-1) {
            newArray.add(queue[count])
            count++;
        }
    }
}

fun main(dataSet: MutableList<MutableList<String>>, answer: MutableList<MutableList<String>> = mutableListOf(), allVariables: MutableList<MutableList<String>> = mutableListOf()) {
    var q = Queue()
    q.queue = dataSet

    var t : MutableList<String> = mutableListOf()
    for(i in 0..1) {
        answer.add(t)
        allVariables.add(t)
    }

    var data = DataClass()
    var tempc: MutableList<String> = mutableListOf()
    tempc.add(declare)
    tempc.add("0")
    tempc.add("x")
    q.queue.add(tempc)
    var temp1: MutableList<String> = mutableListOf()
    temp1.add(forLoop)
    temp1.add("0")
    temp1.add("i")
    temp1.add("1")
    temp1.add("3")
    q.queue.add(temp1)
    var temp3: MutableList<String> = mutableListOf()
    temp3.add(assignmentOperator)
    temp3.add("0")
    temp3.add("x")
    temp3.add("x + 3")
    q.queue.add(temp3)
    var temp2: MutableList<String> = mutableListOf()
    temp2.add(forEnd)
    temp2.add("0")
    q.queue.add(temp2)
    println(q.queue)
    var i = 0
    while(true) {
        var temp: MutableList<String> = mutableListOf()
        temp = q.queue[i]
        when {
            temp[0] == "" -> { // пустой блок
                i++
                if (i == q.queue.size-1) {
                    break
                }
                continue
            }
            temp[0] == declare -> { // обозначение типа блока объявления переменной
                data.declaration(temp[2])
            }
            temp[0] == assignmentOperator -> { // обозначение блока присваивания
                data.assignment(temp[2], temp[3])
            }
            temp[0] == output -> { // обозначение блока вывода
                data.output(temp[2])
            }
            temp[0] == ifCondition -> {
                var flag = data.condition(temp[2], temp[3], temp[4], i, q)
                if (flag != -1) {
                    for(j in i+1..flag) {
                        q.queue[j][0] = ""
                    }
                }
            }
            temp[0] == ifEnd -> {
                i++
                if (i == q.queue.size-1) {
                    break
                }
                continue
            }
            temp[0] == forLoop -> {
                data.declaration(temp[2])
                val start = data.calc(data.varReplacement(temp[3])).toInt()
                val finish = data.calc(data.varReplacement(temp[4])).toInt()
                var new : MutableList<MutableList<String>> = mutableListOf()
                for(k in start..finish) {
                    var newBlock : MutableList<String> = mutableListOf()
                    newBlock.add(assignmentOperator)
                    newBlock.add("0")
                    newBlock.add(temp[2])
                    newBlock.add(k.toString())
                    new.add(newBlock)
                    for (j in i + 1..data.loop(q, i) - 1) {
                        var t : MutableList<String> = mutableListOf()
                        for(h in 0..q.queue[j].size-1) {
                            t.add(q.queue[j][h])
                        }
                        new.add(t)
                    }
                }
                var newBlock : MutableList<String> = mutableListOf()
                newBlock.add(assignmentOperator)
                newBlock.add("0")
                newBlock.add(temp[2])
                newBlock.add(finish.toString())
                new.add(newBlock)
                q.paste(new, i+1)
            }
            temp[0] == forEnd -> {
                i++
                if (i == q.queue.size-1) {
                    break
                }
                continue
            }
            temp[0] == createArray -> {
                data.newArray(temp[2], temp[3])
            }
        }
        i++
        if (i == q.queue.size-1) {
            break
        }
    }
    for(i in 0..data.name.size-1) {
        if (data.name[i] != "") {
            allVariables[0].add(data.name[i])
            allVariables[1].add(data.value[data.findValue(data.name[i])].toString())
        }
    }
    println(data.name)
    println(data.value)
}


