package mx.tec.getfood

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val b0=findViewById<Button>(R.id.btnIniciar)

        val sp = getSharedPreferences("archivo", Context.MODE_PRIVATE)

        val user = sp.getString("Usuario", "-1").toString()
        val password = sp.getString("Password", "-1").toString()

        Log.e("user", user)

        if(user=="-1" && password=="-1") {
            b0.setOnClickListener {
                val i = Intent(this@MainActivity, LogIn::class.java)
                i.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(i)
            }
                //val Usuario = edtUsuario.text.toString()
                //val Contra = edtPassword.text.toString()

                /*with(sp.edit()) {
                    putString("Usuario", Usuario)
                    putString("Password", Contra)
                    commit()
                }*/
        }else{
            b0.setOnClickListener {
                val i = Intent(this@MainActivity, Menu::class.java)
                i.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(i)
            }
        }



    }
}