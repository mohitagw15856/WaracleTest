package com.example.waracle

import androidx.lifecycle.MutableLiveData
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class MyRepo {
    private val apiResponse: MutableLiveData<ApiResponse?> = MutableLiveData<ApiResponse?>()
    private val cakesList: ArrayList<Cakes> = ArrayList()

    init { //application is subclass of context

        //cant call abstract func but since instance is there we can do this
    }

    fun callAPI(): MutableLiveData<ApiResponse?> {
        val call: Call<ResponseBody?>? = RetrofitClient.instance?.getapi()?.cakes()
        call?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.code() == 200) {
                    try {
                        assert(response.body() != null)
                        val dataArray = JSONArray(response.body()?.string())
                        for (i in 0 until dataArray.length()) {
                            val modelRecycler = Cakes()
                            val dataobj: JSONObject = dataArray.getJSONObject(i)
                            modelRecycler.title = (dataobj.getString("title"))
                            modelRecycler.desc = (dataobj.getString("desc"))
                            modelRecycler.image = (dataobj.getString("image"))
                            cakesList.add(modelRecycler)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                   val apiResponseData =ApiResponse(cakesList,null)
                    apiResponse.postValue(apiResponseData)
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                //failed
                val apiResponseData =ApiResponse(null,t)
                apiResponse.postValue(apiResponseData)
                println("t.getMessage() = " + t.message)
            }
        })
        return apiResponse
    }
}