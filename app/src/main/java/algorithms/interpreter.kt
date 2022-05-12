package algorithms

class DataClass {
    var name: MutableList<String> = mutableListOf()
    var value: MutableList<Int> = mutableListOf()

    private fun findValue(nameVar : String): Int {
        for(i in 0..name.size) {
            if (name[i] == nameVar) {
                return i
            }
        }
        return -1
    }

    fun declaration(nameVars : String) {
        var temp = ""
        for(i in nameVars.indices) {
            if (nameVars[i] == ',') {
                name.add(temp)
                value.add(0)
                temp = ""
            }
            else if (nameVars[i] == ' ') {
                continue
            }
            else if (i == nameVars.length-1){
                temp += nameVars[i]
                name.add(temp);
                value.add(0)
            }
            else {
                temp += nameVars[i]
            }
        }
    }

    fun assignment(nameVar : String, valueVar : Int) {
                value[findValue(nameVar)] = valueVar
    }

    fun output(nameVars : String): MutableList<Int> {
        var temp = ""
        var values : MutableList<Int> = mutableListOf()
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
        return values
    }
}

class Queue {
    var queue: MutableList<MutableList<String>> = mutableListOf()

    fun push(element : MutableList<String>) {
        queue.add(element)
    }

    fun size(): Int {
        return queue.size
    }
}

fun main() {
    var q = Queue()
    var data = DataClass()

    for(i in 0..q.size()) {
        var temp: MutableList<String> = mutableListOf()
        temp = q.queue[i]
        if (temp[0] == "0") { // 0 - обозначение типа блока объявления переменной
            data.declaration(temp[1])
        }
        if (temp[0] == "1") { // 1 - обозначение блока присваивания
            data.assignment(temp[1], temp[2].toInt())
        }
        if (temp[0] == "2"){ // 2 - обозначение блока вывода
            data.output(temp[1])
        }
    }
}