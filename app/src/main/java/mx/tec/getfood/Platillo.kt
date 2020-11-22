package mx.tec.getfood

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_platillo.*
import kotlinx.coroutines.sync.Semaphore
import mx.tec.getfood.ui.orden.Orden
import org.json.JSONObject

class Platillo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_platillo)

        val id=intent.getStringExtra("id")
        val nombre=intent.getStringExtra("nombre")
        val descripcion=intent.getStringExtra("descripcion")
        val costo =intent.getStringExtra("costo")
        val puntos = intent.getStringExtra("puntos")
        val imagen=intent.getStringExtra("imagen")



        val sp = getSharedPreferences("orden", Context.MODE_PRIVATE)


        var ids = sp.getStringSet("ids", mutableSetOf()) as MutableSet<String>
        txtNombrePlatillo.text= nombre;
        txtDescripcionPlatillo.text=descripcion;
        txtCostoPlatillo.text=costo;
        txtPuntosPlatillo.text=puntos;



        btnOrdenar.setOnClickListener{

            ids!!.add(id!!)

            with(sp.edit()) {
                putStringSet("ids", ids)
                commit()
            }


          val i = Intent(this@Platillo, Menu::class.java)
            startActivity(i)

        }




    }
/*
    fun InsertarOrden(id:Int){
        var json = JSONObject()
        json.put("usuario", usuario)
        json.put("codigo", codigo)

        val uri = "http://192.168.1.102/getfood/registroCodigo"
        var queue = Volley.newRequestQueue(getActivity())
        val listener = Response.Listener<JSONObject> { response ->
            //Log.e("Mensaje", response.toString())
            if(response.getJSONObject("0").getString("1").equals("1"))
                Toast.makeText(getActivity(), "Codigo Registrado", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(getActivity(), "Inténtalo de más tarde", Toast.LENGTH_SHORT).show()
        }
        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)
    }

 */


}