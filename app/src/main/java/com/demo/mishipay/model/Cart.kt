package com.demo.mishipay.model

class Cart {

    private var scannedProducts:ArrayList<Product> = ArrayList()

    fun add(product: Product) {
        scannedProducts.add(product)
    }

    fun getProducts(): ArrayList<Product> {
        return scannedProducts
    }
}