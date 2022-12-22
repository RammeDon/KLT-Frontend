package com.klt.util

/** Stores static values */
object Values {
    //    val BACKEND_IP = "http://188.150.227.229:3000" // The ip address of the backend API
    val BACKEND_IP = "http://192.168.56.1:3000" // The ip address of the backend API
    val AUTH_TOKEN_NAME = "jwt"                 // The name of the auth token (same as backend)
}

enum class ItemType {
    CLIENT,
    TASK,
    SIDEBAR
}

enum class Sides{
    LEFT,
    RIGHT,
    NONE
}
