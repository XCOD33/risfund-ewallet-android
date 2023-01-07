package com.xcod33.risfund.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetPaymentChannel(
	val iconUrl: String? = null,
	val code: String? = null,
	val name: String? = null
) : Parcelable
