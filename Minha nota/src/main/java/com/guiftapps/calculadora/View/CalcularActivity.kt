package com.guiftapps.calculadora.View

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.Model.MateriaSQL
import com.guiftapps.calculadora.Controller.Materia
import com.guiftapps.calculadora.R

import java.text.DecimalFormat

class CalcularActivity : AppCompatActivity() {

    private var NomeMat: EditText? = null
    private var bimestre1: EditText? = null
    private var bimestre2: EditText? = null
    private var bimestre3: EditText? = null
    private var bimestre4: TextView? = null
    private var status: TextView? = null
    private var ads: AdView? = null
    private var id: Int = 0
    private var isEdit: Boolean = false


    internal var bim1 = 0.0
    internal var bim2 = 0.0
    internal var bim3 = 0.0
    internal var results = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular)

        NomeMat = findViewById(R.id.NomeMateria)
        bimestre1 = findViewById(R.id.bim1)
        bimestre2 = findViewById(R.id.bim2)
        bimestre3 = findViewById(R.id.bim3)
        bimestre4 = findViewById(R.id.bim4)
        status = findViewById(R.id.status)
        val t = intent
        val b = t.extras

        if (b != null) {
            Log.d("oi", "oi")
            isEdit = true
            NomeMat!!.setText(b.get("nome")!!.toString())
            bimestre1!!.setText(b.get("b1")!!.toString())
            bimestre2!!.setText(b.get("b2")!!.toString())
            bimestre3!!.setText(b.get("b3")!!.toString())
            id = MateriaSQL(applicationContext).PegarID(b.get("nome")!!.toString())
        } else {
            isEdit = false
        }

        bimestre1!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }
        })
        bimestre2!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }
        })

        bimestre3!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Calcular()
            }
        })


        InitAdds()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
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
        ads = findViewById(R.id.adView5)
        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)
    }

    fun Calcular() {
        val format = DecimalFormat("0.00")

        bim1 = if (bimestre1!!.text.toString() != "") {
            java.lang.Double.parseDouble(bimestre1!!.text.toString())
        } else {
            0.0
        }

        bim2 = if (bimestre2!!.text.toString() != "") {
            java.lang.Double.parseDouble(bimestre2!!.text.toString())
        } else {
            0.0
        }
        bim3 = if (bimestre3!!.text.toString() != "") {
            java.lang.Double.parseDouble(bimestre3!!.text.toString())
        } else {
            0.0
        }

        if (bim1 < 0 || bim1 > 10 || bim2 < 0 || bim2 > 10 || bim3 < 0 || bim3 > 10) {
            val alerta = AlertDialog.Builder(this)
            alerta.setTitle("Número Inválido!")
            alerta.setMessage("Verifique os números digitados novamente.")
            val meualerta = alerta.create()
            meualerta.show()

        } else {
            results = (60 - (bim1 * 2 + bim2 * 2 + bim3 * 3)) / 3
            if (results <= 0) {

                bimestre4!!.text = format.format(results * -1).toString()
                status!!.text = "Parabéns, você passou!\nSobrou na média final: "
                bimestre4!!.setTextColor(Color.parseColor("GREEN"))
            } else {
                if (results > 10) {
                    bimestre4!!.text = format.format(results).toString()
                    status!!.text = "Reprovado!"
                    bimestre4!!.setTextColor(Color.parseColor("RED"))
                } else {
                    bimestre4!!.text = format.format(results).toString()
                    status!!.text = "Ainda faltam: "
                    bimestre4!!.setTextColor(Color.parseColor("RED"))
                }
            }
        }
    }

    fun Salvar(view: View) {
        if (!isEdit) {
            if (NomeMat!!.text.toString() != "") {
                if (bimestre1!!.text.toString() != "") {
                    bim1 = java.lang.Double.parseDouble(bimestre1!!.text.toString())
                } else {
                    bimestre1!!.setText("0")
                    bim1 = 0.0
                }
                if (bimestre2!!.text.toString() != "") {
                    bim2 = java.lang.Double.parseDouble(bimestre2!!.text.toString())
                } else {
                    bimestre2!!.setText("0")
                    bim2 = 0.0
                }
                if (bimestre3!!.text.toString() != "") {
                    bim3 = java.lang.Double.parseDouble(bimestre3!!.text.toString())
                } else {
                    bimestre3!!.setText("0")
                    bim3 = 0.0
                }
                if (NomeMat!!.text.toString() == "") {
                    val bd = MateriaSQL(applicationContext)
                    NomeMat!!.setText("Matéria sem nome (" + bd.GetId() + ")")
                }
                if (bim1 < 0 || bim1 > 10 || bim2 < 0 || bim2 > 10 || bim3 < 0 || bim3 > 10) {
                    val alerta = AlertDialog.Builder(this)
                    alerta.setTitle("Número Inválido!")
                    alerta.setMessage("Verifique os números digitados novamente.")
                    val meualerta = alerta.create()
                    meualerta.show()

                } else {
                    val mat = Materia()
                    val bd = MateriaSQL(applicationContext)
                    results = (60 - (bim1 * 2 + bim2 * 2 + bim3 * 3)) / 3
                    mat.nomeMateria = NomeMat!!.text.toString()
                    if (results <= 0) {
                        mat.status = "Aprovado"
                        mat.setnFinal(results * -1)
                    } else if (results > 10) {
                        mat.status = "Reprovado!"
                        mat.setnFinal(results)
                    } else {
                        mat.status = "Faltam pontos"
                        mat.setnFinal(results)
                    }
                    mat.bim1 = bim1
                    mat.bim2 = bim2
                    mat.bim3 = bim3
                    Log.d("d",NomeMat!!.text.toString())
                    if (bd.Selecionar(NomeMat!!.text.toString())) {
                        if (bd.Cadastrar(mat)) {
                            val alerta = AlertDialog.Builder(this)
                            alerta.setTitle("Sucesso")
                            alerta.setMessage("Sua nota foi cadastrada com Sucesso!")
                            val meualerta = alerta.create()
                            meualerta.show()
                            NomeMat!!.setText(null)
                            bimestre1!!.setText(null)
                            bimestre2!!.setText(null)
                            bimestre3!!.setText(null)
                            bimestre4!!.text = "0"
                            bim1 = 0.0
                            bim2 = 0.0
                            bim3 = 0.0
                            bimestre4!!.setTextColor(Color.parseColor("BLACK"))
                            status!!.text = "Sua nota"
                        } else {
                            val alerta = AlertDialog.Builder(this)
                            alerta.setTitle("Erro!")
                            alerta.setMessage("Sua nota não foi cadastrada com Sucesso!")
                            val meualerta = alerta.create()
                            meualerta.show()
                        }
                    } else {
                        val alerta = AlertDialog.Builder(this)
                        alerta.setTitle("Matéria já existe!")
                        alerta.setMessage("Insira outro nome para a matéria!")
                        val meualerta = alerta.create()
                        meualerta.show()
                    }
                }
            } else {
                val alerta = AlertDialog.Builder(this)
                alerta.setTitle("Sem nome")
                alerta.setMessage("Insira o nome da matéria!")
                val meualerta = alerta.create()
                meualerta.show()
            }
        } else {
            if (NomeMat!!.text.toString() != "") {
                if (bimestre1!!.text.toString() != "") {
                    bim1 = java.lang.Double.parseDouble(bimestre1!!.text.toString())
                } else {
                    bimestre1!!.setText("0")
                    bim1 = 0.0
                }
                if (bimestre2!!.text.toString() != "") {
                    bim2 = java.lang.Double.parseDouble(bimestre2!!.text.toString())
                } else {
                    bimestre2!!.setText("0")
                    bim2 = 0.0
                }
                if (bimestre3!!.text.toString() != "") {
                    bim3 = java.lang.Double.parseDouble(bimestre3!!.text.toString())
                } else {
                    bimestre3!!.setText("0")
                    bim3 = 0.0
                }
                if (NomeMat!!.text.toString() == "") {
                    val bd = MateriaSQL(applicationContext)
                    NomeMat!!.setText("Matéria sem nome (" + bd.GetId() + ")")
                }
                if (bim1 < 0 || bim1 > 10 || bim2 < 0 || bim2 > 10 || bim3 < 0 || bim3 > 10) {
                    val alerta = AlertDialog.Builder(this)
                    alerta.setTitle("Número Inválido!")
                    alerta.setMessage("Verifique os números digitados novamente.")
                    val meualerta = alerta.create()
                    meualerta.show()

                } else {
                    val mat = Materia()
                    val bd = MateriaSQL(applicationContext)
                    results = (60 - (bim1 * 2 + bim2 * 2 + bim3 * 3)) / 3
                    mat.nomeMateria = NomeMat!!.text.toString()
                    if (results <= 0) {
                        mat.status = "Aprovado"
                        mat.setnFinal(results * -1)
                    } else if (results > 10) {
                        mat.status = "Reprovado!"
                        mat.setnFinal(results)
                    } else {
                        mat.status = "Faltam pontos"
                        mat.setnFinal(results)
                    }
                    mat.bim1 = bim1
                    mat.bim2 = bim2
                    mat.bim3 = bim3
                    mat.id = id

                    if (bd.Atualizar(mat)) {
                        setResult(Activity.RESULT_OK)
                        finish()
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("Edit", true)
                        startActivity(intent)
                    } else {
                        val alerta = AlertDialog.Builder(this)
                        alerta.setTitle("Erro!")
                        alerta.setMessage("Sua nota não foi cadastrada com Sucesso!")
                        val meualerta = alerta.create()
                        meualerta.show()
                        NomeMat!!.setText(null)
                        bimestre1!!.setText(null)
                        bimestre2!!.setText(null)
                        bimestre3!!.setText(null)
                        bimestre4!!.text = "0"
                        bim1 = 0.0
                        bim2 = 0.0
                        bim3 = 0.0
                        bimestre4!!.setTextColor(Color.parseColor("BLACK"))
                        status!!.text = "Sua nota"
                    }
                }
            } else {
                val alerta = AlertDialog.Builder(this)
                alerta.setTitle("Sem nome")
                alerta.setMessage("Insira o nome da matéria!")
                val meualerta = alerta.create()
                meualerta.show()
            }
        }
    }
}