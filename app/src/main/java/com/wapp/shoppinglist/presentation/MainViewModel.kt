package com.wapp.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
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

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList() {
        val list =  getShopListUseCase.getShopList()
        shopList.value = list
    }
}