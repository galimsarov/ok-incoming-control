package ru.otus.otuskotlin.incomingcontrol.m1l4.sql

class WhereContext {
    private var hiddenWhere: MutableList<String> = mutableListOf()

    val where: List<String>
        get() = hiddenWhere.toList()

    infix fun String.eq(param: Any?) {
        hiddenWhere.add(
            "$this ${if (param == null) "is" else "="} ${
                if (param is String) "'$param'"
                else "$param"
            }"
        )
    }

    infix fun String.nonEq(param: Any?) {
        hiddenWhere.add("$this ${if (param == null) "!is" else "!="} $param")
    }

    fun or(block: () -> Unit) {
        block.invoke()
    }
}

class SqlSelectContext {
    private var tableName: String = ""
    private var columnName: String = ""
    private var where: String = ""

    @SqlSelectDsl
    fun from(tableName: String) {
        this.tableName = "from $tableName"
    }

    @SqlSelectDsl
    fun select(vararg columns: String) {
        this.columnName = columns.joinToString(", ")
    }

    @SqlSelectDsl
    fun where(block: WhereContext.() -> Unit) {
        val ctx = WhereContext().apply(block)
        where = " where ${
            if (ctx.where.size == 1) ctx.where.joinToString("")
            else ctx.where.joinToString(" or ", "(", ")")
        }"
    }

    fun build(): String {
        if (tableName.isBlank()) {
            throw Exception()
        } else {
            return "select ${columnName.ifBlank { "*" }} $tableName$where"
        }
    }
}

@SqlSelectDsl1
fun query(block: SqlSelectContext.() -> Unit) = SqlSelectContext().apply(block)

@DslMarker
annotation class SqlSelectDsl

@DslMarker
annotation class SqlSelectDsl1