package com.example.homeworkthirdcourse

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.Wallet.WalletOptions
import com.google.android.gms.wallet.WalletConstants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class PaymentsUtil {

    companion object {
        const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST
    }

    @Throws(JSONException::class)
    private fun getBaseRequest(): JSONObject? {
        return JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0)
    }

    @Throws(JSONException::class)
    private fun getGatewayTokenizationSpecification(): JSONObject? {
        return object : JSONObject() {
            init {
                put("type", "PAYMENT_GATEWAY")
                put("parameters", object : JSONObject() {
                    init {
                        put("gateway", "example")
                        put("gatewayMerchantId", "exampleGatewayMerchantId")
                    }
                })
            }
        }
    }

    private fun getAllowedCardNetworks(): JSONArray? {
        return  JSONArray()
            .put("AMEX")
            .put("DISCOVER")
            .put("INTERAC")
            .put("JCB")
            .put("MASTERCARD")
            .put("VISA")
    }

    private fun getAllowedCardAuthMethods(): JSONArray? {
        return JSONArray()
            .put("PAN_ONLY")
            .put("CRYPTOGRAM_3DS")
    }

    @Throws(JSONException::class)
    private fun getBaseCardPaymentMethod(): JSONObject? {
        val cardPaymentMethod = JSONObject()
        cardPaymentMethod.put("type", "CARD")
        val parameters = JSONObject()
        parameters.put("allowedAuthMethods", getAllowedCardAuthMethods())
        parameters.put("allowedCardNetworks", getAllowedCardNetworks())
        parameters.put("billingAddressRequired", true)
        val billingAddressParameters = JSONObject()
        billingAddressParameters.put("format", "FULL")
        parameters.put("billingAddressParameters", billingAddressParameters)
        cardPaymentMethod.put("parameters", parameters)
        return cardPaymentMethod
    }

    @Throws(JSONException::class)
    private fun getCardPaymentMethod(): JSONObject? {
        val cardPaymentMethod = getBaseCardPaymentMethod()
        cardPaymentMethod!!.put("tokenizationSpecification", getGatewayTokenizationSpecification())
        return cardPaymentMethod
    }

    fun createPaymentsClient(activity: Activity?): PaymentsClient? {
        val walletOptions =
            WalletOptions.Builder().setEnvironment(PAYMENTS_ENVIRONMENT).build()
        return Wallet.getPaymentsClient(activity!!, walletOptions)
    }

    fun getIsReadyToPayRequest(paymentsClient: PaymentsClient, listener: OnCompleteListener<Boolean>) {

        val request = JSONObject(getBaseRequest().toString()).apply {
            put("allowedPaymentMethods",JSONArray().put(getBaseCardPaymentMethod()))
        }
        val isReadyToPayRequest = IsReadyToPayRequest.fromJson(request.toString())
        val task = paymentsClient.isReadyToPay(isReadyToPayRequest)
        task.addOnCompleteListener(listener)
    }

    fun isReadyToPay(paymentsClient: PaymentsClient, listener: OnCompleteListener<Boolean>) {
        val request = IsReadyToPayRequest.newBuilder()
            .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
            .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
            .build()
        val task = paymentsClient.isReadyToPay(request)
        task.addOnCompleteListener(listener)
    }
}
