package com.xcod33.risfund

data class ItemsViewModelVoucher(val title: Int, val price: Int) {
    val getTitle = when(title) {
        1 -> "20,000"
        2 -> "50,000"
        3 -> "100,000"
        4 -> "150,000"
        5 -> "300,000"
        else -> "NULL"
    }

    val getPrice = when(price) {
        1 -> "Rp22,000"
        2 -> "Rp52,000"
        3 -> "Rp102,000"
        4 -> "Rp152,000"
        5 -> "Rp302,000"
        else -> "NULL"
    }
}
