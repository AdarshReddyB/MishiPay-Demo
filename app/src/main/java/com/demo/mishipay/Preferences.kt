package com.demo.mishipay

import android.content.Context
import android.content.SharedPreferences
import com.demo.mishipay.model.Cart
import com.demo.mishipay.model.Product
import com.demo.mishipay.model.Store
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences {

    private fun getSharedPreferencesEditor(context: Context): SharedPreferences {
        return context.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }

    fun saveStore(context: Context, store: Store) {
        save(STORE, context, store)
    }

    fun fetchStore(context: Context): Store? {
        return fetch(STORE, context, Store::class.java)
    }

    fun saveCart(context: Context, cart: Cart) {
        save(CART, context, cart)
    }

    fun fetchCart(context: Context): Cart? {
        return fetch(CART, context, Cart::class.java)
    }

    //private function to save any data model in local preferences
    private fun save(key: String, context: Context, value: Any) {
        getSharedPreferencesEditor(context).edit().putString(key, Gson().toJson(value))
            .apply()
    }

    //private function to get any saved data in local preferences with that class name
    private fun <T> fetch(key: String, context: Context, className: Class<T>): T? {
        val serializedData =
            getSharedPreferencesEditor(context).getString(key, null) ?: return null

        return Gson().fromJson(serializedData, className)
    }

    companion object {
        private val TAG = "MishiPayPreferences"
        private val STORE = "store"
        private val CART = "cart"
    }
}