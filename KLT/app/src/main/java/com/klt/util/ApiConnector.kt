package com.klt.util

import android.util.Log
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

/** The Api Connector has all the functions for talking to the API,
 *  each function will return an onRespond Callback that has an parameter of
 *  Api Result
 *  */
object ApiConnector {

    // Init HTTP Client
    private var client: OkHttpClient = OkHttpClient()


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

        onRespond(callAPI(request))
    }

    /** Api Call for checking if user exists */
    fun userExists(
        email: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/userexists"

        val formBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .build()

        val request: Request = Request.Builder()
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }

    fun mailTokenExists(
        token: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/mailtoken/exists"

        val formBody: RequestBody = FormBody.Builder()
            .add("token", token)
            .build()

        val request: Request = Request.Builder()
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }


    /** Api Call for checking if user exists */
    fun createMailToken(
        email: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/mailtoken"

        val formBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .build()

        val request: Request = Request.Builder()
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }


    /** Api Call for creating an account */
    fun createAccount(
        token: String,
        email: String,
        firstName: String,
        lastName: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/create"

        val formBody: RequestBody = FormBody.Builder()
            .add("firstName", firstName)
            .add("lastName", lastName)
            .add("email", email)
            .build()

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }

    /** Api call to change password */
    fun changePassword(
        token: String,
        newPassword: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/changepassword"

        val formBody: RequestBody = FormBody.Builder()
            .add("newPassword", newPassword)
            .build()

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }

    /** Api call to change password */
    fun forgotPassword(
        newPassword: String,
        email: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/forgotpassword"

        val formBody: RequestBody = FormBody.Builder()
            .add("newPassword", newPassword)
            .add("email", email)
            .build()

        val request: Request = Request.Builder()
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }


    /** Retries the information about the users that the token belongs too */
    fun getUserData(token: String, onRespond: (result: ApiResult) -> Unit) {
        val urlPath = "/api/user"
        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .build()

        onRespond(callAPI(request))
    }

    fun getAllUserData(token: String, onRespond: (result: ApiResult) -> Unit) {
        val urlPath = "/api/user/getall"
        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .build()

        onRespond(callAPI(request))
    }

    /** Api call to delete an user */
    fun deleteUser(
        token: String,
        userId: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/deleteAccount/$userId"

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .delete()
            .build()

        onRespond(callAPI(request))
    }

    fun deleteCustomer(
        token: String,
        customerId: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/ts/c/$customerId/delete"
        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .delete()
            .build()

        onRespond(callAPI(request))
    }

    /** Api call to Create customer */
    fun createCustomer(
        name: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/ts/c/new"

        val formBody: RequestBody = FormBody.Builder()
            .add("name", name)
            .build()

        val request: Request = Request.Builder()
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }

    /** Api call to Create task */
    fun createTask(
        token: String,
        taskAsJson: String,
        onRespond: (result: ApiResult) -> Unit
    ) {

        val urlPath = "/api/ts/t/new"

        val formBody: RequestBody = FormBody.Builder()
            .add("data", taskAsJson)
            .build()

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }


    fun getAllCustomers(
        token: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/ts/c/all"

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .build()

        onRespond(callAPI(request))
    }

    fun sendTaskEntry(
        token: String,
        jsonData: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/ts/t/newentry"

        val formBody: RequestBody = FormBody.Builder()
            .add("data", jsonData)
            .build()

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }

    /** API CAll to get all task's from an customer */
    fun getTasksFromCustomer(
        token: String,
        customerId: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/ts/c/$customerId/t"

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .build()

        onRespond(callAPI(request))
    }

    fun editCustomer(
        token: String,
        jsonData: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/ts/c/edit"

        val formBody: RequestBody = FormBody.Builder()
            .add("data", jsonData)
            .build()

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }

    fun editUser(
        token: String,
        jsonData: JSONObject,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/user/updateuser"

        val formBody: RequestBody = FormBody.Builder()
            .add("data", jsonData.toString())
            .build()

        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .post(formBody)
            .build()

        onRespond(callAPI(request))
    }

    fun getCustomerByName(
        token: String,
        customerName: String,
        onRespond: (result: ApiResult) -> Unit
    ) {
        val urlPath = "/api/ts/c/get-by-name/$customerName"
        val request: Request = Request.Builder()
            .header(Values.AUTH_TOKEN_NAME, token)
            .url(Values.BACKEND_IP + urlPath)
            .build()

        onRespond(callAPI(request))
    }


    private fun callAPI(request: Request): ApiResult {
        return try {
            Log.d("callAPI", request.url.toString())
            val apiResult = client.newCall(request).execute()
            Log.d("callAPI", apiResult.message)
            val jsonData = apiResult.body?.string() ?: "{}"
            ApiResult(jsonData, apiResult.code)
        } catch (e: java.lang.Exception) {
            Log.d("callAPI", "Crash")
            ApiResult("{msg: \"Connection timeout\"}")
        }
    }
}


/** Data class for easily manipulate the respond from the api */
data class ApiResult(
    private val data: String = "{}",
    private val code: Number = 500
) {

    fun status(): HttpStatus {
        return when (code) {
            in 100..299 -> {
                HttpStatus.SUCCESS
            }
            in 300..499 -> {
                HttpStatus.UNAUTHORIZED
            }
            else -> {
                HttpStatus.FAILED
            }
        }
    }

    fun data(): JSONObject {
        return JSONObject(data)
    }

    fun dataArray(): JSONArray {
        return JSONArray(data)
    }
}

/** Http status enum */
enum class HttpStatus {
    SUCCESS,
    UNAUTHORIZED,
    FAILED,
}
