package com.guiftapps.calculadora.View


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout

import com.guiftapps.calculadora.Model.BroadCastReceiverClass
import com.guiftapps.calculadora.R

import java.util.Calendar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, PFFragment.OnFragmentInteractionListener, HistoricoFragment.OnFragmentInteractionListener, DisciplinasListFragment.OnFragmentInteractionListener, NotasBimestraisFragment.OnFragmentInteractionListener, MainFragment.OnFragmentInteractionListener {

    private var fab: FloatingActionMenu? = null
    private var toolbar: Toolbar? = null
    private var menuDisciplina: FloatingActionButton? = null
    private var menuBimestral: FloatingActionButton? = null
    private var listfragment: HistoricoFragment? = null
    private var pffragment: PFFragment? = null
    private var floatingActionMenu: FloatingActionMenu? = null
    private var disciplinasfragment: DisciplinasListFragment? = null
    private var notasBimestraisFragment: NotasBimestraisFragment? = null
    private var mainFragment: MainFragment? = null
    private var layout: LinearLayout? = null
    private var id: Int = 0
    private lateinit var navigationView: NavigationView
    private var alarmMgr: AlarmManager? = null
    private var alarmIntent: PendingIntent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mainFragment = MainFragment.newInstance()

        listfragment = HistoricoFragment.newInstance()
        notasBimestraisFragment = NotasBimestraisFragment.newInstance()
        pffragment = PFFragment.newInstance()
        disciplinasfragment = DisciplinasListFragment.newInstance()
        fab = findViewById(R.id.fab)

        fab!!.setClosedOnTouchOutside(true)
        menuDisciplina = findViewById(R.id.fab_disciplina)
        menuBimestral = findViewById(R.id.fab_bimestral)
        setFragment(mainFragment!!)
        floatingActionMenu = findViewById(R.id.fab)
        menuBimestral!!.setOnClickListener {
            fab!!.close(true)
            startActivity(Intent(applicationContext, BimestralActivity::class.java))
        }
        menuDisciplina!!.setOnClickListener {
            fab!!.close(true)
            startActivity(Intent(applicationContext, SubjectActivity::class.java))
        }

        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val headerView = navigationView.getHeaderView(0)
        layout = headerView.findViewById(R.id.layoutv)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = object : ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                if (fab!!.isOpened) {
                    fab!!.close(true)
                }
            }
        }
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, BroadCastReceiverClass::class.java)
        alarmIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        mainFragment!!.setMain(this)
        // Set the alarm to start at 8:30 a.m.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 18)
        calendar.set(Calendar.MINUTE, 0)

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes.
        alarmMgr!!.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, alarmIntent)
    }

    fun openDrawer() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else if (fab!!.isOpened) {
            fab!!.close(true)
        } else {
            val current = supportFragmentManager.findFragmentById(R.id.frame)
            if (current is MainFragment) {
                super.onBackPressed()
            } else {
                setFragment(mainFragment!!)
                navigationView.setCheckedItem(R.id.Null)
                fab!!.visibility = View.VISIBLE
                supportActionBar!!.title = "Minha Nota"
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_sobre) {
            fab!!.close(true)
            val intent = Intent(applicationContext, InformacaoActivity::class.java)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        id = item.itemId

        when (id) {
            R.id.nav_lista -> {
                val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.closeDrawer(GravityCompat.START)
                fab!!.visibility = View.INVISIBLE
                supportActionBar!!.title = "Lista de notas"
                setFragment(listfragment!!)
                return true

            }
            R.id.nav_pf -> {
                val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.closeDrawer(GravityCompat.START)
                fab!!.visibility = View.INVISIBLE
                supportActionBar!!.title = "Prova Final"
                setFragment(pffragment!!)
                return true
            }
            R.id.nav_qac -> {
                val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.closeDrawer(GravityCompat.START)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://academico.ifmt.edu.br/qacademico/index.asp?t=1001"))
                startActivity(browserIntent)
                return false
            }
            R.id.nav_disciplinas -> {
                val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.closeDrawer(GravityCompat.START)
                fab!!.visibility = View.INVISIBLE
                supportActionBar!!.title = "Lista de disciplinas"
                setFragment(disciplinasfragment!!)

                return true
            }
            R.id.nav_bimestrais -> {
                val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.closeDrawer(GravityCompat.START)
                fab!!.visibility = View.INVISIBLE
                supportActionBar!!.title = "Notas bimestrais"
                setFragment(notasBimestraisFragment!!)
                return true
            }
            R.id.nav_pdp -> {
                val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
                drawer.closeDrawer(GravityCompat.START)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://guilhermeft.wixsite.com/minhanota/politica-de-privacidade"))
                startActivity(browserIntent)
                return false
            }
            R.id.nav_Nfinal -> startActivity(Intent(applicationContext, CalcularActivity::class.java))
            R.id.nav_sobre -> startActivity(Intent(applicationContext, InformacaoActivity::class.java))
        }
        return false
    }

    fun setFragment(fragment: Fragment) {
        if (!fragment.isVisible) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.commit()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }
}
