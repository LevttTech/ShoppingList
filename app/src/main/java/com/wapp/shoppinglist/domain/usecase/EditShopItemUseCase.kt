package com.wapp.shoppinglist.domain.usecase

import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.domain.repository.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}