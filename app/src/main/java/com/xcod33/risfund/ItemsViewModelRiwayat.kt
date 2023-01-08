package com.xcod33.risfund

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemsViewModelRiwayat (val title: String, val date: String, val amount: Int) : Parcelable {
    val getImage = when(title) {
        "transfer sent" -> R.drawable.transferout
        "transfer received" -> R.drawable.transferin
        else -> R.drawable.ic_launcher_background
    }
}