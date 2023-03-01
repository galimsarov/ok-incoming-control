package models

@JvmInline
value class IctrlProductId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = IctrlProductId("")
    }
}