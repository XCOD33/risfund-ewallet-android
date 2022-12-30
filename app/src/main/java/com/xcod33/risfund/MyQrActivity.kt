package com.xcod33.risfund

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MyQrActivity : AppCompatActivity() {

    private lateinit var qrImageView: ImageView
    private lateinit var bottomNav: com.google.android.material.bottomnavigation.BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_qr)

        qrImageView = findViewById(R.id.qrImageView)
        val data = "ivan"
        val writer = QRCodeWriter()
        try {

            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 612, 612)
            val width = bitMatrix.width
            val heigth = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, heigth, Bitmap.Config.RGB_565)
            for (x in 0 until width){
                for (y in 0 until heigth) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            qrImageView.setImageBitmap(bmp)
        } catch (e: WriterException){
            e.printStackTrace()
        }


    }
}