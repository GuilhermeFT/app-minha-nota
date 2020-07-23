package com.guiftapps.calculadora.Model

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull

import com.guiftapps.calculadora.Controller.Notas
import com.guiftapps.calculadora.R

import java.text.DecimalFormat
import java.util.ArrayList

class ListAdapterNotas(context: Context, private val listNotas: ArrayList<Notas>) : ArrayAdapter<Notas>(context, 0, listNotas) {
    @NonNull
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View = LayoutInflater.from(this.context).inflate(R.layout.type_list_notasdisciplina, null)
        val objects = this.listNotas[position]

        val b1 = convertView.findViewById<TextView>(R.id.Mbim1)
        val b2 = convertView.findViewById<TextView>(R.id.Mbim2)
        val b3 = convertView.findViewById<TextView>(R.id.Mbim3)
        val b4 = convertView.findViewById<TextView>(R.id.Mbim4)
        val notaFinal = convertView.findViewById<TextView>(R.id.NotaFinal_ListNotas)
        val disciplina = convertView.findViewById<TextView>(R.id.ListNotas_disciplina)

        val n1: Double
        val n2: Double
        val n3: Double
        val n4: Double
        val format = DecimalFormat("0.00")
        if (objects.nota1 != -1.0) {
            b1.text = format.format(objects.nota1)
            n1 = objects.nota1
        } else {
            b1.text = "N/D"
            n1 = 0.0
        }

        if (objects.nota2 != -1.0) {
            b2.text = format.format(objects.nota2)
            n2 = objects.nota2
        } else {
            b2.text = "N/D"
            n2 = 0.0
        }

        if (objects.nota3 != -1.0) {
            b3.text = format.format(objects.nota3)
            n3 = objects.nota3
        } else {
            b3.text = "N/D"
            n3 = 0.0
        }


        if (objects.nota4 != -1.0) {
            b4.text = format.format(objects.nota4)
            n4 = objects.nota4
        } else {
            b4.text = "N/D"
            n4 = 0.0
        }

        val results = (60 - (n1 * 2 + n2 * 2 + n3 * 3 + n4 * 3)) / 10

        if (results < 0) {
            notaFinal.setTextColor(Color.parseColor("GREEN"))
            notaFinal.text = format.format(results * -1)
        } else {
            notaFinal.setTextColor(Color.parseColor("RED"))
            notaFinal.text = format.format(results)
        }

        disciplina.text = DisciplinaSQL(getContext()).PegarNome(objects.id_disciplina)
        return convertView
    }
}
