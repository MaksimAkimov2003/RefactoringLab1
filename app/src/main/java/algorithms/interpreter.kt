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

    fun findValue(nameVar: String): Int {
        for (i in 0..name.size - 1) {
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
            while (!rpn.isEmpty()) {
                val item = rpn.pop()
                if (!item.isEmpty()) {
                    if (item[0].isDigit() || (item[0] == '.')) {
                        result.push(item)
                        continue
                    }

                    val num2 = result.pop().toDouble()
                    val num1 = result.pop().toDouble()

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

        }

        return if (msgError.isEmpty()) {
            result.pop()
        } else {
            msgError
        }
    }

    private fun toRPN(expr: String): Stack {
        val result = Stack()
        val stack = SymbolsStack()
        var buffer = ""

        for (ch: Char in expr) {
            if (ch.isDigit() || (ch == '.')) {
                buffer += ch.toString()
                continue
            }

            result.push(buffer)
            buffer = ""
            if (stack.isEmpty() || ch == '(') {
                stack.push(ch)
            } else if (ch == ')') {
                loop@ while (!stack.isEmpty()) {
                    val last = stack.pop()
                    when (last) {
                        '(' -> break@loop
                        else -> result.push(last.toString())
                    }
                }
            } else {
                checkStack(stack, ch, result)
            }
        }

        result.push(buffer)
        while (!stack.isEmpty()) {
            result.push(stack.pop().toString())
        }
        return result
    }

    private fun checkStack(stack: SymbolsStack, ch: Char, result: Stack) {
        if (stack.comparePriority(ch)) {
            stack.push(ch)
        } else {
            iterateOnStack(stack, ch, result)
            stack.push(ch)
        }
    }

    private fun iterateOnStack(stack: SymbolsStack, ch: Char, result: Stack) {
        while (!stack.isEmpty()) {
            if (!stack.comparePriority(ch)) {
                result.push(stack.pop().toString())
            } else {
                break
            }
        }
    }

    private fun invertStack(stack: Stack): Stack {
        val result = Stack()
        while (!stack.isEmpty()) result.push(stack.pop())
        return result
    }

    fun declaration(nameVars: String) {
        var temp = ""
        for (i in nameVars.indices) {
            if (nameVars[i] == ',') {
                handleFindValue(temp)
                temp = ""
            } else if (nameVars[i] == ' ') {
                continue
            } else if (i == nameVars.length - 1) {
                temp += nameVars[i]
                if (findValue(temp) == -1) {
                    name.add(temp);
                    value.add(0.0)
                } else {
                    value[findValue(temp)] = 0.0
                }
            } else {
                temp += nameVars[i]
            }
        }
    }

    private fun handleFindValue(temp: String) {
        if (findValue(temp) == -1) {
            name.add(temp)
            value.add(0.0)
        } else {
            value[findValue(temp)] = 0.0
        }
    }

    fun varReplacement(input: String): String {
        var s = input
        for (j in 0..s.length - 1) {
            if (s[j] == '[') {
                for (i in 0..name.size - 1) {
                    if (name[i] != "") {
                        s = Regex(name[i]).replace(s, value[i].toInt().toString())
                    }
                }
                break;
            }
        }
        for (i in 0..name.size - 1) {
            if (name[i] != "") {
                s = Regex(name[i]).replace(s, value[i].toString())
            }
        }
        for (i in 0..name.size - 1) {
            if (name[i] == s) {
                s = value[findValue(name[i])].toString()
            }
        }
        s = Regex(" ").replace(s, "")
        return s
    }

    fun assignment(nameVar: String, valueVar: String) {
        var newValueVar = varReplacement(valueVar);
        for (i in 0..5) {
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

    fun condition(firstBlock: String, cond: String, secBlock: String, index: Int, q: Queue): Int {
        var counterCycle = 1
        var indexOfEnd = 0
        for (i in index + 1..q.queue.size - 1) {
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
        val condition: Boolean? = when (cond) {
            "=" -> calc(varReplacement(firstBlock)).toDouble() != calc(varReplacement(secBlock)).toDouble()
            "!=" -> calc(varReplacement(firstBlock)).toDouble() == calc(varReplacement(secBlock)).toDouble()
            ">=" -> calc(varReplacement(firstBlock)).toDouble() < calc(varReplacement(secBlock)).toDouble()
            "<=" -> calc(varReplacement(firstBlock)).toDouble() > calc(varReplacement(secBlock)).toDouble()
            ">" -> calc(varReplacement(firstBlock)).toDouble() <= calc(varReplacement(secBlock)).toDouble()
            "<" -> calc(varReplacement(firstBlock)).toDouble() >= calc(varReplacement(secBlock)).toDouble()
            else -> null
        }
        return if (condition == true) {
            indexOfEnd
        } else {
            -1
        }
    }

    fun loop(q: Queue, index: Int): Int {
        var counterCycle = 1
        var indexOfEnd = 0
        for (i in index + 1..q.queue.size - 1) {
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

    fun newArray(nameArray: String, countValues: String) {
        var size = calc(varReplacement(countValues)).toDouble().toInt()
        for (i in 0..size - 1) {
            var arrayElem = "$nameArray[$i]"
            declaration(arrayElem)
        }
    }

    fun output(nameVars: String, answer: MutableList<MutableList<String>>) {
        var temp = ""
        for (i in nameVars.indices) {
            if (nameVars[i] == ',') {
                if (value[findValue(temp)].toInt().toDouble() == value[findValue(temp)]) {
                    answer[1].add(value[findValue(temp)].toInt().toString())
                } else {
                    answer[1].add(value[findValue(temp)].toString())
                }
                answer[0].add(name[findValue(temp)])
                temp = ""
            } else if (nameVars[i] == ' ') {
                continue
            } else if (i == nameVars.length - 1) {
                temp += nameVars[i]
                if (value[findValue(temp)].toInt().toDouble() == value[findValue(temp)]) {
                    answer[1].add(value[findValue(temp)].toInt().toString())
                } else {
                    answer[1].add(value[findValue(temp)].toString())
                }
                answer[0].add(name[findValue(temp)])
            } else {
                temp += nameVars[i]
            }
        }
    }
}

class Queue {
    var queue: MutableList<MutableList<String>> = mutableListOf()
    var size: Int = 0

    fun paste(new: MutableList<MutableList<String>> = mutableListOf(), index: Int) {
        var newArray: MutableList<MutableList<String>> = mutableListOf()
        for (i in 0..index - 1) {
            newArray.add(queue[i])
        }
        var count = 0
        for (i in index..index + new.size - 1) {
            newArray.add(new[count])
            count++
        }
        count = index
        for (i in index + new.size..queue.size + new.size - 1) {
            newArray.add(queue[count])
            count++;
        }
        queue = newArray
    }
}

fun main(
    dataSet: MutableList<MutableList<String>>,
    answer: MutableList<MutableList<String>> = mutableListOf(),
    allVariables: MutableList<MutableList<String>> = mutableListOf()
) {
    var q = Queue()
    q.queue = dataSet


    var t: MutableList<String> = mutableListOf()
    var z: MutableList<String> = mutableListOf()
    var y: MutableList<String> = mutableListOf()
    var x: MutableList<String> = mutableListOf()

    initializeData(answer, t, z, allVariables, y, x)

    var data = DataClass()
    mainLoop(q, data, answer)
    fillAllVariables(data, allVariables)
}

private fun fillAllVariables(
    data: DataClass,
    allVariables: MutableList<MutableList<String>>
) {
    for (i in 0..data.name.size - 1) {
        if (data.name[i] != "") {
            allVariables[0].add(data.name[i])
            allVariables[1].add(data.value[data.findValue(data.name[i])].toString())
        }
    }
}

private fun initializeData(
    answer: MutableList<MutableList<String>>,
    t: MutableList<String>,
    z: MutableList<String>,
    allVariables: MutableList<MutableList<String>>,
    y: MutableList<String>,
    x: MutableList<String>
) {
    answer.add(t)
    answer.add(z)
    allVariables.add(y)
    allVariables.add(x)
}

private fun mainLoop(
    q: Queue,
    data: DataClass,
    answer: MutableList<MutableList<String>>
) {
    var i = 0
    while (true) {
        var temp: MutableList<String> = mutableListOf()
        temp = q.queue[i]
        when {
            temp[0] == "" -> { // пустой блок
                i++
                if (i == q.queue.size) {
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
                data.output(temp[2], answer)
            }

            temp[0] == ifCondition -> {
                var flag = data.condition(temp[2], temp[3], temp[4], i, q)
                if (flag != -1) {
                    for (j in i + 1..flag) {
                        q.queue[j][0] = ""
                    }
                }
            }

            temp[0] == ifEnd -> {
                i++
                if (i == q.queue.size) {
                    break
                }
                continue
            }

            temp[0] == forLoop -> {
                data.declaration(temp[2])
                val start = data.calc(data.varReplacement(temp[3])).toDouble().toInt()
                val finish = data.calc(data.varReplacement(temp[4])).toDouble().toInt()
                var new: MutableList<MutableList<String>> = mutableListOf()
                for (k in start..finish - 1) {
                    var newBlock: MutableList<String> = mutableListOf()
                    newBlock.add(assignmentOperator)
                    newBlock.add("0")
                    newBlock.add(temp[2])
                    newBlock.add(k.toString())
                    new.add(newBlock)
                    for (j in i + 1..data.loop(q, i) - 1) {
                        var t: MutableList<String> = mutableListOf()
                        for (h in 0..q.queue[j].size - 1) {
                            t.add(q.queue[j][h])
                        }
                        new.add(t)
                    }
                }
                var newBlock: MutableList<String> = mutableListOf()
                newBlock.add(assignmentOperator)
                newBlock.add("0")
                newBlock.add(temp[2])
                newBlock.add(finish.toString())
                new.add(newBlock)
                q.paste(new, i + 1)
            }

            temp[0] == forEnd -> {
                i++
                if (i == q.queue.size) {
                    break
                }
                continue
            }

            temp[0] == createArray -> {
                data.newArray(temp[2], temp[3])
            }
        }
        i++
        if (i == q.queue.size) {
            break
        }
    }
}


