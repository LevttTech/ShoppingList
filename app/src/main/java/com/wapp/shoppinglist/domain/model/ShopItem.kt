package com.wapp.shoppinglist.domain.model

data class ShopItem (
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = -1
    }

    override fun toString(): String {
        return "ShopItem(name='$name', count=$count, enabled=$enabled, id=$id)"
    }


}