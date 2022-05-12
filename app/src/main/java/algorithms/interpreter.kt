package algorithms

class DataClass {
    var name: MutableList<String> = mutableListOf()
    var value: MutableList<Int> = mutableListOf()

    fun declaration(nameVar : String) {
        name.add(nameVar)
        value.add(0)
    }

    fun assignment(nameVar : String, valueVar : Int) {
        for(i in 0..name.size) {
            if (name[i] == nameVar) {
                value[i] = valueVar;
            }
        }
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
        if (temp[0] == "0") {
            data.declaration(temp[1])
        }
        if (temp[1] == "1") {
            data.assignment(temp[1], temp[2].toInt());
        }
    }
}