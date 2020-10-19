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


class BebidaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_bebida, container, false)
        val lista: RecyclerView = v.findViewById(R.id.lvLista)

        val datos = listOf(
            Elemento(1, "Refresco", "Coca, sidralo naranja", "$15", R.drawable.soda),
            Elemento(2, "Jugo", "Manzana, naranja o mango", "$17", R.drawable.jugo),
            Elemento(3, "Limonada", "Rosa o normal", "$23", R.drawable.limonada),
            Elemento(3, "Moca", "Se puede descafeinada", "$24", R.drawable.moca),
            Elemento(4, "Cerveza de Raiz", "No vendemos de verdad", "$30", R.drawable.raiz)
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