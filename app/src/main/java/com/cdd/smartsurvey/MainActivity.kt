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

    fun SendHistory(aurl: String) {
        val LoginAPIRequest: StringRequest = object : StringRequest(Method.POST, aurl + "insertinfomobile", Response.Listener { response ->
            try {
                jobj = JSONObject(response)
                uid = jobj!!["status"].toString()
                GlobalValue.uid = jobj!!["status"].toString()
                CheckDownloadDB(GlobalValue.dbUrl)
            } catch (e: JSONException) {
            }
        }, //Create an error listener to handle errors appropriately.
                Response.ErrorListener { }) {
            override fun getParams(): Map<String, String> {
                val MyData: MutableMap<String, String> = HashMap()
                MyData["deviceid"] = android_id.toString() //Add the data you'd like to send to the server
                return MyData
            }
        }
        MyRequestQueue!!.add(LoginAPIRequest)
    }

    fun CheckDownloadDB(aurl: String) {
        val LoginAPIRequest: StringRequest = object : StringRequest(Method.POST, aurl + "checkdownloaddb", Response.Listener { response ->
            try {
                jobj = JSONObject(response)
                if (jobj!!["status"].toString() == "0") {
                    val thread: Thread = object : Thread() {
                        override fun run() {
                            try {
                                sleep(3000)
                                stopThread(this)
//                                DownloadDBWithProgressDialog().execute(dbURL)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    thread.start()
                } else {
                    openLogin()
                }
            } catch (e: JSONException) {
            }
        }, //Create an error listener to handle errors appropriately.
                Response.ErrorListener { }) {
            override fun getParams(): Map<String, String> {
                val MyData: MutableMap<String, String> = HashMap()
                MyData["deviceid"] = android_id.toString() //Add the data you'd like to send to the server
                MyData["uid"] = uid.toString()
                return MyData
            }
        }
        MyRequestQueue!!.add(LoginAPIRequest)
    }

//    inner class DownloadDBWithProgressDialog : AsyncTask<String?, String?, String?>() {
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//        protected override fun doInBackground(vararg aurl: String): String? {
//            var count: Int
//            try {
//                val dataFolder = File("/data/data/com.cdd.smartsurvey/databases")
//                if (!dataFolder.exists()) {
//                    dataFolder.mkdirs()
//                }
//                url = URL(aurl[0])
//                urlconnection = url!!.openConnection() as HttpURLConnection
//                urlconnection!!.responseCode
//                FileSize = urlconnection!!.contentLength
//                inputstream = urlconnection!!.inputStream
//                outputstream = FileOutputStream("/data/data/com.cdd.smartsurvey/databases/smartsurvey_master.db")
//                while (inputstream.read(dataArray).also { count = it } != -1) {
//                    totalSize += count.toLong()
//                    publishProgress("" + (totalSize * 100 / FileSize).toInt())
//                    outputstream.write(dataArray, 0, count)
//                }
//                outputstream.flush()
//                outputstream.close()
//                inputstream.close()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            return null
//        }

//        override fun onPostExecute(unused: String?) {
//            Toast.makeText(this@MainActivity, "DB Downloaded Successfully", Toast.LENGTH_LONG).show()
//            openLogin()
//        }
//    }
}