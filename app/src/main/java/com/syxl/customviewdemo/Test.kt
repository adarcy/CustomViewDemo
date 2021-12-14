package com.syxl.customviewdemo

import java.util.*


fun main(args : Array<String>){
    println("Hello World!")

    var map = TreeMap<Char, String>()
    for(ch in 'A'..'F'){
        var s = Integer.toBinaryString(ch.toInt())
        map[ch] = s
    }

    for((a,b) in map){
        println("$a = $b")
    }

    var list = arrayListOf("a","b","c")
    var last = list.last()


}

class Test {

}