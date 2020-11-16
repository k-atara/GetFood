package mx.tec.qrscanner.db

import mx.tec.getfood.ui.QR.entities.QrResults
import mx.tec.qrscanner.db.database.QrResultDataBase
import java.util.*

class HelperDB(var qrResultDataBase: QrResultDataBase) :DBHelper {
    override fun insertQRResult(result: String): Int {
        val time = Calendar.getInstance()
        val resultType = findResultType(result)
        val qrResult = QrResults(result = result, resultType = resultType, calendar = time, favourite = false)
        return qrResultDataBase.getQrDao().insertQrResult(qrResult).toInt()
    }

    override fun getQRResult(id: Int): QrResults {
        return qrResultDataBase.getQrDao().getQrResult(id)
    }

    override fun addToFavourite(id: Int): Int {
        return qrResultDataBase.getQrDao().addToFavourite(id)
    }

    override fun removeFromFavourite(id: Int): Int {
        return qrResultDataBase.getQrDao().removeFromFavourite(id)
    }

    override fun deleteQrResult(id: Int): Int {
        return qrResultDataBase.getQrDao().deleteQrResult(id)
    }

    override fun getAllQRScannedResult(): List<QrResults> {
        return qrResultDataBase.getQrDao().getAllScannedResult()
    }

    override fun getAllFavouriteQRScannedResult(): List<QrResults> {
        return qrResultDataBase.getQrDao().getAllFavouriteResult()
    }

    override fun deleteAllQRScannedResult() {
        qrResultDataBase.getQrDao().deleteAllScannedResult()
    }

    override fun deleteAllFavouriteQRScannedResult() {
        qrResultDataBase.getQrDao().deleteAllFavouriteResult()
    }

    /*
    * This feature will add in future
    * */
    private fun findResultType(result: String): String {
        return "TEXT"
    }
}