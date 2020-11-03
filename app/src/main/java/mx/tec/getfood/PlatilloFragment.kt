package mx.tec.getfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.tec.getfood.elemento.adapter.CustomAdapter
import mx.tec.getfood.elemento.model.Elemento
import org.json.JSONArray

class PlatilloFragment : Fragment(), CustomAdapter.OnElementoClickListener {

    //lateinit var adaptador: CustomAdapter
    var lista: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_platillo, container, false)

        /*val lista: RecyclerView = v.findViewById(R.id.lvLista)

        val datos = listOf(
            Elemento(1, "Hamburguesa", "Se puede vegana", "$31","20",  R.drawable.hamburguesa),
            Elemento(2, "Ensalada", "Solo ensalada", "$24","20",  R.drawable.ensalada),
            Elemento(3, "Burrito", "Al pastor o con suadero", "$25","20",  R.drawable.burrito),
            Elemento(3, "Sandwich", "de jamon o con salchicas", "$24","20",  R.drawable.sandwich),
            Elemento(4, "Pizza", "Peperoni o Champiñon", "$25","20",  R.drawable.pizza)
        )

        val adaptador = getActivity()?.let {
            CustomAdapter(
                it,
                R.layout.layout_elemento, datos, R.anim.bounce
            )
        }

        lista.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        lista.setHasFixedSize(true)
        lista.adapter=adaptador**/

        showData(v)

        return v
    }

    fun showData(v: View){
        var queue = Volley.newRequestQueue(getActivity())

        lista = v.findViewById(R.id.lvLista)
        var platillos = ArrayList<Elemento>()

        val uri = "http://10.0.2.2/getfood/platofuerte"

        val listener = Response.Listener<JSONArray> { response ->
            //Log.e("Mensaje", response.toString())
            for (i in 0 until response.length()) {
                var platillo = Elemento(
                    id = response.getJSONObject(i).getInt("idPlatillo"),
                    nombre = response.getJSONObject(i).getString("nombre"),
                    descripcion = response.getJSONObject(i).getString("descripcion"),
                    costo = "$"+response.getJSONObject(i).getString("costo"),
                    puntos = response.getJSONObject(i).getString("puntos"),
                    imagen = response.getJSONObject(i).getString("imagen")
                )
                platillos.add(platillo)
            }
            val adaptador = getActivity()?.let {
                CustomAdapter(
                    it,
                    R.layout.layout_elemento, platillos, R.anim.bounce, this
                )
            }
            //adaptador.notifyDataSetChanged()
            lista?.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            lista?.setHasFixedSize(true)
            lista?.adapter = adaptador
        }

        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }
        val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
        queue.add(request)
    }

    override fun onItemClick(item: Elemento, position: Int) {
        //Toast.makeText(context, item.nombre, Toast.LENGTH_SHORT).show()
        Log.e("Mensaje", item.nombre)
    }
}