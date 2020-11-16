package mx.tec.getfood.ui.QR.converters
import androidx.room.TypeConverter
import java.util.*
class DateTimeConverter {
    @TypeConverter
    fun toCalendar(l: Long): Calendar? {
        val c = Calendar.getInstance()
        c!!.timeInMillis = l
        return c
    }

    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? {
        return c?.time?.time
    }
}