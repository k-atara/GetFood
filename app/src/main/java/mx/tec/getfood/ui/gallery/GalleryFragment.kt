package mx.tec.getfood.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_codigo.view.*
import mx.tec.getfood.LogIn
import mx.tec.getfood.Menu
import mx.tec.getfood.R

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_codigo, container, false)
        val textView: TextView = root.findViewById(R.id.edt_codigo)
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        root.btn_codigo.setOnClickListener {

        }
        return root
    }
}