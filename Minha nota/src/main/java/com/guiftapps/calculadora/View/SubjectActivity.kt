package com.guiftapps.calculadora.View

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.Model.DisciplinaSQL
import com.guiftapps.calculadora.Controller.Disciplina
import com.guiftapps.calculadora.R

import java.util.ArrayList

class SubjectActivity : AppCompatActivity() {
    private var nomeMat: EditText? = null
    private var seg: CheckBox? = null
    private var ter: CheckBox? = null
    private var qua: CheckBox? = null
    private var qui: CheckBox? = null
    private var sex: CheckBox? = null
    private var sab: CheckBox? = null
    private var next: Button? = null
    private var ads: AdView? = null
    private var act: Switch? = null
    private var checkBoxList: MutableList<CheckBox>? = null
    private var id: Int = 0


    private var meualerta: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        nomeMat = findViewById(R.id.list_nomeMat)

        ads = findViewById(R.id.adView2)


        checkBoxList = ArrayList()

        seg = findViewById(R.id.CBSegunda)
        ter = findViewById(R.id.CBTerca)
        qua = findViewById(R.id.CBQuarta)
        qui = findViewById(R.id.CBQuinta)
        sex = findViewById(R.id.CBSexta)
        sab = findViewById(R.id.CBSabado)
        act = findViewById(R.id.switch_dias)


