package mx.tec.getfood.ui.QR.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import mx.tec.getfood.R

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            CheckForPermision()
        }, 2000)
        //CheckForPermision()
    }

    private fun CheckForPermision() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            goToMainActivty()
        }else{
            requestThePermission()
        }
    }

    private fun goToMainActivty() {
        startActivity(Intent(this, MainQR::class.java))
        finish()
    }

    private fun requestThePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToMainActivty()
            } else if (isPermanentlyDenied()) {
                showGoToAppSettingsDialog()
            }
            else
                requestPermission()
        }
    }

    private fun isPermanentlyDenied(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA).not()
        } else {
            return false
        }
    }
    private fun showGoToAppSettingsDialog() {
        AlertDialog.Builder(this)
            .setTitle("Grand Permission!!")
            .setMessage("We need camera permission to sacn QR code")
            .setPositiveButton("Grant") { dialog, which ->
                goToAppSettings()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(this,"We need permision for start this aplication", Toast.LENGTH_SHORT).show()
                finish()
            }.show()
    }
    fun goToAppSettings(){
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    override fun onRestart() {
        super.onRestart()
        CheckForPermision()
    }


}