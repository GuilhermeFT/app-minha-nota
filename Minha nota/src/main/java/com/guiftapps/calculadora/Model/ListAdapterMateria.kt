package com.guiftapps.calculadora.Model

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

import com.guiftapps.calculadora.Controller.Materia
import com.guiftapps.calculadora.R

import java.text.DecimalFormat
import java.util.ArrayList

class ListAdapterMateria(context: Context, private val listMateria: ArrayList<Materia>) : ArrayAdapter<Materia>(context, 0, listMateria) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = LayoutInflater.from(this.context).inflate(R.layout.type_list_last_bimestre, null)
        val posicao = this.listMateria[position]

        val nome = convertView!!.findViewById<TextView>(R.id.nomemat)
        nome.text = posicao.nomeMateria

        val status = convertView.findViewById<TextView>(R.id.statusM)
        status.text = posicao.status

        val bim1 = convertView.findViewById<TextView>(R.id.bimA)
        bim1.text = posicao.bim1.toString()

        val bim2 = convertView.findViewById<TextView>(R.id.bimB)
        bim2.text = posicao.bim2.toString()

        val bim3 = convertView.findViewById<TextView>(R.id.bimC)
        bim3.text = posicao.bim3.toString()

        val formato = DecimalFormat("0.00")
        val total = convertView.findViewById<TextView>(R.id.total)
        total.text = formato.format(posicao.getnFinal())

        val result = (60 - (posicao.bim1 * 2 + posicao.bim2 * 2 + posicao.bim3 * 3)) / 3
        if (result > 0) {
            total.setTextColor(Color.parseColor("RED"))
        } else {
            total.setTextColor(Color.parseColor("GREEN"))
        }

        return convertView
    }
}
