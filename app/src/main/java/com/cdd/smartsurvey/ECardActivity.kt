package com.cdd.smartsurvey

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.cdd.smartsurvey.utils.ImageUtil
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import kotlinx.android.synthetic.main.activity_ecard.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class ECardActivity : AppCompatActivity() {
    private var barcodeWaterMark: ImageView? = null
    private val size = 2000
    private val size_width = 2000
    private val size_height = 264
    private var eCardBitmap: Bitmap? = null
    private val WaterMarkBitmap: Bitmap? = null
    var btnBackToMenu: Button? = null
    var jobj: JSONObject? = null
    var MyRequestQueue: RequestQueue? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecard)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val firstname = sharedPref.getString(getString(R.string.pref_firstname), "")
        val lastname = sharedPref.getString(getString(R.string.pref_lastname), "")
        txtName.text = "${firstname} ${lastname}"
        val idCard = sharedPref.getString(getString(R.string.pref_idcard), "")
        txtCardID.text = idCard
        txtAddress1.text = sharedPref.getString(getString(R.string.pref_addr), "")
        txtAddress2.text = sharedPref.getString(getString(R.string.pref_addr2), "")
        val position = sharedPref.getString(getString(R.string.pref_position), "")
        txtQuestionerType.text = "ตำแหน่ง ${position}"
        val profileURL = sharedPref.getString(getString(R.string.pref_photo), "")
        Glide.with(applicationContext)
                .load(profileURL)
                .fitCenter()
                .into(imgCard)

        barcodeWaterMark = findViewById<View>(R.id.imageWaterMark) as ImageView
        MyRequestQueue = Volley.newRequestQueue(this)
        btnBackToMenu = findViewById<View>(R.id.btnBackToMenu) as Button
        btnBackToMenu!!.setOnClickListener { onBackPressed() }
        var bitmap: Bitmap? = null
        val waterbitmap: Bitmap? = null
        try {
            bitmap = CreateBarcode(idCard, "QR Code")
            eCardBitmap = bitmap
            imgBarcode.setImageBitmap(bitmap)

        } catch (we: WriterException) {
            we.printStackTrace()
        }

        ReceiveData(GlobalValue.dbUrl)
    }

    @Throws(WriterException::class)
    fun CreateBarcode(message: String?, type: String?): Bitmap {
        var bitMatrix: BitMatrix? = null
        bitMatrix = when (type) {
            "QR Code" -> MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size)
            else -> MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, size, size)
        }
        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (bitMatrix[j, i]) {
                    pixels[i * width + j] = -0x1000000
                } else {
                    pixels[i * width + j] = -0x1
                }
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }

    fun ReceiveData(aurl: String) {
        var subpath = ""
        if (GlobalValue.qestionerid != "0") {
            subpath = "questioner/profile/select"
        }
        val LoginAPIRequest: StringRequest = object : StringRequest(Method.POST, aurl + subpath, Response.Listener { response ->
            try {
                jobj = JSONObject(response)
                val array = jobj!!.getJSONArray("TransQuestionerProfile")
                val jdata = array.getJSONObject(0)
                txtCardID.text = jdata["idcard"].toString()
                txtName!!.text = jdata["tbl_prefix_name"].toString() +
                        jdata["name"].toString() + " " + jdata["surname"].toString()
                txtAddress1!!.text = jdata["address1"].toString() + " " + jdata["tbl_community_name"].toString()
                txtAddress2!!.text = "ต." + jdata["tbl_tumbon_name"].toString() + " " + "อ." + jdata["tbl_amphur_name"].toString() +
                        " " + "จ." + jdata["tbl_province_name"].toString()
                when (jdata["questioner_type"].toString()) {
                    "1" -> txtQuestionerType!!.text = "ตำแหน่ง หัวหน้าตำบล"
                    "2" -> txtQuestionerType!!.text = "ตำแหน่ง รองหัวหน้าตำบล"
                    "3" -> txtQuestionerType!!.text = "ตำแหน่ง สมาชิกตำบล"
                }
//                imgcard!!.setImageBitmap(ImageUtil.convert(jdata["picture"].toString()))
            } catch (e: JSONException) {
            }
        }, //Create an error listener to handle errors appropriately.
                Response.ErrorListener { }) {
            override fun getParams(): Map<String, String> {
                val MyData: MutableMap<String, String> = HashMap()
                MyData["questionerid"] = GlobalValue.qestionerid
                return MyData
            }
        }
        MyRequestQueue!!.add(LoginAPIRequest)
    }
}