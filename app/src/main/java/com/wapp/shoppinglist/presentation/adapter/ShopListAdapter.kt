package com.wapp.shoppinglist.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wapp.shoppinglist.R
import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.presentation.adapter.util.DiffUtilCallback

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
      var cnt = 0
      var shopList = listOf<ShopItem>()
        set(value) {
            val callback = DiffUtilCallback(shopList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)

            field = value
        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null

    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        return ShopItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(
                viewType,
                parent,
                false
            ))
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
       return when (shopList[position].enabled) {
            true -> R.layout.rv_item_enabled
            else -> R.layout.rv_item_disabled
        }
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        holder.bind(shopList[position],
            onShopItemLongClickListener,
            onShopItemClickListener)
    }

    class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvName = view
            .findViewById<TextView>(R.id.textViewItem)
        private val tvCount = view
            .findViewById<TextView>(R.id.textViewCountItem)

        fun bind(item: ShopItem,
                 longClickListener: ((ShopItem) -> Unit)?,
                 clickListener: ((ShopItem) -> Unit)?) {
            tvName.text = item.name
            tvCount.text = item.count.toString()

            itemView.setOnClickListener {
                clickListener?.invoke(item)
            }

            itemView.setOnLongClickListener {
                Log.d(TAG,"clicked")
                longClickListener?.invoke(item)
                true
            }
        }
    }

    companion object {
        const val TAG: String = "Adapter"
    }
}
