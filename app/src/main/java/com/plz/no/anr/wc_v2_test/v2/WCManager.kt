package com.plz.no.anr.wc_v2_test.v2

import com.walletconnect.web3.wallet.client.Wallet
import com.walletconnect.web3.wallet.client.Web3Wallet

class WCManager() {

    fun approveSession() {
        if (Web3Wallet.getSessionProposals().isNotEmpty()) {
            val sessionProposal: Wallet.Model.SessionProposal =
                requireNotNull(Web3Wallet.getSessionProposals().last())

            val sessionNamespaces: Map<String, Wallet.Model.Namespace.Session> =
                sessionProposal.requiredNamespaces.mapValues { (namespace, proposal) ->
                    val accounts =
                        listOf("eip155:923018:0x56deBc685d53dA54C51fc19E879BA5597594B4DB")
                    Wallet.Model.Namespace.Session(
                        accounts = accounts,
                        methods = proposal.methods,
                        events = proposal.events
                    )
                }
            val approveProposal = Wallet.Params.SessionApprove(
                proposerPublicKey = sessionProposal.proposerPublicKey,
                namespaces = sessionNamespaces
            )

            Web3Wallet.approveSession(approveProposal,
                onError = { error ->
                    println("approveSession error $error")
                },
                onSuccess = {
                    println("approveSession success $it")
                })
        }
    }


    fun rejectSession() {
        if (Web3Wallet.getSessionProposals().isNotEmpty()) {
            val sessionProposal: Wallet.Model.SessionProposal =
                requireNotNull(Web3Wallet.getSessionProposals().last())
            val rejectParams: Wallet.Params.SessionReject =
                Wallet.Params.SessionReject(sessionProposal.proposerPublicKey, "reject")
            Web3Wallet.rejectSession(rejectParams, onSuccess = {
                println("rejectSession success $it")
            }) { error ->
                println("rejectSession error $error")
            }
        }

    }


    fun pair(uri: String) {
        val pairingParams = Wallet.Params.Pair(uri)
        Web3Wallet.pair(pairingParams, onSuccess = {
            println("pairing success $it")
        }, onError = { error ->
            println("pairing error $error")
        })


    }

    fun disconnect() {
        val disconnectParams = Wallet.Params.SessionDisconnect("")
        Web3Wallet.disconnectSession(disconnectParams, onSuccess = {
            println("disconnect success $it")
        }, onError = { error ->
            println("disconnect error $error")
        })

    }

}

