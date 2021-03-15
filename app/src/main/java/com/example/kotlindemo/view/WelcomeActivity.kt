package com.example.kotlindemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.internal.Streams.parse
import org.json.JSONObject

class WelcomeActivity : BaseActivity() {

    private  var TAG = "WelcomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_welcome
    }

    override fun initView() {
    }

    override fun initData() {
      var jsonObject =  JSONObject()
        var jsonObject1 =  JsonObject()

        val bizInfoMap = mapOf("cdBiz" to "3",
            "findNum" to  "findNum",
            "resNum" to "resNum" ,
            "posTranNum" to "posTranNum",
            "errorCodeBiz" to "errorCodeBiz",
            "errorMessage" to "errorMessage",
            "transactionArray" to "transactionArray")

        val bizInfoJson = Gson().toJson(bizInfoMap)
        jsonObject.put("procResult", "procResult")
        jsonObject.put("bizInfo", JSONObject(bizInfoJson))
        Log.d(TAG, "1--: $jsonObject")
        Log.d(TAG, "2--: ${JSONObject(bizInfoJson)}")
        Log.d(TAG, "2--: ${JSONObject(bizInfoMap)}")

    }


}
