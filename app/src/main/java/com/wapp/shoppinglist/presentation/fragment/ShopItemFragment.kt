package com.wapp.shoppinglist.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wapp.shoppinglist.R

class ShopItemFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.fragment_shop_item,
            container,
            false
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}