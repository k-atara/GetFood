package mx.tec.getfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val b0=findViewById<Button>(R.id.btn_ingresar)
        b0.setOnClickListener {
            val i = Intent(this@LogIn, Menu::class.java)

            startActivity(i)
        }

    }
}