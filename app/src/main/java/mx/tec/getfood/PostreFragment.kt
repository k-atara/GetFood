package mx.tec.getfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class PostreFragment : Fragment(), RecyclerClickInterface {

    var lista: RecyclerView? = null
    var postres = ArrayList<Elemento>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_postre, container, false)

        showData(v)

        return v
    }
    fun showData(v: View){
        var queue = Volley.newRequestQueue(getActivity())

        lista = v.findViewById(R.id.lvLista)


        val uri = "http://192.168.1.102/getfood/postre"

        val listener = Response.Listener<JSONArray> { response ->
            //Log.e("Mensaje", response.toString())
            for (i in 0 until response.length()) {
                var postre = Elemento(
                    id=response.getJSONObject(i).getInt("idPlatillo"),
                    nombre = response.getJSONObject(i).getString("nombre"),
                    descripcion = response.getJSONObject(i).getString("descripcion"),
                    puntos = response.getJSONObject(i).getString("puntos"),
                    costo = "$"+response.getJSONObject(i).getString("costo"),
                    imagen = response.getJSONObject(i).getString("imagen")
                )
                postres.add(postre)
            }
            val adaptador = getActivity()?.let {
                CustomAdapter(
                    it,
                    R.layout.layout_elemento, postres, R.anim.bounce, this
                )
            }
            //adaptador.notifyDataSetChanged()
            lista!!.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            lista!!.setHasFixedSize(true)
            lista!!.adapter = adaptador
        }

        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }
        val request = JsonArrayRequest(Request.Method.GET, uri, null, listener, error)
        queue.add(request)
    }

    override fun onItemClick(position: Int) {
        //Log.e("Mensaje", "Corre")

        val i= Intent(context, Platillo::class.java )
        i.putExtra("id",postres[position].id.toString())
        i.putExtra("nombre",postres[position].nombre )
        i.putExtra("descripcion",postres[position].descripcion )
        i.putExtra("costo",postres[position].costo )
        i.putExtra("imagen",postres[position].imagen )
        startActivity(i)
    }

    override fun onLongItemClick(position: Int) {
        //Log.e("Mensaje", "Corre")
    }
}