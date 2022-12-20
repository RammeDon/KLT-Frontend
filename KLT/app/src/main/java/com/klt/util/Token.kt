package com.klt.util

import android.content.Context
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

object Token {

    private const val FILENAME = "token.txt"

    fun save(context: Context, token: String) {
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
            fileOutputStream.write(token.toByteArray())
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun get(context: Context): String {
        try {
            var fileInputStream: FileInputStream? = null
            fileInputStream = context.openFileInput(FILENAME)
            val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                    stringBuilder.append(text)
            }
            return stringBuilder.toString()
        }catch (e: Exception){
            return ""
        }
    }
}