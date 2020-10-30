package mx.tec.getfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Pedido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        val b0=findViewById<Button>(R.id.btnOrdenar)
        b0.setOnClickListener {
            val i = Intent(this@Pedido, Menu::class.java)

            startActivity(i)
        }

    }
}