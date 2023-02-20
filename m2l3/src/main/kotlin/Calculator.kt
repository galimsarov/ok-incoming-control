class Calculator {
    fun eval(expression: CalculatorExpression) = when (expression.operation) {
        Operation.ADD -> {
            val first = expression.first
            val second = expression.second
            when (first) {
                is Int -> {
                    when (second) {
                        is Double -> first + second
                        is Float -> first + second
                        is Int -> first + second
                        is Short -> first + second
                        is Long -> first + second
                        is Byte -> first + second
                        else -> throw Exception("Forbidden operation")
                    }
                }

                else -> throw Exception("Not yet implemented")
            }
        }
    }
}

data class CalculatorExpression(val first: Number, val second: Number, val operation: Operation)

infix fun Number.add(other: Number) = CalculatorExpression(this, other, Operation.ADD)

enum class Operation { ADD }