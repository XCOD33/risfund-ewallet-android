package com.xcod33.risfund

data class ItemsViewModelPaketData(val image: Int, val title: Int, val description: Int, val lineImage: Int) {
    val getImage = when(image) {
        1 -> R.drawable.icon_kuota
        2 -> R.drawable.icon_kuota
        3 -> R.drawable.icon_kuota
        4 -> R.drawable.icon_kuota
        5 -> R.drawable.icon_kuota
        6 -> R.drawable.icon_kuota
        7 -> R.drawable.icon_kuota
        8 -> R.drawable.icon_kuota
        9 -> R.drawable.icon_kuota
        else -> R.drawable.ic_launcher_background
    }

    val getTitle = when(title) {
        1 -> "Juventus"
        2 -> "AC Milan"
        3 -> "Inter Milan"
        4 -> "Paris Saint German"
        5 -> "Manchester City"
        6 -> "Manchester United"
        7 -> "Liverpool"
        8 -> "Barcelona"
        9 -> "Real Madrid"
        else -> "Error Club Name invalid!"
    }

    val getDescription = when(description) {
        1 -> "Juventus adalah club sepak bola profesional asal Italia yang berbasis di kota Turin, Piemonte"
        2 -> "AC Milan adalah club sepak bola profesional asal Italia yang berbasis di kota Milan"
        3 -> "Inter Milan adalah club sepak bola profesional asal Italia yang berbasis di kota Milan"
        4 -> "PSG adalah Club sepak bola profesional asal Prancis yang berbasis di kota Paris"
        5 -> "Manchester City adalah sepak bola profesional asal Inggris yang berbasis di kota Manchester"
        6 -> "Manchester United adalah sepak bola profesional asal Inggris yang berbasis di kota Manchester"
        7 -> "Liverpool adalah sepak bola profesional asal Inggris yang berbasis di kota Liverpool"
        8 -> "Barcelona adalah sepak bola profesional asal Spanyol yang berbasis di kota Catalunya"
        9 -> "Real Madrid adalah sepak bola profesional asal Spanyol yang berbasis di kota Madrid"
        else -> "Error Club Description invalid!"
    }

    val getLineImage = when(lineImage) {
        1 -> R.drawable.linebottom
        2 -> R.drawable.linebottom
        3 -> R.drawable.linebottom
        4 -> R.drawable.linebottom
        5 -> R.drawable.linebottom
        6 -> R.drawable.linebottom
        7 -> R.drawable.linebottom
        8 -> R.drawable.linebottom
        9 -> R.drawable.linebottom
        else -> R.drawable.ic_launcher_background
    }
}