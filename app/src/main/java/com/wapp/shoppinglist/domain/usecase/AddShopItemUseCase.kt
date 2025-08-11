package com.wapp.shoppinglist.domain.usecase

import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.domain.repository.ShopListRepository

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}