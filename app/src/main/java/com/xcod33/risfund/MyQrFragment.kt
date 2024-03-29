package com.xcod33.risfund

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.fragment_my_qr.*

class MyQrFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_qr, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = requireActivity().intent.getParcelableExtra<GetUserResponse>("dataUser")

        val writer = QRCodeWriter()
        try {

            val bitMatrix = writer.encode(user!!.userQr.toString(), BarcodeFormat.QR_CODE, 612, 612)
            val width = bitMatrix.width
            val heigth = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, heigth, Bitmap.Config.RGB_565)
            for (x in 0 until width){
                for (y in 0 until heigth) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            qrImageView.setImageBitmap(bmp)

            namaPenggunaTextView.text = user.fullName
        } catch (e: WriterException){
            e.printStackTrace()
        }
    }
}