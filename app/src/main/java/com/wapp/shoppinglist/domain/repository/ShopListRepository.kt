package com.wapp.shoppinglist.domain.repository

import com.wapp.shoppinglist.domain.model.ShopItem

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItemId: Int)
    fun editShopItem(shopItem: ShopItem)
    fun getShopItem(shopItemId: Int): ShopItem
    fun getShopList(): List<ShopItem>
}