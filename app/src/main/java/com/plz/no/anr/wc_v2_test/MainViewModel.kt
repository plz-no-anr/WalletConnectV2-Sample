package com.plz.no.anr.wc_v2_test

import androidx.lifecycle.ViewModel
import com.plz.no.anr.wc_v2_test.v2.WCManager
import com.plz.no.anr.wc_v2_test.v2.WalletDelegate

class MainViewModel: ViewModel() {
    val event = WalletDelegate.walletEvents

    val coreEvent = WalletDelegate.coreEvents
}