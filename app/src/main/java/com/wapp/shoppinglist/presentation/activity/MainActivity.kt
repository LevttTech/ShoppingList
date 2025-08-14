package com.wapp.shoppinglist.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wapp.shoppinglist.R
import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.presentation.activity.AddActivity.Companion.newIntentAddItem
import com.wapp.shoppinglist.presentation.activity.AddActivity.Companion.newIntentEditItem
import com.wapp.shoppinglist.presentation.adapter.ShopListAdapter
import com.wapp.shoppinglist.presentation.viewmodel.AddViewModel
import com.wapp.shoppinglist.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShopListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate()")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        adapter = ShopListAdapter()
        recyclerView = findViewById(R.id.recyclerViewItems)
        recyclerView.adapter = adapter
        adapter.onShopItemLongClickListener = {
            viewModel.changedEnableState(it)
        }
        adapter.onShopItemClickListener = {
            startActivity(
                newIntentEditItem(
                    this,
                    it.id
                )
            )
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getShopList().observe(this) {
            adapter.shopList = it
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.buttonAddItem)
        buttonAddItem.setOnClickListener {
            startActivity(
                newIntentAddItem(this)
            )
        }
        setUpSwipeLogic()

    }


    companion object {
        const val TAG = "MainActivity"
    }


    fun setUpSwipeLogic(): Unit {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.shopList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}