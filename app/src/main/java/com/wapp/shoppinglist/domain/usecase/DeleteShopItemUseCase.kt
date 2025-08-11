package com.wapp.shoppinglist.domain.usecase

import com.wapp.shoppinglist.domain.repository.ShopListRepository


class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteShopItem(shopItemId: Int) {
        shopListRepository.deleteShopItem(shopItemId);
    }
}