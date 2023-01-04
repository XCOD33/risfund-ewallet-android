package com.xcod33.risfund.data

data class User(
	val data: Data,
	val message: String
)

data class Data(
	val phoneNumber: String,
	val birthdate: String,
	val gender: String,
	val balance: Int,
	val updatedAt: String,
	val userQr: String,
	val fullName: String,
	val createdAt: String,
	val userId: Int,
	val username: String
)

