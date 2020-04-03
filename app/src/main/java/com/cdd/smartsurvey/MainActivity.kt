package com.cdd.smartsurvey

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    var dbURL = "http://smartsurvey.ddns.me/SmartSurveyAPI_war_exploded/downloaddb/db"
    var url: URL? = null
    var urlconnection: HttpURLConnection? = null
    var FileSize = 0
    var dataArray = ByteArray(1024)
    var totalSize: Long = 0
    var MyRequestQueue: RequestQueue? = null
    var android_id: String? = null
    var uid: String? = null
    var jobj: JSONObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        android_id = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        MyRequestQueue = Volley.newRequestQueue(this)
        //        SendHistory(GlobalValue.dbUrl);
        CopyDBFromAssets()
        openLogin()
    }

    @Synchronized
    private fun stopThread(theThread: Thread) {
        var theThread: Thread? = theThread
        if (theThread != null) {
            theThread = null
        }
    }

    fun openLogin() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    fun CopyDBFromAssets() {
        var count: Int
        try {
            val dataFolder = File("/data/data/com.cdd.smartsurvey/databases")
            if (!dataFolder.exists()) {
                dataFolder.mkdirs()
            }
            val inputstream = applicationContext.assets.open("smartsurvey_master.db")
            val outputstream = FileOutputStream("/data/data/com.cdd.smartsurvey/databases/smartsurvey_master.db")

            val buffer = ByteArray(1024)
            var length: Int = inputstream.read(buffer)
            while ((length) > 0) {
                outputstream.write(buffer, 0, length)
                length = inputstream.read(buffer)
            }
            inputstream.close()
            outputstream.flush()
            outputstream.close()


            while (inputstream.read(dataArray).also { count = it } != -1) {
                outputstream.write(dataArray, 0, count)
            }
            outputstream.flush()
            outputstream.close()
            inputstream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}