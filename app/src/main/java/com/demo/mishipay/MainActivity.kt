package com.demo.mishipay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.mishipay.adapter.CartAdapter
import com.demo.mishipay.databinding.ActivityMainBinding
import com.demo.mishipay.model.Cart
import com.demo.mishipay.model.Product
import com.demo.mishipay.model.Store
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private var store: Store? = null

    private var cart: Cart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        activityMainBinding.fabScan.setOnClickListener {
            //initializeScan()
            IntentIntegrator(this).initiateScan()
        }

        initializeStore()
        initializeCart()
    }

    private fun initializeCart() {
        cart = Preferences().fetchCart(this)
        if (cart != null && cart?.getProducts()?.isNotEmpty()!!) {
            activityMainBinding.recyclerView.visibility = View.VISIBLE
            activityMainBinding.textViewScanNow.visibility = View.GONE
        } else {
            activityMainBinding.textViewScanNow.visibility = View.VISIBLE
        }
        activityMainBinding.recyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity)
        activityMainBinding.recyclerView.adapter =
            cart?.let { CartAdapter(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                checkScannedProductInStore(result)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun checkScannedProductInStore(result: IntentResult) {
        val scannedProduct =
            store?.products?.find { product -> product.id == result.contents }
        if (scannedProduct == null) {
            Toast.makeText(this, "Item not found in store", Toast.LENGTH_LONG).show()
            return
        }
        updateCart(scannedProduct)
    }

    private fun updateCart(product: Product) {
        if (cart == null) {
            cart = Cart()
            activityMainBinding.textViewScanNow.visibility = View.GONE
        }
        val size = cart?.getProducts()?.size
        val lastPos = if (size!! > 1) size - 1 else 0
        cart?.add(product)
        if (activityMainBinding.recyclerView.adapter == null) {
            activityMainBinding.recyclerView.adapter = CartAdapter(cart!!)
        } else {
            activityMainBinding.recyclerView.adapter!!.notifyItemChanged(lastPos)
        }
        Preferences().saveCart(this, cart!!)
    }

    private fun initializeStore() {
        var products = ArrayList<Product>()
        products.add(
            Product(
                imageURl = R.drawable.t_shirt,
                description = "T shirt by Puma",
                name = "Puma round neck T shirt",
                price = 700.00,
                id = "ABC1001"
            )
        )
        products.add(
            Product(
                imageURl = R.drawable.jeans,
                description = "Jeans by Pepe",
                name = "Pepe Jeans",
                price = 1500.00,
                id = "ABC1002"
            )
        )
        products.add(
            Product(
                imageURl = R.drawable.track_pant,
                description = "Track pant by Reebok",
                name = "Reebok Track pant",
                price = 950.50,
                id = "ABC1003"
            )
        )
        products.add(
            Product(
                imageURl = R.drawable.socks,
                description = "Socks by Puma",
                name = " Puma Socks",
                price = 300.00,
                id = "ABC1004"
            )
        )
        products.add(
            Product(
                imageURl = R.drawable.denim_jacket,
                description = "Jacket by Puma",
                name = "Puma Jacket",
                price = 4000.00,
                id = "ABC1005"
            )
        )
        products.add(
            Product(
                imageURl = R.drawable.suit,
                description = "Suit by Armani",
                name = "Armani Suit",
                price = 9999.99,
                id = "ABC1006"
            )
        )
        products.add(
            Product(
                imageURl = R.drawable.t_shirt,
                description = "T shirt by Adidas",
                name = "Adidas T-shirt",
                price = 800.00,
                id = "ABC1007"
            )
        )
        products.add(
            Product(
                imageURl = R.drawable.socks,
                description = "Socks by Adidas",
                name = "Adidas Socks",
                price = 350.00,
                id = "ABC1008"
            )
        )
        store = Store(products, "LocalStore", "Bangalore")
        Preferences().saveStore(this, store!!)
    }

}
