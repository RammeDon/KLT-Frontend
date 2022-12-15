package com.klt.util

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL

/** The Api Connector has all the functions for talking to the API,
 *  each function will return an onRespond Callback that has an parameter of
 *  Api Result
 *  */
object ApiConnector {

    // Init HTTP Client
    private var client: OkHttpClient = OkHttpClient();


    /** Api call that requires email and password,
     * in the result there is an token that can be saved for authenticated calls * */
    fun login(
        email: String,
        Password: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/login"

        val formBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .add("password", Password)
            .build()

        val request: Request = Request.Builder()
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        val call = client.newCall(request)

        onRespond(ApiResult(call.execute()))
    }

    /** Retries the information about the users that the token belongs too */
    fun getUserData(token: String, onRespond: (result: ApiResult) -> Unit) {
        val urlPath = "/api/user"
        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .build()
        val call = client.newCall(request)
        onRespond(ApiResult(call.execute()))
    }
}


/** Data class for easily manipulate the respond from the api */
data class ApiResult(
    val response: Response
) {
    fun getData(): JSONObject { return JSONObject(response.body?.string() ?: "{}")  }
    fun httpCode(): HttpStatus { return HttpStatus.values().find { it.code == response.code }!! }
}


/** Http status enum */
enum class HttpStatus(val code: Int) {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    REQUEST_TIMEOUT(408),
    UN_PROCESSABLE_ENTITY(422),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504)
}
