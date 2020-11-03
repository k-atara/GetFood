package mx.tec.getfood.elemento.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Base64.decode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import mx.tec.getfood.Platillo
import mx.tec.getfood.R
import mx.tec.getfood.Registro
import mx.tec.getfood.elemento.model.Elemento
import java.io.File
import java.util.Base64.getDecoder


class CustomAdapter(private val context: Context, private val layout: Int, private val dataSource: List<Elemento>, private val animation: Int, private val listener: OnElementoClickListener) : RecyclerView.Adapter<CustomAdapter.ElementoViewHolder>() {

    class ElementoViewHolder(inflater: View, parent: ViewGroup, layout: Int, var context: Context)
        : RecyclerView.ViewHolder(inflater){
        var imagen: ImageView? = null
        var nombre: TextView? = null
        var descripcion: TextView? = null
        var costo: TextView? = null
        var cardView: CardView? = null

        init{
            imagen = itemView.findViewById(R.id.imgImagen)
            nombre = itemView.findViewById(R.id.txtNombre)
            descripcion = itemView.findViewById(R.id.txtDescripcion)
            costo = itemView.findViewById(R.id.txtCosto)
        }

        fun bindData(item:Elemento, action: OnElementoClickListener){
            val img = item.imagen
            val imageBytes = Base64.decode(img,Base64.DEFAULT)
            val decodedBitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            imagen!!.setImageBitmap(decodedBitmap)
            nombre!!.text = item.nombre
            descripcion!!.text = item.descripcion
            costo!!.text = item.costo
            cardView?.setOnClickListener {
                Log.e("Mensaje", item.nombre)
                action.onItemClick(item, adapterPosition)
            }
        }

    }

    interface OnElementoClickListener{
        fun onItemClick(item: Elemento, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementoViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ElementoViewHolder(inflater, parent, layout, context)
    }

    override fun onBindViewHolder(holder: ElementoViewHolder, position: Int) {
        val elemento = dataSource[position]
        val animation = AnimationUtils.loadAnimation(context, animation)
        holder.itemView.startAnimation(animation)
        holder.cardView?.setOnClickListener{
            Log.e("Mensaje", "Hola")
        }
        holder.bindData(elemento, listener)
    }

    override fun getItemCount(): Int { return dataSource.size }
}