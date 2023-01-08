package com.xcod33.risfund

data class ItemsViewModelPaketData(val title: Int, val price: Int) {
    val getTitle = when(title) {
        1 -> "Kuota 3GB"
        2 -> "Kuota 5GB"
        3 -> "Kuota 7,5GB"
        4 -> "Kuota 10GB + 2GB Kuota Malam"
        5 -> "12GB + 3GB Kuota Malam"
        6 -> "20GB + 5GB Kuota Malam"
        7 -> "25GB + 10GB Kuota Malam"
        else -> "NULL"
    }

    val getPrice = when(price) {
        1 -> "Rp40,000"
        2 -> "Rp50,000"
        3 -> "Rp60,000"
        4 -> "Rp75,000"
        5 -> "Rp90,000"
        6 -> "Rp100,000"
        7 -> "Rp120,000"
        else -> "NULL"
    }
}
