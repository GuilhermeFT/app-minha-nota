package com.guiftapps.calculadora.Model

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.guiftapps.calculadora.Controller.Disciplina
import com.guiftapps.calculadora.R
import com.guiftapps.calculadora.View.DisciplinasListFragment
import kotlinx.android.synthetic.main.bottomsheet_layout_disciplina.view.*

import java.util.ArrayList

@SuppressLint("ValidFragment")
class BottomSheetDisciplinas @SuppressLint("ValidFragment")
constructor(private val lista: ArrayList<Disciplina>, private val position: Int, private val disciplinasListFragment: DisciplinasListFragment) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.bottomsheet_layout_disciplina, container, false)

        val disc = v.findViewById<TextView>(R.id.disciplina_bot)
        val dias = v.findViewById<TextView>(R.id.diassemana_bot)
        val excluir = v.findViewById<Button>(R.id.deletebuttom)
        val editar = v.findViewById<Button>(R.id.editbuttom)

        disc.text = lista[position].disciplina

        if (lista[position].diaSemana == null) {
            dias.text = "Dias da semana desabilitado"
        } else {
            dias.text = lista[position].diaSemana
        }

        excluir.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Deseja excluir " + lista[position].disciplina + "?")
            builder.setMessage("você irá apagar esta disciplina de seu celular e não terá como recuperá-la!")

            builder.setPositiveButton("Excluir") { _, _ ->
                if (DisciplinaSQL(context!!).Deletar(lista[position].id)) {
                    if (NotasSQL(context!!).PegarID(lista[position].id) != 0) {

                        if (NotasSQL(context!!).Deletar(NotasSQL(context!!).PegarID(lista[position].id))) {
                            disciplinasListFragment.BottonState("delete", lista, position)
                            dismiss()
                        }
                    } else {
                        disciplinasListFragment.BottonState("delete", lista, position)
                        dismiss()
                    }
                }
            }
            builder.setNeutralButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            val alerta = builder.create()
            alerta.show()
        }

        v.btn_adicionar.setOnClickListener{
            dismiss()
            disciplinasListFragment.BottonState("add", lista, position)
        }

        editar.setOnClickListener {
            dismiss()
            disciplinasListFragment.BottonState("edit", lista, position)
        }
        return v
    }
}
