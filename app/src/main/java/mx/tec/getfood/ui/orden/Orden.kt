package mx.tec.getfood.ui.orden

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_orden.*
import mx.tec.getfood.MainActivity
import mx.tec.getfood.Menu
import mx.tec.getfood.R
import mx.tec.getfood.elemento.adapter.CustomAdapter
import mx.tec.getfood.elemento.adapter.RecyclerClickInterface
import mx.tec.getfood.elemento.model.Elemento
import org.json.JSONArray
import org.json.JSONObject


class Orden : AppCompatActivity(), RecyclerClickInterface {
    var lista: RecyclerView? = null

    var suma: Int=0;

    var platillos= ArrayList<Elemento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden)


        var puntos=0;


        val spUsuario = getSharedPreferences("archivo", Context.MODE_PRIVATE)

        val sp = getSharedPreferences("orden", Context.MODE_PRIVATE)
        val ids = sp.getStringSet("ids", mutableSetOf())

        val user = spUsuario?.getString("Usuario", "-1").toString()
        var idPersona = 0
        //JALAR DATOOS DEL USUARIO   CON ESTE BLOQUE DE CODIGO SE HACE LA LLAMADA AL SERVICIO
        var json = JSONObject()
        json.put("usuario", user)
        val uri = "http://192.168.0.3/getfood/cuenta"
        var queue = Volley.newRequestQueue(this)
        val listener = Response.Listener<JSONObject> { response ->
            //Log.e("Mensaje", response.toString())
            //  txtNombre!!.text=
            idPersona = response.getJSONObject("0").getString("idPersona").toInt()
            puntos= response.getJSONObject("0").getString("puntos").toInt()

        }
        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)

        //FIN
        ids!!.forEachIndexed{index, id->
            //Log.e("Ids:",id)

            if (index == ids.size-1){
                cargarPlatillos(id, true)

            }
            else{
                cargarPlatillos(id,false)
            }
        }




        lista = findViewById(R.id.lvLista)
        lista?.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        lista?.setHasFixedSize(true)



        btnOrdenar.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Opción de pago")
                .setMessage("¿Con qué desea pagar?")

                .setPositiveButton("Puntos") { dialog, button ->

                    if(puntos<suma){
                        Toast.makeText(this,"No cuentas con puntos suficientes para pagar",Toast.LENGTH_LONG).show()
                    }else{
                        puntos -= suma;
                        //RESTAR punto en la BD;
                        var json = JSONObject()
                        json.put("idPersona", idPersona)
                        json.put("puntos",puntos)
                        val uri = "http://192.168.0.3/getfood/restarPuntos"
                        var queue = Volley.newRequestQueue(this)
                        val listener = Response.Listener<JSONObject> { response ->
                            //Log.e("MENSAJE COOL", "SI RESTE LOS PUNTOS")
                        }
                        val error = Response.ErrorListener { error ->
                            Log.e("Mensaje", error.message!!)
                        }

                        val request = JsonObjectRequest(Request.Method.PUT, uri, json, listener, error)
                        queue.add(request)

                        //Log.e("IDPERSONA", idPersona.toString())
                        //GENERARTURNO

                        val uri2 = "http://192.168.0.3/getfood/generarTurno"

                        var pedido = JSONObject()
                        var idsP :String = ""
                        ids.forEach{ id->
                            idsP+= "$id,"
                        }
                        pedido.put("especificaciones",txtEspecificaciones.text)
                        pedido.put("codigo", "")
                        pedido.put("idPersona", idPersona)
                        pedido.put("ids",idsP)


                        val listener2 = Response.Listener<JSONObject> { response ->
                            //Log.e("Mensaje", response.toString())



                            //Log.e("response", response.toString())
                            //Log.e("idTurno", response.getJSONObject("0").getString("idTurno"))

                            with(sp.edit()) {

                                remove("ids")
                                putInt("turno", response.getJSONObject("0").getString("idTurno").toInt())

                                commit()
                            }

                            val i= Intent(this@Orden, Menu::class.java)
                            startActivity(i)


                            //val adaptador = CustomAdapter(requireContext(), R.layout.layout_elemento, platillos, R.anim.bounce,this)
                            //adaptador.notifyDataSetChanged()

                        }

                        val error2 = Response.ErrorListener { error ->
                            Log.e("MensajeError", error.message!!)
                        }
                        val request2 = JsonObjectRequest(Request.Method.POST, uri2, pedido, listener2, error2)
                        queue.add(request2)



                    }


                }
                .setNegativeButton("Formarse") { dialog, button ->
                    var queue = Volley.newRequestQueue(this)


                    val uri = "http://192.168.0.3/getfood/generarTurno"

                    var pedido = JSONObject()
                    var idsP :String = ""
                    ids.forEach{ id->
                        idsP+= "$id,"
                    }
                    pedido.put("especificaciones",txtEspecificaciones.text)
                    pedido.put("codigo", "")
                    pedido.put("idPersona", idPersona)
                    pedido.put("ids",idsP)


                    val listener = Response.Listener<JSONObject> { response ->
                        //Log.e("Mensaje", response.toString())



                        //Log.e("response", response.toString())
                        //Log.e("idTurno", response.getJSONObject("0").getString("idTurno"))

                        with(sp.edit()) {

                            remove("ids")
                            putInt("turno", response.getJSONObject("0").getString("idTurno").toInt())

                            commit()
                        }

                        val i= Intent(this@Orden, Menu::class.java)
                        startActivity(i)


                        //val adaptador = CustomAdapter(requireContext(), R.layout.layout_elemento, platillos, R.anim.bounce,this)
                        //adaptador.notifyDataSetChanged()

                    }

                    val error = Response.ErrorListener { error ->
                        Log.e("MensajeError", error.message!!)
                    }
                    val request = JsonObjectRequest(Request.Method.POST, uri, pedido, listener, error)
                    queue.add(request)





                }.show()






        }
    }



fun cargarPlatillos(id:String, elementoFinal: Boolean = false){

    var queue = Volley.newRequestQueue(this)


    val uri = "http://192.168.0.3/getfood/platillo/${id}"

    val listener = Response.Listener<JSONArray> { response ->
        //Log.e("Mensaje", response.toString())

            var platillo = Elemento(
                id=response.getJSONObject(0).getInt("idPlatillo"),
                nombre = response.getJSONObject(0).getString("nombre"),
                descripcion = response.getJSONObject(0).getString("descripcion"),
                costo = "$"+response.getJSONObject(0).getString("costo"),
                puntos = response.getJSONObject(0).getString("puntos"),
                imagen = response.getJSONObject(0).getString("imagen")
            )
             //Log.e("platillo cargado", platillo.toString())
            platillos.add(platillo)

            suma+= platillo.costo.removeRange(0,1).toInt()
        if(elementoFinal){
                val adaptador = CustomAdapter(this@Orden, R.layout.layout_elemento, platillos, R.anim.bounce, this
                )
                lista?.adapter = adaptador


            }
        txtTotal.setText("Total: $ ${suma}")




        //val adaptador = CustomAdapter(requireContext(), R.layout.layout_elemento, platillos, R.anim.bounce,this)
        //adaptador.notifyDataSetChanged()

    }


    val error = Response.ErrorListener { error ->
        Log.e("Mensaje", error.message!!)
    }
    val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
    queue.add(request)
}

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onLongItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}