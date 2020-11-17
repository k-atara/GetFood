package mx.tec.getfood.ui.QR

import mx.tec.getfood.ui.QR.entities.QrResults


interface DBHelper {

    fun insertQRResult(result: String): Int

    fun getQRResult(id: Int): QrResults

    fun addToFavourite(id: Int): Int

    fun removeFromFavourite(id: Int): Int

    fun deleteQrResult(id: Int): Int

    fun getAllQRScannedResult(): List<QrResults>

    fun getAllFavouriteQRScannedResult(): List<QrResults>

    fun deleteAllQRScannedResult()

    fun deleteAllFavouriteQRScannedResult()
}