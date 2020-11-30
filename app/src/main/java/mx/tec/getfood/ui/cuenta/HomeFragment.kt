package mx.tec.getfood.ui.cuenta

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_home.*
import mx.tec.getfood.R
import org.json.JSONObject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val mContext: Context? = null
    var txtNombre: TextView? = null
    var txtCorreo: TextView? = null
    var txtUsuario: TextView? = null
    var txtPuntos: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val v = inflater.inflate(R.layout.fragment_home, container, false)

        val sp = getActivity()?.getSharedPreferences("archivo", Context.MODE_PRIVATE)
        val user = sp?.getString("Usuario", "-1").toString()

        showData(v, user)

        return v
    }
    fun showData(v: View, user: String) {
        var json = JSONObject()
        json.put("usuario", user)

        txtUsuario=v.findViewById(R.id.txtUsuario)
        txtNombre=v.findViewById(R.id.txtNombre)
        txtCorreo=v.findViewById(R.id.txtCorreo)
        txtPuntos=v.findViewById(R.id.txtPuntos)
        txtUsuario!!.text=user

        val uri = "http://10.0.0.12/getfood/cuenta"
        var queue = Volley.newRequestQueue(v.context)
        val listener = Response.Listener<JSONObject> { response ->
            //Log.e("Mensaje", response.toString())
            txtNombre!!.text=response.getJSONObject("0").getString("nombre")
            txtCorreo!!.text=response.getJSONObject("0").getString("correo")
            txtPuntos!!.text=response.getJSONObject("0").getString("puntos")
        }
        val error = Response.ErrorListener { error ->
            Log.e("Mensaje", error.message!!)
        }

        val request = JsonObjectRequest(Request.Method.POST, uri, json, listener, error)
        queue.add(request)
    }
}