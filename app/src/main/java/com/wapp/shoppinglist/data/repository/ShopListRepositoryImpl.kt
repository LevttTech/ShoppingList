package com.wapp.shoppinglist.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.domain.repository.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = sortedSetOf<ShopItem>({o1,o2 -> o1.id.compareTo(o2.id)})
    private val shopListLD = MutableLiveData<List<ShopItem>>();
    private var autoIncrementId = 0

    private val TAG = "ShopListRepository"

    init {
        for (i in 0 until 10) {
            val item = ShopItem("name $i",i,Random.nextBoolean())
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if(shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        upd()
    }

    override fun deleteShopItem(shopItemId: Int) {
        val item = shopList.find { it.id == shopItemId }
            ?: throw NoSuchElementException("Shop item with id $shopItemId not found")
        shopList.remove(item)
        upd()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw NoSuchElementException("Element with id $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    fun upd() {
      shopListLD.value = shopList.toList()
    }

}