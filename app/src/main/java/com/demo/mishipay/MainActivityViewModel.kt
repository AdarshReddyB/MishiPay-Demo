package com.demo.mishipay

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.demo.mishipay.model.Cart
import com.demo.mishipay.model.Product

class MainActivityViewModel(application: Application):AndroidViewModel(application) {


    val cart:MutableLiveData<Cart> = MutableLiveData()

    fun productAdded(product: Product) {

    }
}