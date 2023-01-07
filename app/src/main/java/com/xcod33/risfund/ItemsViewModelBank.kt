package com.xcod33.risfund

data class ItemsViewModelBank(val image: Int, val bankName: Int) {
    val getImage = when(image) {
        1 -> R.drawable.ic_launcher_background
        2 -> R.drawable.ic_launcher_background
        3 -> R.drawable.ic_launcher_background
        4 -> R.drawable.ic_launcher_background
        5 -> R.drawable.ic_launcher_background
        else -> R.drawable.ic_launcher_background
    }

    val getBankName = when(bankName) {
        1 -> "nama bank"
        2 -> "nama bank"
        3 -> "nama bank"
        4 -> "nama bank"
        5 -> "nama bank"
        else -> "nama bank"
    }
}
