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


class PostreFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_postre, container, false)
        val lista: RecyclerView = v.findViewById(R.id.lvLista)

        val datos = listOf(
            Elemento(1, "Helado", "Fresa, vainilla o cholate", "$15", R.drawable.helado),
            Elemento(2, "Pastel", "tres leches o chocolate", "$20", R.drawable.pastel),
            Elemento(3, "Pay de queso", "Solo hay de queso", "$30", R.drawable.pay),
            Elemento(3, "Gelatina", "uva, fresa o naranja", "$24", R.drawable.gelatina),
            Elemento(4, "Cupcakes", "vainalla o chocolate", "$15", R.drawable.cupkake)
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