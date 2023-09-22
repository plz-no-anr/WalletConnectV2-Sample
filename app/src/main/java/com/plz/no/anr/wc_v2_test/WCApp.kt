package com.plz.no.anr.wc_v2_test

import android.app.Application
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.web3.wallet.client.Wallet
import com.walletconnect.web3.wallet.client.Web3Wallet

class WCApp: Application() {

    override fun onCreate() {
        super.onCreate()
        val projectId = "fd580de31f76ef6386427867893fbcff" // Get Project ID at https://cloud.walletconnect.com/
        val relayUrl = "relay.walletconnect.com"
        val serverUrl = "wss://$relayUrl?projectId=$projectId"
        val connectionType = ConnectionType.AUTOMATIC
        val appMetaData = Core.Model.AppMetaData(
            name = "Fncy Wallet",
            description = "Wallet Description",
            url = "Wallet Url",
            icons = listOf()/*list of icon url strings*/,
            redirect = "kotlin-wallet-wc:/request" // Custom Redirect URI
        )

        CoreClient.initialize(
            relayServerUrl = serverUrl,
            connectionType = connectionType,
            application = this,
            metaData = appMetaData
        ) {
            println("CoreClient initialize error $it")
        }

        Web3Wallet.initialize(Wallet.Params.Init(CoreClient), onSuccess = {
            println("Web3Wallet initialize success")
        }, onError = { error ->
            println("Web3Wallet initialize error $error")
        })

    }


}