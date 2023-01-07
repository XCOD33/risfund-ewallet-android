package com.xcod33.risfund.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetUserResponse(
	val userId: Int? = null,
	val fullName: String? = null,
	val phoneNumber: String? = null,
	val birthdate: String? = null,
	val gender: String? = null,
	val username: String? = null,
	val balance: Int? = null,
	val userQr: String? = null,
	val createdAt: String? = null,
	val updatedAt: String? = null
) : Parcelable
