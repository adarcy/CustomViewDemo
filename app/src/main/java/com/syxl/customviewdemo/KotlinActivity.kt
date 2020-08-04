package com.syxl.customviewdemo

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity() {


    companion object{
        fun actionStart(context: Context, data1:String, data2 : String){
            val intent = Intent(context, KotlinActivity::class.java)
            intent.putExtra("data1", data1)
            intent.putExtra("data2", data2)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        var data1 = intent.extras.getString("data1")
        var data2 = intent.extras.getString("data2  ")

        textView.text = "data1=$data1 \n data2=$data2"
    }
}