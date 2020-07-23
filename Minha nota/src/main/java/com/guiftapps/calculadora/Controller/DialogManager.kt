package com.guiftapps.calculadora.Controller

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

import com.guiftapps.calculadora.Model.DisciplinaSQL
import com.guiftapps.calculadora.Model.NotasSQL
import com.guiftapps.calculadora.R

import java.util.ArrayList


class DialogManager
constructor(private val b: Button, context: Context, private val selectbim: Spinner) : DialogFragment() {
    val isCheck: Boolean


    init {
        val c: Cursor? = DisciplinaSQL(context).Selecionar()
        isCheck = c!!.moveToNext()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val arrayT = arrayOf("1º Bimestre", "2º Bimestre", "3º Bimestre", "4º Bimestre")
        val arrays = ArrayList<String>()
        val items = ArrayList<String>()
        val builder = AlertDialog.Builder(activity)
        val c = DisciplinaSQL(this.activity!!).Selecionar()
        if (c != null) {
            while (c.moveToNext()) {
                items.add(c.getString(1))
            }
            val arr = items.toTypedArray()
            builder.setTitle("Selecionar disciplina")
            builder.setItems(arr) { dialog, which ->
                b.text = items[which]

                val nota = Notas()
                nota.id_disciplina = DisciplinaSQL(this.context!!).PegarID(b.text.toString())
                for (i in 0..3) {

                    nota.bimestre = arrayT[i]
                    if (NotasSQL(this.context!!).Verificar(nota)) {
                        arrays.add(arrayT[i])
                    }
                }
                val adapter = ArrayAdapter(context!!, R.layout.my_spinner, arrays)
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                selectbim.adapter = adapter
            }
        }
        return builder.create()
    }
}

