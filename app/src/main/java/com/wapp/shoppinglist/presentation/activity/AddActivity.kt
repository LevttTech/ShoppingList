package com.wapp.shoppinglist.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.wapp.shoppinglist.R
import com.wapp.shoppinglist.domain.model.ShopItem
import com.wapp.shoppinglist.presentation.viewmodel.AddViewModel

class AddActivity : AppCompatActivity() {
    private lateinit var viewModel: AddViewModel
    private lateinit var etName: TextInputEditText
    private lateinit var etCount: TextInputEditText
    private lateinit var saveButton: Button
    private var id: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[AddViewModel::class.java]
        id = intent.getIntExtra(EXTRA_SHOP_ITEM_ID,-1)
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        saveButton = findViewById(R.id.saveButtun)
        viewModel.errorValidation.observe(this) {
            when(it) {
                AddViewModel.ERR_COUNT -> Toast.makeText(this,"count item error",Toast.LENGTH_SHORT).show()
                AddViewModel.ERR_NAME -> Toast.makeText(this,"name error", Toast.LENGTH_SHORT).show()
                else -> {
                    Log.d(TAG,"success")
                }
            }
        }
        viewModel.shouldCloseScreen.observe(this) {
            Log.d(TAG,"onChangedSCS()")
            finish()
        }
        when (mode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
            else -> throw Exception("not able mode")
        }

    }

    fun launchEditMode() {
        viewModel.getShopItem(id)
        viewModel.shopItem.observe(this) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        saveButton.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    fun launchAddMode() {
        saveButton.setOnClickListener {
            val name = etName.text?.toString()
            val count = etCount.text?.toString()
            viewModel.addShopItem(name, count)
        }
    }

    companion object {
        private const val TAG = "AddActivity"
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        fun newIntentAddItem(context: Context): Intent{
            val intent = Intent(context, AddActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, id: Int): Intent {
            val intent = Intent(context, AddActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
}