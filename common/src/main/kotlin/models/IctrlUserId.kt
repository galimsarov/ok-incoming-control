package models

@JvmInline
value class IctrlUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = IctrlUserId("")
    }
}