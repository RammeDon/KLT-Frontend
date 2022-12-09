package com.klt.util

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL


object ApiConnector {

    /** Private method for doing a generic get request */
    private var client: OkHttpClient = OkHttpClient();

    /** Call api to login */
    fun login() {
        //TODO: Update this functionality, currently this is just a test to the api
        val res = getRequest("http://192.168.0.38:3000/api/user", "123")
        res?.let { Log.d("APIKLT", it) }
    }

    /** Private method for doing get request */
    private fun getRequest(sUrl: String, jwt: String): String? {
        //TODO: header name token 'auth' should not be hardcoded
        var result: String? = null
        try {
            val url = URL(sUrl)
            val request = Request.Builder()
                .header("auth", jwt)
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
}