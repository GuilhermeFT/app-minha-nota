package com.guiftapps.calculadora.View

import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.Model.DisciplinaSQL
import com.guiftapps.calculadora.Model.NotasSQL
import com.guiftapps.calculadora.Controller.DialogManager
import com.guiftapps.calculadora.Controller.Notas
import com.guiftapps.calculadora.R
import kotlinx.android.synthetic.main.activity_bimestral.*
import java.text.DecimalFormat
import java.util.ArrayList

class BimestralActivity : AppCompatActivity() {
    private var pm: EditText? = null
    private var pb: EditText? = null
    private var p3: EditText? = null
    private var p4: EditText? = null
    private var ct: EditText? = null
    private var adc: Button? = null
    private var selectbim: Spinner? = null
    private var m1: TextView? = null
    private var m2: TextView? = null
    private var qtd: TextView? = null
    private var ads: AdView? = null
    private var alerta: AlertDialog? = null

    private var con = 0.0
    private var n1 = 0.0
    private var n2 = 0.0
    private var n3 = 0.0
    private var n4 = 0.0
    private var results: Double = 0.toDouble()
    internal var div = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bimestral)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        selectbim = findViewById(R.id.spinner_bim)
        adc = findViewById(R.id.selecMat)
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Cadastre suas disciplinas na outra seção!")
        builder.setTitle("Não há disciplinas")
        alerta = builder.create()

        conceitoN.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }

        })

        provabimestral.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }
        })
        provamensal.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }
        })

        ava3.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }
        })

        ava4.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }
        })
        ct = findViewById(R.id.conceitoN)
        pm = findViewById(R.id.provamensal)
        pb = findViewById(R.id.provabimestral)
        p3 = findViewById(R.id.ava3)
        p4 = findViewById(R.id.ava4)
        m1 = findViewById(R.id.media1)
        m2 = findViewById(R.id.media2)
        qtd = findViewById(R.id.qtdmedia)

        val b = intent.extras
        val arrays = arrayOf("1º Bimestre", "2º Bimestre", "3º Bimestre", "4º Bimestre")
        if (b != null) {
            adc!!.text = DisciplinaSQL(applicationContext).PegarNome(b.getInt("id_disc"))
            val arraysn = ArrayList<String>()
            val nota = Notas()
            nota.id_disciplina = DisciplinaSQL(applicationContext).PegarID(adc!!.text.toString())
            for (i in 0..3) {
                nota.bimestre = arrays[i]
                if (NotasSQL(applicationContext).Verificar(nota)) {
                    arraysn.add(arrays[i])
                }
            }
            val adapter = ArrayAdapter(applicationContext, R.layout.my_spinner, arraysn)
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            selectbim!!.adapter = adapter
        } else {
            adc!!.setOnClickListener {
                val dialogManager = DialogManager(adc!!, applicationContext, spinner_bim)
                if (dialogManager.isCheck) {
                    dialogManager.show(supportFragmentManager, null)

                } else {
                    alerta!!.show()
                }
            }
            val adapter = ArrayAdapter(applicationContext, R.layout.my_spinner, arrays)
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            selectbim!!.adapter = adapter
        }
        InitAdds()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun InitAdds() {
        MobileAds.initialize(applicationContext, "ca-app-pub-2865932856120238~9749752037")
        val adView = AdView(applicationContext)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = getString(R.string.adsCode)
        ads = findViewById(R.id.adView7)
        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)
    }

    fun Adicionar(view: View) {
        results = 0.0
        con = 0.0
        n1 = 0.0
        n2 = 0.0
        n3 = 0.0
        n4 = 0.0
        div = 0
        if (adc!!.text.toString() != "SELECIONAR MATÉRIA") {
            val nota = Notas()
            nota.id_disciplina = DisciplinaSQL(applicationContext).PegarID(adc!!.text.toString())
            nota.bimestre = selectbim!!.selectedItem.toString()
            if (NotasSQL(this).Verificar(nota)) {
                if (NotasSQL(applicationContext).Verificar(DisciplinaSQL(applicationContext).PegarID(adc!!.text.toString()))) {
                    var check = false
                    if (ct!!.text.toString() != "") {
                        con = java.lang.Double.parseDouble(ct!!.text.toString())
                    }

                    if (pm!!.text.toString() != "") {
                        n1 = java.lang.Double.parseDouble(pm!!.text.toString())
                        div += 1
                        check = true
                        if (pb!!.text.toString() != "") {
                            n2 = java.lang.Double.parseDouble(pb!!.text.toString())
                            div += 1
                            check = true
                            if (p3!!.text.toString() != "") {
                                n3 = java.lang.Double.parseDouble(p3!!.text.toString())
                                div += 1
                                check = true
                                if (p4!!.text.toString() != "") {
                                    n4 = java.lang.Double.parseDouble(p4!!.text.toString())
                                    div += 1
                                    check = true
                                }
                            }
                        }
                    }

                    if (n1 < 0 || n1 > 10 || n2 < 0 || n2 > 10 || n3 < 0 || n3 > 10 || n4 < 0 || n4 > 10 || con < 0 || con > 2) {
                        val alerta = AlertDialog.Builder(this)
                        var meualerta: AlertDialog?
                        alerta.setTitle("Número(s) inválido(s)!")
                        alerta.setMessage("Digite sua nota de 0 à 10 pontos ou  seu conceito de 0 à 2 pontos!")
                        meualerta = alerta.create()
                        meualerta!!.show()
                    } else {
                        if (check) {
                            val notas = Notas()
                            notas.bimestre = selectbim!!.selectedItem.toString()
                            notas.id_disciplina = DisciplinaSQL(applicationContext).PegarID(adc!!.text.toString())
                            results = (n1 + n2 + n3 + n4) / div * 0.8
                            results += con
                            when (selectbim!!.selectedItem.toString()) {
                                "1º Bimestre" -> {
                                    notas.nota1 = results
                                    notas.nota2 = (-1).toDouble()
                                    notas.nota3 = (-1).toDouble()
                                    notas.nota4 = (-1).toDouble()
                                }
                                "2º Bimestre" -> {
                                    notas.nota2 = results
                                    notas.nota1 = (-1).toDouble()
                                    notas.nota3 = (-1).toDouble()
                                    notas.nota4 = (-1).toDouble()
                                }
                                "3º Bimestre" -> {
                                    notas.nota3 = results
                                    notas.nota2 = (-1).toDouble()
                                    notas.nota1 = (-1).toDouble()
                                    notas.nota4 = (-1).toDouble()
                                }
                                "4º Bimestre" -> {
                                    notas.nota4 = results
                                    notas.nota2 = (-1).toDouble()
                                    notas.nota3 = (-1).toDouble()
                                    notas.nota1 = (-1).toDouble()
                                }
                            }

                            if (NotasSQL(applicationContext).Cadastrar(notas)) {
                                adc!!.text = "SELECIONAR MATÉRIA"
                                ct!!.setText(null)
                                pm!!.setText(null)
                                pb!!.setText(null)
                                p3!!.setText(null)
                                p4!!.setText(null)
                                m1!!.text = "0.0"
                                m2!!.text = "0.0"
                                m1!!.setTextColor(Color.parseColor("BLACK"))
                                m2!!.setTextColor(Color.parseColor("BLACK"))
                                qtd!!.text = "0"
                                val alerta = AlertDialog.Builder(this)
                                var meualerta: AlertDialog?
                                alerta.setTitle("Nota salva com sucesso")
                                alerta.setMessage("Verifique-a em seu histórico.")
                                meualerta = alerta.create()
                                meualerta!!.show()
                            }
                        } else {
                            val alerta = AlertDialog.Builder(this)
                            var meualerta: AlertDialog?
                            alerta.setTitle("Está faltando algo!")
                            alerta.setMessage("Preencha os campos de notas!")
                            meualerta = alerta.create()
                            meualerta!!.show()
                        }
                    }
                } else {
                    var check = false
                    if (ct!!.text.toString() != "") {
                        con = java.lang.Double.parseDouble(ct!!.text.toString())
                    }

                    if (pm!!.text.toString() != "") {
                        n1 = java.lang.Double.parseDouble(pm!!.text.toString())
                        div += 1
                        check = true
                        if (pb!!.text.toString() != "") {
                            n2 = java.lang.Double.parseDouble(pb!!.text.toString())
                            div += 1
                            check = true
                            if (p3!!.text.toString() != "") {
                                n3 = java.lang.Double.parseDouble(p3!!.text.toString())
                                div += 1
                                check = true
                                if (p4!!.text.toString() != "") {
                                    n4 = java.lang.Double.parseDouble(p4!!.text.toString())
                                    div += 1
                                    check = true
                                }
                            }
                        }
                    }

                    if (n1 < 0 || n1 > 10 || n2 < 0 || n2 > 10 || n3 < 0 || n3 > 10 || n4 < 0 || n4 > 10 || con < 0 || con > 2) {
                        val alerta = AlertDialog.Builder(this)
                        var meualerta: AlertDialog?
                        alerta.setTitle("Número(s) inválido(s)!")
                        alerta.setMessage("Digite sua nota de 0 à 10 pontos ou  seu conceito de 0 à 2 pontos!")
                        meualerta = alerta.create()
                        meualerta!!.show()
                    } else {
                        if (check) {
                            val notas = Notas()
                            notas.bimestre = selectbim!!.selectedItem.toString()
                            val id_disciplina = DisciplinaSQL(applicationContext).PegarID(adc!!.text.toString())
                            notas.id_disciplina = id_disciplina
                            notas.id = NotasSQL(applicationContext).PegarID(id_disciplina)
                            results = (n1 + n2 + n3 + n4) / div * 0.8
                            results += con
                            val cursor = NotasSQL(applicationContext).Selecionar()
                            while (cursor!!.moveToNext()) {
                                if (cursor.getInt(1) == id_disciplina) {
                                    when (selectbim!!.selectedItem.toString()) {
                                        "1º Bimestre" -> {
                                            notas.nota1 = results
                                            notas.nota2 = cursor.getDouble(4)
                                            notas.nota3 = cursor.getDouble(5)
                                            notas.nota4 = cursor.getDouble(6)
                                        }
                                        "2º Bimestre" -> {
                                            notas.nota2 = results
                                            notas.nota1 = cursor.getDouble(3)
                                            notas.nota3 = cursor.getDouble(5)
                                            notas.nota4 = cursor.getDouble(6)
                                        }
                                        "3º Bimestre" -> {
                                            notas.nota3 = results
                                            notas.nota2 = cursor.getDouble(4)
                                            notas.nota1 = cursor.getDouble(3)
                                            notas.nota4 = cursor.getDouble(6)
                                        }
                                        "4º Bimestre" -> {
                                            notas.nota4 = results
                                            notas.nota2 = cursor.getDouble(4)
                                            notas.nota3 = cursor.getDouble(5)
                                            notas.nota1 = cursor.getDouble(3)
                                        }
                                    }
                                    break
                                }
                            }

                            if (NotasSQL(applicationContext).Atualizar(notas)) {
                                adc!!.text = "SELECIONAR MATÉRIA"
                                ct!!.text = null
                                pm!!.text = null
                                pb!!.text = null
                                p3!!.text = null
                                p4!!.text = null
                                m1!!.text = "0.0"
                                m2!!.text = "0.0"
                                m1!!.setTextColor(Color.parseColor("BLACK"))
                                m2!!.setTextColor(Color.parseColor("BLACK"))
                                qtd!!.text = "0"
                                val alerta = AlertDialog.Builder(this)
                                var meualerta: AlertDialog?
                                alerta.setTitle("Nota salva com sucesso")
                                alerta.setMessage("Verifique-a em seu histórico.")
                                meualerta = alerta.create()
                                meualerta!!.show()
                            }
                        } else {
                            val alerta = AlertDialog.Builder(this)
                            var meualerta: AlertDialog?
                            alerta.setTitle("Está faltando algo!")
                            alerta.setMessage("Preencha os campos de notas!")
                            meualerta = alerta.create()
                            meualerta!!.show()
                        }
                    }
                }
            } else {
                val alerta = AlertDialog.Builder(this)
                var meualerta: AlertDialog?
                alerta.setTitle("Erro ao salvar nota!")
                alerta.setMessage("Há uma nota salva no mesmo bimestre e matéria! Para mudar, acesse o histórico referente e isto.")
                meualerta = alerta.create()
                meualerta!!.show()
            }
        } else {
            val alerta = AlertDialog.Builder(this)
            var meualerta: AlertDialog?
            alerta.setTitle("Está faltando algo!")
            alerta.setMessage("Selecione uma matéria no primeiro item!")
            meualerta = alerta.create()
            meualerta!!.show()
        }
    }

    fun Calcular() {
        results = 0.0
        con = 0.0
        n1 = 0.0
        n2 = 0.0
        n3 = 0.0
        n4 = 0.0
        div = 0
        var check = false
        if (ct!!.text.toString() != "") {
            con = java.lang.Double.parseDouble(ct!!.text.toString())
        }

        if (pm!!.text.toString() != "") {
            n1 = java.lang.Double.parseDouble(pm!!.text.toString())
            div += 1
            check = true
            if (pb!!.text.toString() != "") {
                n2 = java.lang.Double.parseDouble(pb!!.text.toString())
                div += 1
                check = true
                if (p3!!.text.toString() != "") {
                    n3 = java.lang.Double.parseDouble(p3!!.text.toString())
                    div += 1
                    check = true
                    if (p4!!.text.toString() != "") {
                        n4 = java.lang.Double.parseDouble(p4!!.text.toString())
                        div += 1
                        check = true
                    }
                }
            }
        }
        if (n1 < 0 || n1 > 10 || n2 < 0 || n2 > 10 || n3 < 0 || n3 > 10 || n4 < 0 || n4 > 10 || con < 0 || con > 2) {
            val alerta = AlertDialog.Builder(this)
            var meualerta: AlertDialog?
            alerta.setTitle("Número(s) inválido(s)!")
            alerta.setMessage("Digite sua nota de 0 à 10 pontos ou  seu conceito de 0 à 2 pontos!")
            meualerta = alerta.create()
            meualerta!!.show()
        } else {
            if (check) {
                val format = DecimalFormat("0.00")
                qtd!!.text = div.toString()
                results = (n1 + n2 + n3 + n4) / div * 0.8
                m1!!.text = format.format(results)
                if (results >= 6) {
                    m1!!.setTextColor(Color.parseColor("GREEN"))
                } else {

                }

                m2!!.text = format.format(results + con)
                if (results + con >= 6) {
                    m2!!.setTextColor(Color.parseColor("GREEN"))
                } else {
                    m2!!.setTextColor(Color.parseColor("RED"))
                }
            }
        }
    }
}
