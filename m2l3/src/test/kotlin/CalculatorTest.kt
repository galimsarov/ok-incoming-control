import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class CalculatorTest : ShouldSpec({
    val calculator = Calculator()
    should("process DSL expression -> int && int") { calculator.eval(2 add 3) shouldBe 5 }
    should("process DSL expression -> int && long") { calculator.eval(2 add 3L) shouldBe 5 }
    should("process DSL expression -> int && double") { calculator.eval(2 add 3.0) shouldBe 5 }
    should("process DSL expression -> int && float") { calculator.eval(2 add 3.0f) shouldBe 5 }
    should("process DSL expression -> int && short") { calculator.eval(2 add 3.toShort()) shouldBe 5 }
    should("process DSL expression -> int && byte") { calculator.eval(2 add 3.toByte()) shouldBe 5 }
})