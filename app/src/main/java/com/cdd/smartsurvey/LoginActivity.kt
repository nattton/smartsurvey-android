package com.cdd.smartsurvey

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cdd.smartsurvey.http.model.LoginResponse
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {
    var returnOut: String? = null
    var isLoading: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FuelManager.instance.basePath = GlobalValue.apiUrl
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        btnLogin.setOnClickListener { doLogin() }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val txtNetWorkStatus = findViewById<View>(R.id.txtNetworkStatus) as TextView
                    if (isNetworkAvailable == true) {
                        txtNetWorkStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.networkon, 0, 0, 0)
                        txtNetWorkStatus.setTextColor(resources.getColor(R.color.colorNetworkOn))
                        txtNetWorkStatus.text = "  Online / ออนไลน์"
                    } else {
                        txtNetWorkStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.networkoff, 0, 0, 0)
                        txtNetWorkStatus.setTextColor(resources.getColor(R.color.colorNetworkOff))
                        txtNetWorkStatus.text = "  Offline / ออฟไลน์"
                    }
                }
            }
        }, 0, 3000)

        checkLogin()
    }

    private fun checkLogin() {
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var user = sharedPref.getString(getString(R.string.pref_user), "")
        var userToken = sharedPref.getString(getString(R.string.pref_user_token), "")
        if (user != "" && userToken != "") {
            openMainMenu()
        }
    }

    private val isNetworkAvailable: Boolean
        private get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    fun openMainMenu() {
        val intent = Intent(this, FirstMenuActivity::class.java)
        startActivity(intent)
    }

    fun openRegister() {
        val intent = Intent(this, RegisterPagesActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val ab = AlertDialog.Builder(this@LoginActivity, R.style.AlertDialogTheme)
        ab.setTitle("แจ้งเตือน")
        ab.setMessage("ท่านต้องการออกจากโปรแกรมนี้หรือไม่ ?")
        ab.setPositiveButton("ตกลง") { dialog, which ->
            dialog.dismiss()
            finish()
        }
        ab.setNegativeButton("ยกเลิก") { dialog, which -> dialog.dismiss() }
        ab.show()
    }

    public override fun onDestroy() {
        Process.killProcess(Process.myPid())
        super.onDestroy()
    }

    fun doLogin() {
        if (isLoading) return
        isLoading = true
        val user = username!!.text.toString()
        val params = listOf(
                Pair("u", user),
                Pair("p", password!!.text.toString()),
                Pair("t", GlobalValue.apiToken),
                Pair("task", "login")
        )
        Fuel.post("mobile.php", params)
                .responseObject(LoginResponse.Deserializer()) { _, _, result ->
                    isLoading = false
                    when (result) {
                        is Result.Success -> {
                            val (loginResponse, _) = result
                            if (loginResponse?.status == "0") {
                                val ab = AlertDialog.Builder(this@LoginActivity, R.style.AlertDialogTheme)
                                ab.setTitle("แจ้งเตือน")
                                ab.setMessage("ไม่พบ Username / Password ไม่ถูกต้อง")
                                ab.setPositiveButton("ตกลง") { dialog, which -> dialog.dismiss() }
                                ab.show()
                            } else if (loginResponse?.status == "1") {
                                GlobalValue.loginid = loginResponse.token
                                val sharedPref = applicationContext.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                                with(sharedPref.edit()) {
                                    putString(getString(R.string.pref_user), user)
                                    putString(getString(R.string.pref_user_token), loginResponse.token)
                                    putString(getString(R.string.pref_activation), loginResponse.activation)
                                    putString(getString(R.string.pref_firstname), loginResponse.fname)
                                    putString(getString(R.string.pref_lastname), loginResponse.lname)
                                    putString(getString(R.string.pref_idcard), loginResponse.idcard)
                                    putString(getString(R.string.pref_addr), loginResponse.addr)
                                    putString(getString(R.string.pref_addr2), loginResponse.addr2)
                                    putString(getString(R.string.pref_position), loginResponse.position)
                                    putString(getString(R.string.pref_photo), loginResponse.photo)
                                    apply()
                                }

                                if (loginResponse.activation == "0") {
                                    openRegister()
                                } else if (loginResponse.activation == "1") {
                                    openMainMenu()
                                }
                            }
                        }
                        is Result.Failure -> {
                            returnOut = result.error.message
                        }
                    }
                }
    }
}