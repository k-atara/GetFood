package mx.tec.getfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val b0=findViewById<Button>(R.id.btnIniciar)
        b0.setOnClickListener {
            val i = Intent(this@MainActivity, LogIn::class.java)

            startActivity(i)
        }

    }
}