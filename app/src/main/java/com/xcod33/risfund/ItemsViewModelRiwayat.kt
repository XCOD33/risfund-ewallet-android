package com.xcod33.risfund

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemsViewModelRiwayat (val title: String, val date: String, val amount: Int) : Parcelable {
    val getImage = when(title) {
        "transfer sent" -> R.drawable.transferout
        "transfer received" -> R.drawable.transferin
        "payment sent" -> R.drawable.transferout
        "payment received" -> R.drawable.transferin
        else -> R.drawable.receipt
    }

    val getAmount = when(title) {
        "transfer sent" -> "-RP" + amount
        "transfer received" -> "+RP" + amount
        "payment sent" -> "-RP" + amount
        "payment received" -> "+RP" + amount
        else -> "-RP" + amount
    }
}