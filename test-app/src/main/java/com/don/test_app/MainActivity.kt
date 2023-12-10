/*
 * Copyright [2023] [Don Chakkappan donchakkappan@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.don.test_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.don.test_app.api.ApiInterface
import com.don.test_app.api.RetrofitClient
import com.don.test_app.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        retrofit = RetrofitClient.getInstance(this)
        apiInterface = retrofit.create(ApiInterface::class.java)

        makeGetRequest()
        binding.buttonGetHit.setOnClickListener { makeGetRequest() }
        binding.buttonPostHit.setOnClickListener { makePostRequest() }
        binding.button404Hit.setOnClickListener { makeInvalidRequest() }
    }

    private fun makeGetRequest() {

        lifecycleScope.launchWhenCreated {
            try {

                val response = apiInterface.getAllUsers()

                if (response.isSuccessful) {
                    val json = Gson().toJson(response.body())
                    if (response.body()?.data?.size!! <= 0) {
                        Toast.makeText(this@MainActivity, "No Data ", Toast.LENGTH_LONG).show()
                    } else {
                        binding.txtData.text = json
                    }
                } else {
                    Toast.makeText(this@MainActivity, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                }
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.e("Error", it) }
            }
        }

    }

    private fun makePostRequest() {

        lifecycleScope.launchWhenCreated {
            try {

                val jsonObject = JSONObject()
                try {
                    jsonObject.put("name", Random.nextInt(100))
                    jsonObject.put("value", Random.nextInt(100))
                    jsonObject.put("pair", Random.nextInt(100))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val params = jsonObject.toString()
                val requestBody = params.toRequestBody("application/json".toMediaTypeOrNull())

                val response = apiInterface.postAllUsers(requestBody)

                if (response.isSuccessful) {
                    val json = Gson().toJson(response.body())
                    if (response.body()?.data?.size!! <= 0) {
                        Toast.makeText(this@MainActivity, "No Data ", Toast.LENGTH_LONG).show()
                    } else {
                        binding.txtData.text = json
                    }
                } else {
                    Toast.makeText(this@MainActivity, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                }
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.e("Error", it) }
            }
        }

    }

    private fun makeInvalidRequest() {

        lifecycleScope.launchWhenCreated {
            try {

                val jsonObject = JSONObject()
                try {
                    jsonObject.put("name", Random.nextInt(100))
                    jsonObject.put("value", Random.nextInt(100))
                    jsonObject.put("pair", Random.nextInt(100))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val params = jsonObject.toString()
                val requestBody = params.toRequestBody("application/json".toMediaTypeOrNull())

                val response = apiInterface.getError(requestBody)

                if (response.isSuccessful) {
                    val json = Gson().toJson(response.body())
                    if (response.body()?.data?.size!! <= 0) {
                        Toast.makeText(this@MainActivity, "No Data ", Toast.LENGTH_LONG).show()
                    } else {
                        binding.txtData.text = json
                    }
                } else {
                    Toast.makeText(this@MainActivity, response.errorBody().toString(), Toast.LENGTH_LONG).show()
                }
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.e("Error", it) }
            }
        }

    }
}