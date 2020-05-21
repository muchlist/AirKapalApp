package com.muchlis.simak.utils

import java.util.*

/*Menghilangkan semua spasi dan menjadikan huruf besar
untuk input nomer container dan voyage ke database*/
fun String.customTrim(): String {
    return this.replace("\\s".toRegex(), "").toUpperCase(Locale.ROOT)
}

/*Memberikan spasi setelah 4 huruf pertama untuk kemudahan
melihat nomer container pada list*/
fun String.addSpaceInFour(): String {
    return if (this.length > 4){
        this.substring(0,4) + " " + this.substring(4,this.length)
    } else this
}