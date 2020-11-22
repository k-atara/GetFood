package mx.tec.getfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Confirmar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar)

        val resp = intent.getStringExtra("mensaje")

        var txtMensaje = findViewById<TextView>(R.id.txtMensaje)
        txtMensaje.text = resp

        var btnContinuar = findViewById<Button>(R.id.btnContinuar)
        btnContinuar.setOnClickListener{
            val i = Intent(this@Confirmar, Menu::class.java)
            startActivity(i)
        }
    }
}