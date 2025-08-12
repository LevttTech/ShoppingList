package com.wapp.shoppinglist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wapp.shoppinglist.data.repository.ShopListRepositoryImpl
import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.domain.usecase.DeleteShopItemUseCase
import com.wapp.shoppinglist.domain.usecase.EditShopItemUseCase
import com.wapp.shoppinglist.domain.usecase.GetShopListUseCase

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)



    fun getShopList(): LiveData<List<ShopItem>> {
        return getShopListUseCase.getShopList()
    }

    fun changedEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItem(newItem)
    }
    fun deleteShopItem(shopItemId: Int) {
        deleteShopItemUseCase.deleteShopItem(shopItemId)
    }

    fun editShopItem(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(shopItem)
    }
}