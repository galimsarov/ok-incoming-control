import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class MyTests : ShouldSpec({
    should("return the length of the string") {
        "kotlin".length shouldBe 6
    }
})