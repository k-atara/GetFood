package mx.tec.getfood

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val btnIngresar=findViewById<Button>(R.id.btn_ingresar)
        val edtUsuario = findViewById<EditText>(R.id.edtNombre)
        val edtContrasena = findViewById<EditText>(R.id.edtContrasena)

        btnIngresar.setOnClickListener {
            val usuario = edtUsuario.text.toString()
            val contra = edtContrasena.text.toString()

            if (usuario != "" && contra != "") {
               addPersona(usuario, contra)
            }else{
                Toast.makeText(this@LogIn, "Completa los campos", Toast.LENGTH_SHORT).show()
            }

        }

        val btnRegistrarse=findViewById<Button>(R.id.btn_reg)
        btnRegistrarse.setOnClickListener {
            val i = Intent(this@LogIn, Registro::class.java)

            startActivity(i)
        }

    }
    fun addPersona(usuario:String, contra:String){

        var json = JSONObject()
        json.put("usuario", usuario)
        json.put("contra", contra)

        var resp = ""
        val uri = "http://192.168.0.3/getfood/login"
        var queue = Volley.newRequestQueue(this)
        val listener = Response.Listener<JSONObject> { response ->
            Log.e("Mensaje", response.toString())

            if(response.toString().contains("correcto")){
                Toast.makeText(this@LogIn, "Bienvenido", Toast.LENGTH_SHORT).show()
                val sp = getSharedPreferences("archivo", Context.MODE_PRIVATE)

                with(sp.edit()) {
                    putString("Usuario", usuario)
                    putString("Password", contra)
                    commit()
                }

                val i = Intent(this@LogIn, Menu::class.java)
                startActivity(i)
            }
            if(response.toString().contains("usuario"))
                Toast.makeText(this@LogIn, "El usuario no existe", Toast.LENGTH_SHORT).show()
            if(response.toString().contains("contra"))
                Toast.makeText(this@LogIn, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            Log.e("Mensaje", resp)
        }
        val error = Response.ErrorListener { error ->
            Log.e("MensajeERRLOG", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)
    }
}