        if (act!!.isChecked) {
            seg!!.isEnabled = true
            ter!!.isEnabled = true
            qua!!.isEnabled = true
            qui!!.isEnabled = true
            sex!!.isEnabled = true
            sab!!.isEnabled = true
        } else {
            seg!!.isEnabled = false
            ter!!.isEnabled = false
            qua!!.isEnabled = false
            qui!!.isEnabled = false
            sex!!.isEnabled = false
            sab!!.isEnabled = false
        }
        act!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                seg!!.isEnabled = true
                ter!!.isEnabled = true
                qua!!.isEnabled = true
                qui!!.isEnabled = true
                sex!!.isEnabled = true
                sab!!.isEnabled = true
            } else {
                seg!!.isEnabled = false
                ter!!.isEnabled = false
                qua!!.isEnabled = false
                qui!!.isEnabled = false
                sex!!.isEnabled = false
                sab!!.isEnabled = false
                seg!!.isChecked = false
                ter!!.isChecked = false
                qua!!.isChecked = false
                qui!!.isChecked = false
                sex!!.isChecked = false
                sab!!.isChecked = false
            }
        }

        next = findViewById(R.id.bNext)
        val b = intent.extras

        if (b != null) {
            nomeMat!!.setText(b.getString("nome"))
            id = b.getInt("id")
            if (b.getString("semana") != null) {
                val semana = b.getString("semana")!!.split("\\ ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (i in semana.indices) {
                    if (seg!!.text == semana[i]) {
                        act!!.isChecked = true
                        seg!!.isChecked = true
                    }
                    if (ter!!.text == semana[i]) {
                        ter!!.isChecked = true
                        act!!.isChecked = true
                    }
                    if (qua!!.text == semana[i]) {
                        qua!!.isChecked = true
                        act!!.isChecked = true
                    }
                    if (qui!!.text == semana[i]) {
                        qui!!.isChecked = true
                        act!!.isChecked = true
                    }
                    if (sex!!.text == semana[i]) {
                        sex!!.isChecked = true
                        act!!.isChecked = true
                    }
                    if (sab!!.text == semana[i]) {
                        sab!!.isChecked = true
                        act!!.isChecked = true
                    }
                }
            } else {
                act!!.isChecked = false
            }
        }
        InitAdds()
    }

    fun InitAdds() {
        MobileAds.initialize(applicationContext, "ca-app-pub-2865932856120238~9749752037")
        val adView = AdView(applicationContext)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = getString(R.string.adsCode)
        ads = findViewById(R.id.adView2)
        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)
    }

    fun Adicionar(view: View) {
        if (id == 0) {
            if (nomeMat!!.text.toString() != "") {
                if (DisciplinaSQL(applicationContext).ifExists(nomeMat!!.text.toString())) {
                    if (act!!.isChecked) {
                        if (seg!!.isChecked || ter!!.isChecked || qua!!.isChecked ||
                                qui!!.isChecked || sex!!.isChecked || sab!!.isChecked) {
                            checkBoxList!!.clear()
                            checkBoxList!!.add(seg!!)
                            checkBoxList!!.add(ter!!)
                            checkBoxList!!.add(qua!!)
                            checkBoxList!!.add(qui!!)
                            checkBoxList!!.add(sex!!)
                            checkBoxList!!.add(sab!!)
                            val disciplina = Disciplina()
                            disciplina.disciplina = nomeMat!!.text.toString()

                            disciplina.diaSemana = ""
                            for (i in checkBoxList!!.indices) {
                                if (checkBoxList!![i].isChecked) {
                                    disciplina.diaSemana = disciplina.diaSemana + checkBoxList!![i].text.toString() + " "
                                }
                            }


                            if (DisciplinaSQL(applicationContext).Cadastrar(disciplina)) {
                                nomeMat!!.setText(null)
                                act!!.isChecked = false
                                Toast.makeText(applicationContext, "Disciplina adicionada com sucesso!", Toast.LENGTH_SHORT).show()

                            } else {
                                val alerta = AlertDialog.Builder(this)
                                var meualerta: AlertDialog? = null
                                alerta.setTitle("Erro ao Adicionar!")
                                alerta.setMessage("Ocorreu algum erro no cadastro dessa nova matéria!.")
                                meualerta = alerta.create()
                                meualerta!!.show()
                            }
                        } else {
                            val alerta = AlertDialog.Builder(this)
                            alerta.setTitle("Nenhum dia da semana?")
                            alerta.setMessage("Os dias da semana será usado para que você saiba em quais dias esta disciplina terá aula.")
                            alerta.setPositiveButton("Ok") { dialog, which -> meualerta!!.dismiss() }
                            alerta.setNegativeButton("Ignorar") { dialog, which ->
                                val disciplina = Disciplina()
                                disciplina.disciplina = nomeMat!!.text.toString()
                                if (DisciplinaSQL(applicationContext).Cadastrar(disciplina)) {
                                    nomeMat!!.setText(null)
                                    act!!.isChecked = false
                                    Toast.makeText(applicationContext, "Disciplina adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                                } else {
                                    val alerta = AlertDialog.Builder(applicationContext)
                                    var meualerta: AlertDialog?
                                    alerta.setTitle("Erro ao Adicionar!")
                                    alerta.setMessage("Ocorreu algum erro no cadastro dessa nova matéria!.")
                                    meualerta = alerta.create()
                                    meualerta!!.show()
                                }
                            }
                            meualerta = alerta.create()
                            meualerta!!.show()
                        }
                    } else {
                        val disciplina = Disciplina()
                        disciplina.disciplina = nomeMat!!.text.toString()
                        if (DisciplinaSQL(applicationContext).Cadastrar(disciplina)) {
                            nomeMat!!.setText(null)
                            act!!.isChecked = false
                            Toast.makeText(applicationContext, "Disciplina adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                        } else {
                            val alerta = AlertDialog.Builder(this)
                            var meualerta: AlertDialog?
                            alerta.setTitle("Erro ao Adicionar!")
                            alerta.setMessage("Ocorreu algum erro no cadastro dessa nova matéria!.")
                            meualerta = alerta.create()
                            meualerta!!.show()
                        }
                    }
                } else {
                    val alerta = AlertDialog.Builder(this)
                    alerta.setTitle("Disciplina já está cadastrada!")
                    alerta.setMessage("mude o nome da matéria ou adicione alguma diferenciação ao nome digitado!")
                    val meualerta = alerta.create()
                    meualerta.show()
                }
            } else {
                val alerta = AlertDialog.Builder(this)
                alerta.setTitle("Matéria sem nome!")
                alerta.setMessage("Insira o nome da matéria desejada como identificação!")
                val meualerta = alerta.create()
                meualerta.show()
            }
        } else {
            if (nomeMat!!.text.toString() != "") {
                if (act!!.isChecked) {
                    if (seg!!.isChecked || ter!!.isChecked || qua!!.isChecked ||
                            qui!!.isChecked || sex!!.isChecked || sab!!.isChecked) {
                        checkBoxList!!.clear()
                        checkBoxList!!.add(seg!!)
                        checkBoxList!!.add(ter!!)
                        checkBoxList!!.add(qua!!)
                        checkBoxList!!.add(qui!!)
                        checkBoxList!!.add(sex!!)
                        checkBoxList!!.add(sab!!)
                        val disciplina = Disciplina()
                        disciplina.disciplina = nomeMat!!.text.toString()

                        disciplina.diaSemana = ""
                        for (i in checkBoxList!!.indices) {
                            if (checkBoxList!![i].isChecked) {
                                disciplina.diaSemana = disciplina.diaSemana + checkBoxList!![i].text.toString() + " "
                            }
                        }
                        disciplina.id = id
                        if (DisciplinaSQL(applicationContext).Atualizar(disciplina)) {
                            setResult(Activity.RESULT_OK)
                            finish()
                            Toast.makeText(applicationContext, "Disciplina adicionada com sucesso!", Toast.LENGTH_SHORT).show()

                        } else {
                            val alerta = AlertDialog.Builder(this)
                            var meualerta: AlertDialog?
                            alerta.setTitle("Erro ao Adicionar!")
                            alerta.setMessage("Ocorreu algum erro no cadastro dessa nova matéria!.")
                            meualerta = alerta.create()
                            meualerta!!.show()
                        }
                    } else {
                        val alerta = AlertDialog.Builder(this)
                        alerta.setTitle("Nenhum dia da semana?")
                        alerta.setMessage("Os dias da semana será usado para que você saiba em quais dias esta disciplina terá aula.")
                        alerta.setPositiveButton("Ok") { dialog, which -> meualerta!!.dismiss() }
                        alerta.setNegativeButton("Ignorar") { dialog, which ->
                            val disciplina = Disciplina()
                            disciplina.id = id
                            disciplina.disciplina = nomeMat!!.text.toString()
                            if (DisciplinaSQL(applicationContext).Atualizar(disciplina)) {
                                setResult(Activity.RESULT_OK)
                                finish()
                                Toast.makeText(applicationContext, "Disciplina salva com sucesso!", Toast.LENGTH_SHORT).show()
                            } else {
                                val alerta = AlertDialog.Builder(applicationContext)
                                var meualerta: AlertDialog?
                                alerta.setTitle("Erro ao Adicionar!")
                                alerta.setMessage("Ocorreu algum erro no cadastro dessa nova matéria!.")
                                meualerta = alerta.create()
                                meualerta!!.show()
                            }
                        }
                        meualerta = alerta.create()
                        meualerta!!.show()
                    }
                } else {
                    val disciplina = Disciplina()
                    disciplina.id = id
                    disciplina.disciplina = nomeMat!!.text.toString()
                    if (DisciplinaSQL(applicationContext).Atualizar(disciplina)) {
                        setResult(Activity.RESULT_OK)
                        finish()
                        Toast.makeText(applicationContext, "Disciplina salva com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        val alerta = AlertDialog.Builder(this)
                        var meualerta: AlertDialog?
                        alerta.setTitle("Erro ao Adicionar!")
                        alerta.setMessage("Ocorreu algum erro no cadastro dessa nova matéria!.")
                        meualerta = alerta.create()
                        meualerta!!.show()
                    }
                }
            } else {
                val alerta = AlertDialog.Builder(this)
                alerta.setTitle("Matéria sem nome!")
                alerta.setMessage("Insira o nome da matéria desejada como identificação!")
                val meualerta = alerta.create()
                meualerta.show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MainFragment.MostrarMatérias()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
