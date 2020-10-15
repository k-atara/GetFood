package mx.tec.getfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mx.tec.getfood.R

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val b0=findViewById<Button>(R.id.btn_registro)
        b0.setOnClickListener {
            val i = Intent(this@Registro, LogIn::class.java)

            startActivity(i)
        }

        val b1=findViewById<Button>(R.id.btm_cancelar)
        b1.setOnClickListener {
            val i = Intent(this@Registro, MainActivity::class.java)

            startActivity(i)
        }

    }
}