package com.wapp.shoppinglist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.ViewModel
import com.wapp.shoppinglist.data.repository.ShopListRepositoryImpl
import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.domain.usecase.AddShopItemUseCase
import com.wapp.shoppinglist.domain.usecase.EditShopItemUseCase
import com.wapp.shoppinglist.domain.usecase.GetShopItemUseCase

class AddViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val _shouldCloseScreen: MutableLiveData<Boolean> = MutableLiveData()
    val shouldCloseScreen: LiveData<Boolean>
        get() = _shouldCloseScreen

    private val _shopItem: MutableLiveData<ShopItem> = MutableLiveData<ShopItem>()

    val shopItem:LiveData<ShopItem>
        get() = _shopItem

    private val _errorValidation: MutableLiveData<Int> = MutableLiveData<Int>()
    val errorValidation: LiveData<Int>
        get() = _errorValidation


    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseText(inputName)
        val count = parseInt(inputCount)

        if (validation(name, count)) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
            }
            _shouldCloseScreen.value = true
        }
    }
    fun getShopItem(id: Int) {
        val item = getShopItemUseCase.getShopItem(id)
        _shopItem.value = item
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseText(inputName)
        val count = parseInt(inputCount)

        if (validation(name, count)) {
            addShopItemUseCase.addShopItem(
                ShopItem(
                    name = name,
                    count = count,
                    enabled = true
                )
            )
            _shouldCloseScreen.value = true
        }
    }


    private fun validation(name: String,
                           count: Int): Boolean {
       _errorValidation.value = when {
           name.isBlank() -> ERR_NAME
           count <= 0 -> ERR_COUNT
           else -> null
       }

        return _errorValidation.value == null
    }
    private fun parseText(text: String?): String = text?.trim() ?: ""

    private fun parseInt(text: String?): Int = try {
        text?.toInt() ?: 0
    } catch (e: NumberFormatException) {
        0
    }

    companion object {
        const val ERR_NAME = 1
        const val ERR_COUNT = 2
    }
}