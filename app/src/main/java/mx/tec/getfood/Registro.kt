package mx.tec.getfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val b0=findViewById<Button>(R.id.btn_registro)
        b0.setOnClickListener {
            val nombre = edtUsuario.text.toString()
            val correo = edtCorreo.text.toString()
            val usuario = edtUsuario.text.toString()
            var contrasena = edtContrasena.text.toString()

            addPersona(nombre, correo, usuario, contrasena)

            val i = Intent(this@Registro, LogIn::class.java)
            Toast.makeText(this, "Registro completado", Toast.LENGTH_SHORT).show();
            startActivity(i)

        }

        val b1=findViewById<Button>(R.id.btm_cancelar)
        b1.setOnClickListener {
            val i = Intent(this@Registro, MainActivity::class.java)
            startActivity(i)
        }

    }
    fun addPersona(nombre:String, correo:String, usuario:String, contrasena:String){

        var json = JSONObject()
        json.put("nombre", nombre)
        json.put("correo", correo)
        json.put("nickname", usuario)
        json.put("password", contrasena)

        var resp = false;
        val uri = "http://10.0.2.2/getfood/usuario"
        var queue = Volley.newRequestQueue(this)
        val listener = Response.Listener<JSONObject> { response ->
            Log.e("Mensaje", response.toString())
            resp=true
        }
        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)
    }
}