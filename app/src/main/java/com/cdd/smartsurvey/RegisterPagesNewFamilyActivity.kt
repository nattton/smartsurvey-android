package com.cdd.smartsurvey

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.cdd.smartsurvey.data.model.Family
import com.cdd.smartsurvey.http.model.Community
import com.cdd.smartsurvey.utils.*
import com.layernet.thaidatetimepicker.date.DatePickerDialog
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register_newfamily.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RegisterPagesNewFamilyActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    companion object {
        private const val TAG = "registerPagesActivity"
    }

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_TAKE_PHOTO = 1

    lateinit var currentPhotoPath: String
    var allMessage: AllMessage? = null
    var imageView: CircleImageView? = null

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
        val formUtil = FormUtils(this@RegisterPagesNewFamilyActivity, layoutInflater)
        txtPrefix.setOnClickListener { formUtil.ShowAlertDialogWithPrefix_Listview(txtPrefix, txtGender) }
        txtGender.setOnClickListener { formUtil.ShowAlertDialogWithGender_Listview(txtGender) }
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

    fun openMenuDetailSurvey() {
        val intent = Intent(this, MenuSurveyActivity::class.java)
        startActivity(intent)
    }

    fun StoreData() {
        val selectCommunity: Community = intent.getParcelableExtra(GlobalValue.EXTRA_COMMUNITY)

        var family = Family()

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