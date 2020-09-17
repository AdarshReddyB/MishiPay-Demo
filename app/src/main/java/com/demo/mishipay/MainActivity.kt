package com.demo.mishipay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.demo.mishipay.model.Product
import com.demo.mishipay.model.Store
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.CaptureActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : CaptureActivity() {

    var products = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.scan_btn)

        scan_btn.setOnClickListener {
            //initializeScan()
            IntentIntegrator(this).initiateScan()
        }

        products.add(Product(imageURl = "", description = "Asdf", name = "Product 1", price = 1.22, id = "1"))
        products.add(Product(imageURl = "", description = "Asdf", name = "Product 2", price = 1.22,id = "2"))
        products.add(Product(imageURl = "", description = "Asdf", name = "Product 3", price = 1.22, id = "3"))
        products.add(Product(imageURl = "", description = "Asdf", name = "Product 4", price = 1.22, id = "4"))

        Preferences().saveStore(this, Store(products, "LocalStore", "Bangalore"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.formatName + "\n" + result.getContents(),
                    Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
