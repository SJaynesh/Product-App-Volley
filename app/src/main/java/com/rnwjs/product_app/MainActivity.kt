package com.rnwjs.product_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.rnwjs.product_app.adpater.ProductAdapter
import com.rnwjs.product_app.databinding.ActivityMainBinding
import com.rnwjs.product_app.models.ProductModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var allProductData: ArrayList<ProductModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchProductsData()

        binding.postBtn.setOnClickListener {
            addNewUser("Joan", "Android Developer")
        }
    }

    // Product Data Get
    private fun fetchProductsData() {
        val api = "https://dummyjson.com/product"
        val newRequestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Method.GET, api, null, {
                // Success
                Log.i(TAG, "fetchProductsData: $it")


                val allData = it.getJSONArray("products")
                // Json Parsing =>
                // 1. Json Decode
                // JSON => Array
                // 2. Json Encode
                // Array => JSON

                for (i in 0..<allData.length()) {
                    Log.i(TAG, "fetchProductsData: ${allData[i]}")

                    val data = allData.getJSONObject(i)

                    val title = data.getString("title")
                    val desc = data.getString("description")
                    val image = data.getString("thumbnail")

//                    val dimensions = data.getJSONObject("dimensions");
//                    val width = dimensions.getDouble("width")

                    allProductData.add(ProductModel(title = title, desc = desc, image = image))
                }

                val adapter = ProductAdapter(allProductData)
                binding.rvProductData.adapter = adapter


            }, {
                // Error
                Log.e(TAG, "fetchProductsData: $it")
            }
        );



        newRequestQueue.add(jsonObjectRequest)
    }

    // Post API
    private fun addNewUser(name: String, job: String) {
        val api = "https://reqres.in/api/users";

        val requestQueue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(Method.POST, api, {
            Log.i("Success", "addNewUser: $it")
        }, {
            Log.e("Error", "addNewUser: $it")
        }) {

            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()

                var data : MutableMap<String,String> = mutableMapOf()

                data.put("x-rapidapi-key", "d8f7cda0bamshbc4d982d36cb16dp1e6fe5jsn32111f51b5bd")
                data.put("x-rapidapi-host", "instagram-scraper-api2.p.rapidapi.com")
            }

            override fun getParams(): MutableMap<String, String>? {
                return super.getParams()

                var data: MutableMap<String, String> = mutableMapOf()

                data.put("name", name)
                data.put("job", job)

                return data;
            }
        };

        requestQueue.add(stringRequest);
    }
}