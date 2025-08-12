package com.wapp.shoppinglist.domain.usecase

import androidx.lifecycle.LiveData
import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.domain.repository.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): LiveData<List<ShopItem>> {
       return shopListRepository.getShopList()
    }
}