package com.xcod33.risfund

data class ItemsViewModelTransferConfirmation  (val title: Int, val detail: Int) {
    val getTitle = when(title) {
        1 -> "Pembayaran Ke"
        2 -> "Deskripsi"
        3 -> "Jumlah Transfer"
        4 -> "No. Transaksi"
        5 -> "Waktu Selesai"
        else -> "NULL"
    }

    val getDetail = when(detail) {
        1 -> "89571287146"
        2 -> "Bayar Hutang"
        3 -> "Rp20.000"
        4 -> "1a8c4fdb-8a86-4744-9d06-4fb54d6d8333"
        5 -> "12-12-2022 10:31"
        else -> "NULL"
    }
}