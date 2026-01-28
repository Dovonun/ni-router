package com.ni.router

class PayloadRouter {
    fun getPayloadFromIntent(action: String?, dataString: String?, extraText: String?): String? {
        return when (action) {
            "android.intent.action.VIEW" -> dataString
            "android.intent.action.SEND" -> extraText
            else -> null
        }
    }
}
