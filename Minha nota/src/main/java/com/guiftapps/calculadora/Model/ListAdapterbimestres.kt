package com.guiftapps.calculadora.Model

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull

import com.guiftapps.calculadora.Controller.Notas
import com.guiftapps.calculadora.R

import java.text.DecimalFormat
import java.util.ArrayList

class ListAdapterbimestres(context: Context, private val listNotas: ArrayList<Notas>, internal var bimSel: ArrayList<Int>, private val bim: ArrayList<Double>, private val pos: Int, private val bottomSheetnotas: BottomSheetnotas) : ArrayAdapter<Double>(context, 0, bim) {
    @NonNull
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View = LayoutInflater.from(this.context).inflate(R.layout.type_list_bimestres, null)
        val objects = this.bim[position]

        val bimName = convertView.findViewById<TextView>(R.id.bimName_tlb)
        val bim = convertView.findViewById<TextView>(R.id.notabim_tlb)
        val b = convertView.findViewById<Button>(R.id.deleteButton_tlb)

        b.setOnClickListener {
            val notas = Notas()
            notas.id = listNotas[pos].id
            notas.id_disciplina = listNotas[pos].id_disciplina

            if (bimName.text == "1º Bimestre:") {
                notas.nota1 = (-1).toDouble()
                notas.nota2 = listNotas[pos].nota2
                notas.nota3 = listNotas[pos].nota3
                notas.nota4 = listNotas[pos].nota4
            } else if (bimName.text == "2º Bimestre:") {
                notas.nota1 = listNotas[pos].nota1
                notas.nota2 = (-1).toDouble()
                notas.nota3 = listNotas[pos].nota3
                notas.nota4 = listNotas[pos].nota4
            } else if (bimName.text == "3º Bimestre:") {
                notas.nota1 = listNotas[pos].nota1
                notas.nota2 = listNotas[pos].nota2
                notas.nota3 = (-1).toDouble()
                notas.nota4 = listNotas[pos].nota4
            } else if (bimName.text == "4º Bimestre:") {
                notas.nota1 = listNotas[pos].nota1
                notas.nota2 = listNotas[pos].nota2
                notas.nota3 = listNotas[pos].nota3
                notas.nota4 = (-1).toDouble()
            }

            if (NotasSQL(getContext()).Atualizar(notas)) {
                clear()
                bottomSheetnotas.List()
            }
        }
        val format = DecimalFormat("0.00")

        if (java.lang.Double.parseDouble(objects.toString()) != -1.0) {
            when (bimSel[position]) {
                1 -> {
                    bimName.text = "1º Bimestre:"
                    bim.text = format.format(objects)
                }
                2 -> {
                    bimName.text = "2º Bimestre:"
                    bim.text = format.format(objects)
                }
                3 -> {
                    bimName.text = "3º Bimestre:"
                    bim.text = format.format(objects)
                }
                4 -> {
                    bimName.text = "4º Bimestre:"
                    bim.text = format.format(objects)
                }
            }
        }


        return convertView
    }
}
