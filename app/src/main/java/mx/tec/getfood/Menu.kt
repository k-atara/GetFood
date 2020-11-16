package mx.tec.getfood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.platoFuerte, R.id.bebidas, R.id.postres, R.id.nav_home, R.id.nav_codigo, R.id.confirmar, R.id.nav_turno, R.id.nav_orden
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.btn_nav)
        //val navController1 = findNavController(R.id.buton_nav)
        bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.nav_home || destination.id == R.id.nav_codigoqr || destination.id == R.id.nav_turno || destination.id == R.id.nav_codigo || destination.id == R.id.nav_orden) {
                bottomNavigation.visibility = View.GONE
            }else {
                bottomNavigation.visibility = View.VISIBLE
            }
        }
        //        passDataCom()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout->{
                val sp = getSharedPreferences("archivo", Context.MODE_PRIVATE)

                with(sp.edit()) {
                    remove("Usuario")
                    remove("Password")
                    commit()
                }

                val i= Intent(this@Menu,MainActivity::class.java)
                startActivity(i)
                return true
            }
            else->  return super.onOptionsItemSelected(item)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
//     fun passDataCom(editTextImput: String) {
//        val bundle=Bundle()
//        bundle.putString("message",editTextImput)
//
//        val transaction=this.supportFragmentManager.beginTransaction()
//        val fragmentB= PlatilloFragment()
//        fragmentB.arguments= bundle
//       transaction.replace(R.id.frame_layout,fragmentB)
//        transaction.commit()
//    }
}