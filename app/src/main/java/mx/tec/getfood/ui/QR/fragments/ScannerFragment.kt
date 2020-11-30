package mx.tec.getfood.ui.QR.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_scanner.view.*
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import mx.tec.getfood.R
import mx.tec.getfood.ui.QR.DBHelper
import mx.tec.getfood.ui.QR.HelperDB
import mx.tec.getfood.ui.QR.database.QrResultDataBase
import mx.tec.getfood.ui.QR.dialogs.QRCodeResultDialog


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
            Toast.makeText(requireContext(),"Empty Qr Code",Toast.LENGTH_SHORT).show()
        }else{
            saveToDataBase(contents)
        }
    }

    private fun saveToDataBase(contents: String) {
        val insertedResultId = dbHelper.insertQRResult(contents)
        val qrResult = dbHelper.getQRResult(insertedResultId)
        resultDialog.show(qrResult)
    }

}