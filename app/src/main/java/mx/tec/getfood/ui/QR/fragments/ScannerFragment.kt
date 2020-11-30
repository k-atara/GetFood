package mx.tec.getfood.ui.QR.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_scanner.view.*
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import mx.tec.getfood.Confirmar
import mx.tec.getfood.R
import mx.tec.getfood.ui.QR.DBHelper
import mx.tec.getfood.ui.QR.HelperDB
import mx.tec.getfood.ui.QR.database.QrResultDataBase
import mx.tec.getfood.ui.QR.dialogs.QRCodeResultDialog
import org.json.JSONObject


class ScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    companion object{
        fun newInstance(): ScannerFragment {
            return ScannerFragment()

        }
    }

    private lateinit var mView: View
    lateinit var scannerView: ZBarScannerView
    lateinit var resultDialog: QRCodeResultDialog
    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_scanner, container, false)
        init()
        initViews()
        OnClick()
        return mView.rootView
    }

    private fun init() {
        dbHelper=HelperDB(QrResultDataBase.getAppDatabase(requireContext())!!)
    }

    private fun OnClick() {
        mView.flashToggle.setOnClickListener {
            if (mView.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun initViews() {
        InitializeQrScanner()
        setResultDialog()
    }

    private fun setResultDialog() {
        resultDialog = QRCodeResultDialog(requireContext())
        resultDialog.setOnDismissListener(object : QRCodeResultDialog.OnDismissListener{
            override fun onDismiss() {
                resetPreview()
            }

        })
//
    }
    private fun resetPreview() {
        scannerView.stopCamera()
        scannerView.startCamera()
        scannerView.stopCameraPreview()
        scannerView.resumeCameraPreview(this)
    }
    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    private fun InitializeQrScanner() {
        scannerView = ZBarScannerView(context)
        scannerView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorAlert))
        scannerView.setBorderColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        scannerView.setLaserColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        scannerView.setBorderStrokeWidth(10)
        scannerView.setSquareViewFinder(true)
        scannerView.setAutoFocus(true)
        scannerView.setResultHandler(this)
        startQRCamera()
        mView.containerScanner.addView(scannerView)

    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }
    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }
    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        onQrResults(rawResult?.contents)
        //scannerView.resumeCameraPreview(this)
    }

    private fun onQrResults(contents : String?) {
        if (contents.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Su codigo esta vacio",Toast.LENGTH_SHORT).show()
        }else{
            addQrCodigo(contents)
            saveToDataBase(contents)
        }
    }

    private fun saveToDataBase(contents: String) {
        val insertedResultId = dbHelper.insertQRResult(contents)
        val qrResult = dbHelper.getQRResult(insertedResultId)
        resultDialog.show(qrResult)
    }

    fun addQrCodigo(codigo: String){

        val sp = getActivity()?.getSharedPreferences("archivo", Context.MODE_PRIVATE)
        val user = sp?.getString("Usuario", "-1").toString()

        var json = JSONObject()
        json.put("usuario", user)
        json.put("codigo", codigo)

        Log.e("Mensaje de prueba", user + codigo)

        val uri = "http://192.168.1.102/getfood/registroCodigo"
        var queue = Volley.newRequestQueue(getActivity())
        val listener = Response.Listener<JSONObject> { response ->
            //Log.e("Mensaje", response.toString())
            if(response.getJSONObject("0").getString("resul").equals("0")) {
               // Toast.makeText(getActivity(), "Inténtalo de más tarde", Toast.LENGTH_SHORT).show()

            }
            if(response.getJSONObject("0").getString("resul").equals("1")){
              //  Toast.makeText(getActivity(), "Se registro con exito", Toast.LENGTH_SHORT).show()

            }

        }
        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)
    }

}