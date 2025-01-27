package algorithms

import org.junit.Before
import org.junit.Test

class DataClassTest {
    private var dataClass: DataClass = DataClass()

    @Before
    fun initDataClass() {
        dataClass = DataClass()
    }

    /**
     * Анализ граничных условий
     */
    @Test
    fun `GIVEN empty string WHEN invoke method THEN return empty stack`() {
        val rpn: Stack = dataClass.toRPN("")
        val array = rpn.getArray()
        assert(array.isEmpty())
    }

    @Test
    fun `GIVEN blanks string WHEN invoke method THEN return empty stack`() {
        val rpn: Stack = dataClass.toRPN("    ")
        val array = rpn.getArray()
        assert(array.isEmpty())
    }

    /**
     * Структурированное базисное тестирование
     */
    @Test
    fun `GIVEN string of digits WHEN invoke method THEN return stack of digits`() {
        val rpn: Stack = dataClass.toRPN("234")
        val array = rpn.getArray()

        val condition = array.all {
            it.toIntOrNull() != null
        }

        assert(condition)
    }

    @Test
    fun `GIVEN not empty string WHEN string contains dots THEN return stack contains dots`() {
        val rpn: Stack = dataClass.toRPN(".eAS(")
        val array = rpn.getArray()

        assert(array.contains("."))
    }

    @Test
    fun `GIVEN not empty string WHEN string contains '(' symbol THEN return stack contains '(' symbol`() {
        val rpn: Stack = dataClass.toRPN(".es)dfAS(")
        val array = rpn.getArray()

        assert(array.contains("("))
    }

    @Test
    fun `GIVEN not empty string WHEN string contains only '(' and '(' symbols THEN return string with only empty values`() {
        val rpn: Stack = dataClass.toRPN("()")
        val array = rpn.getArray()

        assert(array.all { it.isEmpty() })
    }

    @Test
    fun `GIVEN valid expression WHEN use brackets, operators and variables THEN return string with the right order`() {
        val rpn: Stack = dataClass.toRPN("(a+b)")
        val array = rpn.getArray()

        assert(array[0] == "(")
        assert(array[1] == "+")
        assert(array[2] == "a")
        assert(array[3] == "b")
    }

    /**
     * Тестирование, основанное на потоках данных(состояниях данных)
     */
    @Test
    fun `GIVEN object with uninitialized name property WHEN invoke method THEN return not found result`() {
        val value = dataClass.findValue("someValue")

        assert(value == -1)
    }

    @Test
    fun `GIVEN object with initialized name property WHEN name contains input parameter THEN return index of this parameter`() {
        val parameter = "a"
        dataClass.name = mutableListOf("b", "a", "c")

        val expected = dataClass.findValue(parameter)
        val actual = dataClass.name.indexOf(parameter)

        assert(expected == actual)
    }

    @Test
    fun `GIVEN object with initialized name property WHEN name doesn't contains input parameter THEN return -1`() {
        val parameter = "n"
        dataClass.name = mutableListOf("b", "a", "c")

        val expected = dataClass.findValue(parameter)

        assert(expected == -1)
    }
}