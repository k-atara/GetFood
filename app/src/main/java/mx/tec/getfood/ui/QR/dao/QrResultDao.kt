package mx.tec.getfood.ui.QR.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.tec.getfood.ui.QR.entities.QrResults



@Dao
interface QrResultDao {

    @Query("SELECT * FROM QrResults ORDER BY time DESC")
    fun getAllScannedResult(): List<QrResults>

    @Query("SELECT * FROM QrResults WHERE favourite = 1 ORDER BY time DESC")
    fun getAllFavouriteResult(): List<QrResults>

    @Query("DELETE FROM QrResults")
    fun deleteAllScannedResult()

    @Query("DELETE FROM QrResults WHERE favourite = 1")
    fun deleteAllFavouriteResult()

    @Query("DELETE FROM QrResults WHERE id = :id")
    fun deleteQrResult(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQrResult(qrResult: QrResults): Long

    @Query("SELECT * FROM QrResults WHERE id = :id")
    fun getQrResult(id: Int): QrResults

    @Query("UPDATE QrResults SET favourite = 1 WHERE id = :id")
    fun addToFavourite(id: Int): Int

    @Query("UPDATE QrResults SET favourite = 0 WHERE id = :id")
    fun removeFromFavourite(id: Int): Int

    @Query("SELECT * FROM QrResults WHERE result = :result ")
    fun checkIfQrResultExist(result: String): Int

}