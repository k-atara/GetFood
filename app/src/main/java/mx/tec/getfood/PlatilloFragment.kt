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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_platillo, container, false)
        val lista: RecyclerView = v.findViewById(R.id.lvLista)

        val datos = listOf(
            Elemento(1, "Element 1", "Descripcion 1", "$1", R.drawable.hamburguesa),
            Elemento(2, "Element 2", "Descripcion 2", "$2", R.drawable.ensalada),
            Elemento(3, "Element 3", "Descripcion 3", "$3", R.drawable.burrito),
            Elemento(3, "Element 4", "Descripcion 4", "$4", R.drawable.sandwich),
            Elemento(4, "Element 5", "Descripcion 5", "$5", R.drawable.pizza)
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