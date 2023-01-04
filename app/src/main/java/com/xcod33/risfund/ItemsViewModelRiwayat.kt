package com.xcod33.risfund

data class ItemsViewModelRiwayat (val image: Int, val title: Int, val date: Int, val price: Int) {
    val getImage = when(image) {
        1 -> R.drawable.menupulsa
        2 -> R.drawable.menupaketdata
        3 -> R.drawable.menubpjs
        4 -> R.drawable.menuwifi
        5 -> R.drawable.menupdam
        else -> R.drawable.ic_launcher_background
    }

    val getTitle = when(title) {
        1 -> "Pulsa"
        2 -> "Paket Data"
        3 -> "BPJS"
        4 -> "Wifi"
        5 -> "PDAM"
        else -> "NULL"
    }

    val getDate = when(date) {
        1 -> "12 Desember 2022 - 09:00"
        2 -> "10 Desember 2022 - 10:45"
        3 -> "10 Desember 2022 - 07:50"
        4 -> "9 Desember 2022 - 11:00"
        5 -> "7 Desember 2022 - 22.50"
        else -> "NULL"
    }

    val getPrice = when(price) {
        1 -> "Rp40,000"
        2 -> "Rp50,000"
        3 -> "Rp60,000"
        4 -> "Rp75,000"
        5 -> "Rp90,000"
        else -> "NULL"
    }
}