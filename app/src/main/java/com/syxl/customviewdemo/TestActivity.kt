package com.syxl.customviewdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.syxl.customviewdemo.flow_layout.TagAdapter
import com.syxl.customviewdemo.flow_layout.FlowLayout
import kotlinx.android.synthetic.main.activiry_test.*

class TestActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiry_test)

//        var list = arrayOf("Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
//                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
//                    "Android", "Weclome Hello", "Button Text", "TextView","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
//                    "Android", "Weclome", "Button ImageView")
//
//        flow_layout.setAdapter(object : TagAdapter<String>(list) {
//            override fun getView(parent: FlowLayout, position: Int, s: String): View {
//                val tv = LayoutInflater.from(applicationContext).inflate(
//                        R.layout.tv,
//                        flow_layout, false
//                ) as TextView
//                tv.text = s
//                return tv
//            }
//        })

    }

}
