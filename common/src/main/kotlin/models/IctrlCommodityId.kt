package models

@JvmInline
value class IctrlCommodityId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = IctrlCommodityId("")
    }
}