package ru.otus.otuskotlin.incomingcontrol.m1l4.sql

class WhereContext {
    infix fun String.eq(param: Any?) = "$this ${if (param == null) "is" else "="} '$param'"

    infix fun String.nonEq(param: Any?) = "$this ${if (param == null) "!is" else "!="} $param"
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
    fun where(block: WhereContext.() -> String) {
        val blockResult = block.invoke(WhereContext())
        where = " where $blockResult"
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