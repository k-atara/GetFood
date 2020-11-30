package mx.tec.getfood.ui.codigo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_codigo.*
import kotlinx.android.synthetic.main.fragment_codigo.view.*
import mx.tec.getfood.Confirmar
import mx.tec.getfood.Menu
import mx.tec.getfood.R
import org.json.JSONObject


class GalleryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_codigo, container, false)
        val textView: TextView = root.findViewById(R.id.edt_codigo)
        /*galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        root.btn_codigo.setOnClickListener {
            val codigo = edt_codigo.text.toString()

            addCodigo(codigo)
            edt_codigo.setText("")
        }
        return root
    }

    fun addCodigo(codigo: String){

        val sp = getActivity()?.getSharedPreferences("archivo", Context.MODE_PRIVATE)
        val user = sp?.getString("Usuario", "-1").toString()

        var json = JSONObject()
        json.put("usuario", user)
        json.put("codigo", codigo)

        //Log.e("Mensaje de prueba", user + codigo)

        val uri = "http://192.168.0.3/getfood/registroCodigo"
        var queue = Volley.newRequestQueue(getActivity())
        val listener = Response.Listener<JSONObject> { response ->
            //Log.e("Mensaje", response.toString())
            if(response.getJSONObject("0").getString("resul").equals("0")) {
                //Toast.makeText(getActivity(), "Inténtalo de más tarde", Toast.LENGTH_SHORT).show()
                val i = Intent(getActivity(), Confirmar::class.java)
                i.putExtra("mensaje", "Inténtalo de más tarde")
                startActivity(i)
            }
            if(response.getJSONObject("0").getString("resul").equals("1")){
                val i = Intent(getActivity(), Confirmar::class.java)
                i.putExtra("mensaje", "Código registrado con éxito")
                startActivity(i)
            }

        }
        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)
    }
}