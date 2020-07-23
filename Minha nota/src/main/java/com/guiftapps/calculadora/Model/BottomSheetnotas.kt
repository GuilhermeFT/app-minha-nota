package com.guiftapps.calculadora.Model

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.guiftapps.calculadora.Controller.Notas
import com.guiftapps.calculadora.R
import com.guiftapps.calculadora.View.NotasBimestraisFragment

import java.text.DecimalFormat
import java.util.ArrayList

@SuppressLint("ValidFragment")
class BottomSheetnotas @SuppressLint("ValidFragment")
constructor(private var lista: ArrayList<Notas>?, private val position: Int, private val notasBimestraisFragment: NotasBimestraisFragment) : BottomSheetDialogFragment() {
    private var listNotas: ListView? = null
    private var listAdapterbimestres: ListAdapterbimestres? = null
    private var n1: Double = 0.toDouble()
    private var n2: Double = 0.toDouble()
    private var n3: Double = 0.toDouble()
    private var n4: Double = 0.toDouble()
    private var anual: TextView? = null
    private var msg: TextView? = null
    private var v: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.bottomsheet_layout_notas, container, false)
        this.v = v
        //alterando tamanho do card
        val NotsList = ArrayList<Double>()
        val Bim = ArrayList<Int>()

        if (lista!![position].nota1 != -1.0) {
            NotsList.add(lista!![position].nota1)
            Bim.add(1)
        }
        if (lista!![position].nota2 != -1.0) {
            NotsList.add(lista!![position].nota2)
            Bim.add(2)
        }
        if (lista!![position].nota3 != -1.0) {
            NotsList.add(lista!![position].nota3)
            Bim.add(3)
        }
        if (lista!![position].nota4 != -1.0) {
            NotsList.add(lista!![position].nota4)
            Bim.add(4)
        }
        if (NotsList.isEmpty()) {
            val view = v.findViewById<View>(R.id.view_bsnotas)
            val layoutParams = view.layoutParams
            layoutParams.height = 0
            view.layoutParams = layoutParams
        } else {
            val view = v.findViewById<View>(R.id.view_bsnotas)
            val layoutParams = view.layoutParams
            layoutParams.height = 300
            view.layoutParams = layoutParams
        }
        //fim do tamanho alterado
        val disc = v.findViewById<TextView>(R.id.disciplina_bsnotas)
        anual = v.findViewById(R.id.nfinal_bsnotas)
        val excluir = v.findViewById<Button>(R.id.excluir_bsnotas)
        listNotas = v.findViewById(R.id.list_notasb)
        msg = v.findViewById(R.id.msg_bsnotas)
        ListTable()

        disc.text = DisciplinaSQL(this.context!!).PegarNome(lista!![position].id_disciplina)

        val format = DecimalFormat("0.00")
        if (lista!![position].nota1 != -1.0) {
            n1 = lista!![position].nota1
        } else {
            n1 = 0.0
        }

        if (lista!![position].nota2 != -1.0) {
            n2 = lista!![position].nota2
        } else {
            n2 = 0.0
        }

        if (lista!![position].nota3 != -1.0) {
            n3 = lista!![position].nota3
        } else {
            n3 = 0.0
        }


        if (lista!![position].nota4 != -1.0) {
            n4 = lista!![position].nota4
        } else {
            n4 = 0.0
        }
        val results = (60 - (n1 * 2 + n2 * 2 + n3 * 3 + n4 * 3)) / 10

        if (results < 0) {
            msg!!.text = "Você passou nessa matéria"
            anual!!.setTextColor(Color.parseColor("GREEN"))
            anual!!.text = format.format(results * -1)
        } else {
            msg!!.text = "Ainda faltam..."
            anual!!.setTextColor(Color.parseColor("RED"))
            anual!!.text = format.format(results)
        }
        excluir.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Deseja excluir todas as notas de " + DisciplinaSQL(context!!).PegarNome(lista!![position].id_disciplina) + "?")
            builder.setMessage("Você irá somente apagar todas as notas bimestrais da matéria!")

            builder.setPositiveButton("Excluir") { _, _ ->
                val bd = NotasSQL(context!!)
                if (bd.Deletar(lista!![position].id)) {
                    notasBimestraisFragment.BottomState()
                    dismiss()
                }
            }
            builder.setNeutralButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            val alerta: AlertDialog
            alerta = builder.create()
            alerta.show()
        }

        return v
    }

    fun List() {
        val bd = NotasSQL(this.context!!)
        val cursor = bd.Selecionar()
        if (cursor!!.count != 0) {
            val lista = ArrayList<Notas>()
            while (cursor.moveToNext()) {
                val notas = Notas()
                notas.id = cursor.getInt(0)
                notas.id_disciplina = cursor.getInt(1)
                notas.bimestre = cursor.getString(2)
                notas.nota1 = cursor.getDouble(3)
                notas.nota2 = cursor.getDouble(4)
                notas.nota3 = cursor.getDouble(5)
                notas.nota4 = cursor.getDouble(6)
                lista.add(notas)
            }
            this.lista = lista
        }
        ListTable()
        notasBimestraisFragment.BottomState()
        val format = DecimalFormat("0.00")
        if (lista!![position].nota1 != -1.0) {
            n1 = lista!![position].nota1
        } else {
            n1 = 0.0
        }

        if (lista!![position].nota2 != -1.0) {
            n2 = lista!![position].nota2
        } else {
            n2 = 0.0
        }

        if (lista!![position].nota3 != -1.0) {
            n3 = lista!![position].nota3
        } else {
            n3 = 0.0
        }


        if (lista!![position].nota4 != -1.0) {
            n4 = lista!![position].nota4
        } else {
            n4 = 0.0
        }
        val results = (60 - (n1 * 2 + n2 * 2 + n3 * 3 + n4 * 3)) / 10
        if (results < 0) {
            msg!!.text = "Você passou nessa matéria"
            anual!!.setTextColor(Color.parseColor("GREEN"))
            anual!!.text = format.format(results * -1)
        } else {
            msg!!.text = "Ainda faltam..."
            anual!!.setTextColor(Color.parseColor("RED"))
            anual!!.text = format.format(results)
        }
    }

    fun ListTable() {

        val NotsList = ArrayList<Double>()
        val Bim = ArrayList<Int>()

        if (lista!![position].nota1 != -1.0) {
            NotsList.add(lista!![position].nota1)
            Bim.add(1)
        }
        if (lista!![position].nota2 != -1.0) {
            NotsList.add(lista!![position].nota2)
            Bim.add(2)
        }
        if (lista!![position].nota3 != -1.0) {
            NotsList.add(lista!![position].nota3)
            Bim.add(3)
        }
        if (lista!![position].nota4 != -1.0) {
            NotsList.add(lista!![position].nota4)
            Bim.add(4)
        }
        if (NotsList.isEmpty()) {
            val view = v!!.findViewById<View>(R.id.view_bsnotas)
            val layoutParams = view.layoutParams
            layoutParams.height = 0
            view.layoutParams = layoutParams
        } else {
            val view = v!!.findViewById<View>(R.id.view_bsnotas)
            val layoutParams = view.layoutParams
            layoutParams.height = 300
            view.layoutParams = layoutParams
        }
        listNotas!!.adapter = ListAdapterbimestres(context!!, lista!!, Bim, NotsList, position, this)

    }
}
