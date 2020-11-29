package mx.tec.getfood.ui.QR.dialogs

import android.app.Dialog
import android.content.Context
import kotlinx.android.synthetic.main.layout_resultado.*
import mx.tec.getfood.R
import mx.tec.getfood.ui.QR.entities.QrResults
import mx.tec.getfood.ui.QR.utils.toFormattedDisplay

class QRCodeResultDialog(var context: Context) {
    private lateinit var  dialog: Dialog
    private var qrResults : QrResults?=null
    private var onDismissListener: OnDismissListener? = null

    init {
        init()
        initDialog()
    }


    private fun init() {

    }

    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_resultado)
        dialog.setCancelable(false)
        onClick()
    }

    fun show(recentQrResult: QrResults) {
        this.qrResults = recentQrResult
       dialog.scannedDate.text = qrResults?.calendar?.toFormattedDisplay()
        dialog.scannedText.text = qrResults!!.result
        dialog.show()
    }

    private fun onClick() {
        dialog.cancelDialog.setOnClickListener {
            dialog.dismiss()
            onDismissListener?.onDismiss()
        }
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    fun setOnDismissListener(dismissListener: OnDismissListener) {
        this.onDismissListener = dismissListener
    }
}