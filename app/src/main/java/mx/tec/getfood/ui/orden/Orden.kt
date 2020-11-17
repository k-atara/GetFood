package mx.tec.getfood.ui.orden

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_orden.*
import mx.tec.getfood.MainActivity
import mx.tec.getfood.Menu
import mx.tec.getfood.R
import mx.tec.getfood.elemento.adapter.CustomAdapter
import mx.tec.getfood.elemento.adapter.RecyclerClickInterface
import mx.tec.getfood.elemento.model.Elemento
import org.json.JSONArray


class Orden : AppCompatActivity(), RecyclerClickInterface {
    var lista: RecyclerView? = null

    var platillos= ArrayList<Elemento>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orden)




        val sp = getSharedPreferences("orden", Context.MODE_PRIVATE)
        val ids = sp.getStringSet("ids", mutableSetOf())


        ids!!.forEach{
            id->
            Log.e("Ids:",id)
          cargarPlatillos(id)
        }

        val adaptador = CustomAdapter(this@Orden, R.layout.layout_elemento, platillos, R.anim.bounce, this
            )


        lista = findViewById(R.id.lvLista)
        lista?.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        lista?.setHasFixedSize(true)
        lista?.adapter = adaptador



        btnOrdenar.setOnClickListener {
         

            ids!!.forEach{
                id->
                guardarIds(id)
            }

            with(sp.edit()) {

                remove("ids")

                commit()
            }

            val i= Intent(this@Orden, Menu::class.java)
            startActivity(i)
        }
    }

    fun guardarIds(id:String){


    }
fun cargarPlatillos(id:String){

    var queue = Volley.newRequestQueue(this)


    val uri = "http://10.0.2.2/getfood/platillo/${id}"

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
            platillos.add(platillo)


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