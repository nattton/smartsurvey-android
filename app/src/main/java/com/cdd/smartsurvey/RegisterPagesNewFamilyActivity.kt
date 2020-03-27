package com.cdd.smartsurvey

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.cdd.smartsurvey.RegisterPagesNewFamilyActivity
import com.cdd.smartsurvey.data.model.Answer
import com.cdd.smartsurvey.data.model.Family
import com.cdd.smartsurvey.data.model.Member
import com.cdd.smartsurvey.http.model.Community
import com.cdd.smartsurvey.sqlite.DatabaseHelper
import com.cdd.smartsurvey.sqlite.model.Gender
import com.cdd.smartsurvey.sqlite.model.Prefix
import com.cdd.smartsurvey.utils.AllMessage
import com.cdd.smartsurvey.utils.GetThaiCharector
import com.cdd.smartsurvey.utils.ImageUtil
import com.layernet.thaidatetimepicker.date.DatePickerDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register_newfamily.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterPagesNewFamilyActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    companion object {
        private const val TAG = "registerPagesActivity"
        const val MY_BLINKID_REQUEST_CODE = 123
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_TAKE_PHOTO = 1
    private val RECORD_REQUEST_CODE = 101

    lateinit var currentPhotoPath: String
    var returnOut: String? = null
    var allMessage: AllMessage? = null
    var imageView: CircleImageView? = null
    private var mDisplayDate: TextView? = null
    var btnStartSurvey: Button? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_newfamily)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)
        allMessage = AllMessage()
        imageView = findViewById<View>(R.id.profileimage) as CircleImageView
        btnCapture!!.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 1)
        }

        txtBirthDate.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd = DatePickerDialog.newInstance(
                    this@RegisterPagesNewFamilyActivity,
                    now[Calendar.YEAR],
                    now[Calendar.MONTH],
                    now[Calendar.DAY_OF_MONTH]
            )

            dpd.show(fragmentManager, "Datepickerdialog");
        }
        btnSave.setOnClickListener { StoreData() }
        txtName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    if (!GetThaiCharector.checkThai(s)) {
                        val ab = AlertDialog.Builder(this@RegisterPagesNewFamilyActivity, R.style.AlertDialogTheme)
                        ab.setTitle("แจ้งเตือน")
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น")
                        ab.setPositiveButton("ตกลง") { dialog, which ->
                            dialog.dismiss()
                            txtName!!.setText("")
                        }
                        ab.show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        txtSurname.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    if (!GetThaiCharector.checkThai(s)) {
                        val ab = AlertDialog.Builder(this@RegisterPagesNewFamilyActivity, R.style.AlertDialogTheme)
                        ab.setTitle("แจ้งเตือน")
                        ab.setMessage("ต้องใช้เป็นภาษาไทยเท่านั้น")
                        ab.setPositiveButton("ตกลง") { dialog, which -> dialog.dismiss() }
                        ab.show()
                    }
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
        txtPrefix.setOnClickListener { ShowAlertDialogWithPrefix_Listview() }
        txtGender!!.setOnClickListener { ShowAlertDialogWithGender_Listview() }
        btnBack.setOnClickListener { onBackPressed() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val processBitmap = resizeBitmap(currentPhotoPath)
            saveImage(processBitmap, currentPhotoPath)
//            val processBitmap = BitmapFactory.decodeFile(currentPhotoPath)
            profileimage.setImageBitmap(processBitmap)
        }

//        try {
//            val bitmap = data!!.extras["data"] as Bitmap
//            if (bitmap != null) {
//                txtPicture = ImageUtil.convert(bitmap)
//                imageView!!.setImageBitmap(bitmap)
//            }
//            if (requestCode != MY_BLINKID_REQUEST_CODE) {
//                return
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    private fun onScanCanceled() {
        Toast.makeText(this, "Scan cancelled!", Toast.LENGTH_SHORT).show()
    }

    fun openMenuDetailSurvey() {
        val intent = Intent(this, MenuSurveyActivity::class.java)
        startActivity(intent)
    }

    fun ShowAlertDialogWithPrefix_Listview() {
        val txtSearch: EditText
        val db: DatabaseHelper
        val prefixList: MutableList<Prefix> = ArrayList()
        db = DatabaseHelper(this)
        prefixList.addAll(db.allPrefixs)
        var item: Prefix
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in prefixList.indices) {
            item = prefixList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesNewFamilyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_prefix_valuelist, null)
        val prefixAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                prefixAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = prefixAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtPrefix!!.tag = clickItemMap["code"]
            txtPrefix!!.text = clickItemMap["codename"] as String?
            if (txtPrefix!!.tag == "001" || txtPrefix!!.tag == "002") {
                txtGender!!.tag = "1"
                txtGender!!.text = "ชาย (male)"
            } else {
                txtGender!!.tag = "2"
                txtGender!!.text = "หญิง (Female)"
            }
            show.dismiss()
        }
        val btnClose = mView.findViewById<View>(R.id.btnClose) as Button
        btnClose.setOnClickListener { show.dismiss() }
    }

    fun ShowAlertDialogWithGender_Listview() {
        val txtSearch: EditText
        val genderList: MutableList<Gender> = ArrayList()
        val db: DatabaseHelper
        db = DatabaseHelper(this)
        genderList.addAll(db.allGenders)
        var item: Gender
        val itemDataList = ArrayList<Map<String, Any?>>()
        for (i in genderList.indices) {
            item = genderList[i]
            val listItemMap: MutableMap<String, Any?> = HashMap()
            listItemMap["code"] = item.code
            listItemMap["codename"] = item.codename
            itemDataList.add(listItemMap)
        }
        val mBuilder = AlertDialog.Builder(this@RegisterPagesNewFamilyActivity)
        val mView = layoutInflater.inflate(R.layout.dialog_gender_valuelist, null)
        val genderAdapter = SimpleAdapter(this, itemDataList, android.R.layout.simple_list_item_2, arrayOf("codename"), intArrayOf(android.R.id.text1))
        txtSearch = mView.findViewById<View>(R.id.editText) as EditText
        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                genderAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        val list = mView.findViewById<View>(R.id.lstValue) as ListView
        list.adapter = genderAdapter
        mBuilder.setView(mView)
        val show = mBuilder.show()
        list.onItemClickListener = OnItemClickListener { adapterView, view, index, l ->
            val clickItemObj = adapterView.adapter.getItem(index)
            val clickItemMap = clickItemObj as HashMap<*, *>
            txtGender!!.tag = clickItemMap["code"]
            txtGender!!.text = clickItemMap["codename"] as String?
            show.dismiss()
        }
        var btnClose = mView.findViewById<View>(R.id.btnClose)
        btnClose!!.setOnClickListener { show.dismiss() }
    }

    fun StoreData() {
        val selectCommunity: Community = intent.getParcelableExtra(GlobalValue.EXTRA_COMMUNITY)

        var family = Family("",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ArrayList<Member>(),
                Answer("", "", ""))

        with(family) {
            community = selectCommunity.community_id
            prefix = txtPrefix.tag.toString()
            fname = txtName.text.toString()
            lname = txtSurname.text.toString()
            idcard = txtCardID.text.toString()
            gender = txtGender.tag.toString()
            birthdate = txtBirthDate.text.toString()
        }


        val intent = Intent(this, AddressSurveyActivity::class.java).apply {
            putExtra(GlobalValue.EXTRA_COMMUNITY, selectCommunity)
            putExtra(GlobalValue.EXTRA_FAMILY, family)
        }
        startActivity(intent)
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File

                    null
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.cdd.smartsurvey.fileprovider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }

        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    fun resizeBitmap(imagePath: String): Bitmap {
        val scale = resources.getDisplayMetrics().density
        var b = BitmapFactory.decodeFile(imagePath)

        val origWidth = b.width
        val origHeight = b.height

        val destWidth = 2048

        if (origWidth > destWidth) {
            // picture is wider than we want it, we calculate its target height
            val destHeight = origHeight / (origWidth / destWidth);
            // we create an scaled bitmap so it reduces the image, not just trim it
            var b2 = Bitmap.createScaledBitmap(b, destWidth, destHeight, false);

            return b2
        }

        return b
    }

    private fun saveImage(finalBitmap: Bitmap, toPath: String) {
        var bos = ByteArrayOutputStream()
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos)
        var data = bos.toByteArray()

        val file = File(toPath)
        if (file.exists()) file.delete()
        try {
            val stream = FileOutputStream(file)
            stream.write(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        Log.d(RegisterPagesNewFamilyActivity.TAG, "onDateSet: dd/mm/yyyy: $dayOfMonth/${monthOfYear + 1}/${year + 543}")
        val date = "$dayOfMonth/${monthOfYear + 1}/${year + 543}"
        txtBirthDate.text = date
    }
}