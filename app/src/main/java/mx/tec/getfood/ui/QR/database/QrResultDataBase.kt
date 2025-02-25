package mx.tec.getfood.ui.QR.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.tec.getfood.ui.QR.dao.QrResultDao
import mx.tec.getfood.ui.QR.entities.QrResults


@Database(entities = [QrResults::class], version = 1,exportSchema = false)
abstract class QrResultDataBase  : RoomDatabase() {
    abstract fun getQrDao(): QrResultDao
    companion object {
        private const val DB_NAME = "QrResultDatabase"
        private var qrResultDataBase: QrResultDataBase? = null
        fun getAppDatabase(context: Context): QrResultDataBase? {
            if (qrResultDataBase == null) {
                qrResultDataBase =
                    Room.databaseBuilder(context.applicationContext, QrResultDataBase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
            }
            return qrResultDataBase
        }

        fun destroyInstance() {
            qrResultDataBase = null
        }
    }
}