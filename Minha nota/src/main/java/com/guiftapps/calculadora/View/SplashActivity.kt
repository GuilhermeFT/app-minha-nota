package com.guiftapps.calculadora.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.guiftapps.calculadora.Model.DisciplinaSQL
import com.guiftapps.calculadora.Model.MateriaSQL
import com.guiftapps.calculadora.Model.NotasSQL
import com.guiftapps.calculadora.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            var m = MainActivity()
            if (!(NotasSQL(applicationContext).CriarTabela() && MateriaSQL(applicationContext).CriarTabela() &&
                            DisciplinaSQL(applicationContext).CriarTabela())) {
                Toast.makeText(applicationContext, "Erro ao criar banco de dados", Toast.LENGTH_LONG).show()
            }
            startActivity(Intent(applicationContext, m::class.java))
            finish()
        }, 2500)
    }
}
