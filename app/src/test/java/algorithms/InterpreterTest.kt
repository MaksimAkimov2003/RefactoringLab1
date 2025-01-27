package algorithms

import android.annotation.SuppressLint
import com.example.firstapp.tagDeclareIntegerView
import com.example.firstapp.tagIfOperatorView
import com.example.firstapp.tagOutputView
import org.junit.Test
import org.mockito.Mockito

class InterpreterTest {
    @Test
    fun `GIVEN valid expression WHEN invoke method THEN answer should contain output value`() {
        val queue = Mockito.mock(Queue::class.java)

        val dataSet = mutableListOf(mutableListOf(tagDeclareIntegerView, "a=5", tagOutputView, "a"))
        val answer: MutableList<MutableList<String>> = mutableListOf()
        val allVariables: MutableList<MutableList<String>> = mutableListOf()

        Mockito.`when`(queue.getingQueue()).thenReturn(dataSet)

        val list = mutableListOf(tagOutputView)
        val interpreter = Interpreter(queue)

        interpreter.main(dataSet = dataSet, answer = answer, allVariables = allVariables)

        assert(allVariables.contains(list))
    }

    @Test
    fun `GIVEN invalid expression WHEN invoke method THEN answer should not contain output value`() {
        val queue = Mockito.mock(Queue::class.java)

        val dataSet = mutableListOf(mutableListOf(tagDeclareIntegerView, "a5", tagDeclareIntegerView, "a"))
        val answer: MutableList<MutableList<String>> = mutableListOf()
        val allVariables: MutableList<MutableList<String>> = mutableListOf()

        Mockito.`when`(queue.getingQueue()).thenReturn(dataSet)

        val list = mutableListOf(tagOutputView)
        val interpreter = Interpreter(queue)

        interpreter.main(dataSet = dataSet, answer = answer, allVariables = allVariables)

        assert(!allVariables.contains(list))
    }

    @Test
    fun `GIVEN valid expression WHEN invoke method with unitialized stack THEN answer should not contain output value`() {
        val queue = Mockito.mock(Queue::class.java)
        val dataClass = Mockito.mock(DataClass::class.java)

        val dataSet = mutableListOf(mutableListOf(tagDeclareIntegerView, "a=5", tagDeclareIntegerView, "a"))
        val answer: MutableList<MutableList<String>> = mutableListOf()
        val allVariables: MutableList<MutableList<String>> = mutableListOf()

        Mockito.`when`(queue.getingQueue()).thenReturn(dataSet)
        Mockito.`when`(dataClass.toRPN("")).thenReturn(Stack())

        val list = mutableListOf(tagOutputView)
        val interpreter = Interpreter(queue, dataClass)

        interpreter.main(dataSet = dataSet, answer = answer, allVariables = allVariables)

        assert(!allVariables.contains(list))
    }

    @Test
    fun `GIVEN invalid expression WHEN invoke method with unitialized stack THEN answer should not contain output value`() {
        val queue = Mockito.mock(Queue::class.java)
        val dataClass = Mockito.mock(DataClass::class.java)

        val dataSet = mutableListOf(mutableListOf(tagDeclareIntegerView, "=7", tagDeclareIntegerView, "a"))
        val answer: MutableList<MutableList<String>> = mutableListOf()
        val allVariables: MutableList<MutableList<String>> = mutableListOf()

        Mockito.`when`(queue.getingQueue()).thenReturn(dataSet)
        Mockito.`when`(dataClass.toRPN("")).thenReturn(Stack())

        val list = mutableListOf(tagOutputView)
        val interpreter = Interpreter(queue, dataClass)

        interpreter.main(dataSet = dataSet, answer = answer, allVariables = allVariables)

        assert(!allVariables.contains(list))
    }

    /**
     * Классы плохих данных(DataClass и Queue)
     */
    @Test
    fun `GIVEN declare variable operation WHEN invoke method with default parameters THEN answer should contain declare tag and variable name`() {
        val dataSet = mutableListOf(mutableListOf(tagDeclareIntegerView, "a=7", tagDeclareIntegerView, "a"))
        val answer: MutableList<MutableList<String>> = mutableListOf()
        val allVariables: MutableList<MutableList<String>> = mutableListOf()
        val interpreter = Interpreter()

        interpreter.main(dataSet = dataSet, answer = answer, allVariables = allVariables)

        val queue = interpreter.q

        assert(queue.queue[0].contains("Declare"))
        assert(queue.queue[0].contains("a"))
    }

    @Test
    fun `GIVEN if condition operation WHEN invoke method with default parameters THEN answer should contain if tag and variable name`() {
        val dataSet = mutableListOf(mutableListOf(tagDeclareIntegerView, "if(a==b)", tagIfOperatorView, "a"))
        val answer: MutableList<MutableList<String>> = mutableListOf()
        val allVariables: MutableList<MutableList<String>> = mutableListOf()
        val interpreter = Interpreter()

        interpreter.main(dataSet = dataSet, answer = answer, allVariables = allVariables)

        val queue = interpreter.q

        assert(queue.queue[0].contains(tagIfOperatorView))
        assert(queue.queue[0].contains("a"))
    }
}