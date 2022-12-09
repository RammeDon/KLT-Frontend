package com.klt.util


import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL
import kotlin.math.log


object ApiConnector {

    /** Private method for doing a generic get request */
    private var client: OkHttpClient = OkHttpClient();
    private fun getRequest(sUrl: String, jwt: String): String? {
        var result: String? = null
        try {
            val url = URL(sUrl)
            val request = Request.Builder()
                .header("auth", "1234")
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            result = response.body?.string()
        }
        catch(err:Error) {
            print("Error when executing get request: "+err.localizedMessage)
        }
        return result
    }

    fun test() {
        val response = getRequest("http://192.168.0.38:3000/api/user", "1234")
        response?.let { Log.d("KLTAPI", it) }
    }
}