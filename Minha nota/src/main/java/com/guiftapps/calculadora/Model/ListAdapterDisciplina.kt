package com.guiftapps.calculadora.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull

import com.guiftapps.calculadora.Controller.Disciplina
import com.guiftapps.calculadora.R

import java.util.ArrayList

class ListAdapterDisciplina(context: Context, private var listDisciplina: ArrayList<Disciplina>?) : ArrayAdapter<Disciplina>(context, 0, listDisciplina) {
    private var disciplina: TextView? = null
    private var status: TextView? = null
    private var dias: TextView? = null

    @NonNull
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View = LayoutInflater.from(this.context).inflate(R.layout.type_list_disciplinas, null)
        val objects = this.listDisciplina!![position]

        disciplina = convertView.findViewById(R.id.list_nomeMat)
        status = convertView.findViewById(R.id.list_statusNota)
        dias = convertView.findViewById(R.id.list_semana)

        disciplina!!.text = objects.disciplina

        if (NotasSQL(getContext()).PegarID(objects.id) != 0) {
            status!!.text = "Possui notas salvas"
        } else {
            status!!.text = "Não possui notas cadastradas"
        }

        if (objects.diaSemana == null) {
            dias!!.text = "Não há dias na semana"
        } else {
            dias!!.text = objects.diaSemana
        }
        return convertView
    }
}
