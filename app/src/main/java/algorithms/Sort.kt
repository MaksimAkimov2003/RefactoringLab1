package algorithms

import android.view.View
import kotlinx.android.synthetic.main.view_if_close.view.textNumber


fun sortAllViewList(A: MutableList<View>, p: Int, r: Int) {
    if (p < r) {
        var q: Int = partition(A, p, r)
        sortAllViewList(A, p, q - 1)
        sortAllViewList(A, q + 1, r)

    }
}

fun partition(A: MutableList<View>, p: Int, r: Int): Int {
    var temp = A[r].textNumber.text
    var tempString = temp.toString()
    var x = tempString.toInt()
    var i = p - 1
    for (j in p until r) {
        var y = A[j].textNumber.text
        var yString = y.toString()
        var yInt = yString.toInt()
        if (yInt <= x) {
            i++
            exchange(A, i, j)
        }
    }
    exchange(A, i + 1, r)
    return i + 1
}

fun exchange(A: MutableList<View>, i: Int, j: Int) {
    var temp = A[i]
    A[i] = A[j]
    A[j] = temp
}