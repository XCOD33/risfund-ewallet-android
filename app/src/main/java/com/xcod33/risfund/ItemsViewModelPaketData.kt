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
        1 -> 40000
        2 -> 50000
        3 -> 60000
        4 -> 75000
        5 -> 95000
        6 -> 100000
        7 -> 120000
        else -> "NULL"
    }
}
