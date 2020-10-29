package mx.tec.getfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mx.tec.getfood.elemento.adapter.CustomAdapter
import mx.tec.getfood.elemento.model.Elemento

class PlatilloFragment : Fragment() {

    lateinit var adaptador: CustomAdapter
    var rvLista: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_platillo, container, false)
        val lista: RecyclerView = v.findViewById(R.id.lvLista)

        val datos = listOf(
            Elemento(1, "Hamburguesa", "Se puede vegana", "$31", R.drawable.hamburguesa),
            Elemento(2, "Ensalada", "Solo ensalada", "$24", R.drawable.ensalada),
            Elemento(3, "Burrito", "Al pastor o con suadero", "$25", R.drawable.burrito),
            Elemento(3, "Sandwich", "de jamon o con salchicas", "$24", R.drawable.sandwich),
            Elemento(4, "Pizza", "Peperoni o Champiñon", "$25", R.drawable.pizza)
        )

        val adaptador = getActivity()?.let {
            CustomAdapter(
                it,
                R.layout.layout_elemento, datos, R.anim.bounce
            )
        }

        lista.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        lista.setHasFixedSize(true)
        lista.adapter=adaptador

        return v
    }
}