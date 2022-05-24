package com.example.androidkotlinfinal.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter


fun String.formatDate(): String {
    return try{
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
        LocalDate.parse(this.substring(0,10)).format(formatter)
    }catch (e: Exception){
        ""
    }
}