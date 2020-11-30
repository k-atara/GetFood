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
import mx.tec.getfood.elemento.adapter.RecyclerClickInterface
import mx.tec.getfood.elemento.model.Elemento
import org.json.JSONArray

class PlatilloFragment : Fragment(), RecyclerClickInterface {

    //lateinit var adaptador: CustomAdapter
    var lista: RecyclerView? = null
    var platillos = ArrayList<Elemento>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_platillo, container, false)

        showData(v)

        return v
    }

    fun showData(v: View){
        var queue = Volley.newRequestQueue(getActivity())

        lista = v.findViewById(R.id.lvLista)


        val uri = "http://192.168.0.3/getfood/platofuerte"

        val listener = Response.Listener<JSONArray> { response ->
            //Log.e("Mensaje", response.toString())
            for (i in 0 until response.length()) {
                var platillo = Elemento(
                    id=response.getJSONObject(i).getInt("idPlatillo"),
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
            //val adaptador = CustomAdapter(requireContext(), R.layout.layout_elemento, platillos, R.anim.bounce,this)
            //adaptador.notifyDataSetChanged()
            lista?.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            lista?.setHasFixedSize(true)
            lista?.adapter = adaptador
        }

        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }
        val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
        queue.add(request)
    }

    override fun onItemClick(position: Int) {
        //Log.e("Mensaje", "Corre")
        //Log.e("Platillos", platillos[position].toString() )

        //Log.e("ID",platillos[position].id.toString())
        val i = Intent(context, Platillo::class.java)
        i.putExtra("id",platillos[position].id.toString())
        i.putExtra ("nombre", platillos[position].nombre)
        i.putExtra("descripcion",platillos[position].descripcion )
        i.putExtra("costo",platillos[position].costo )
        i.putExtra("puntos",platillos[position].puntos)
        i.putExtra("imagen",platillos[position].imagen )
        startActivity(i)

    }

    override fun onLongItemClick(position: Int) {
        //Log.e("Mensaje", "Corre")


    }
}