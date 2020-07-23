package com.guiftapps.calculadora.View

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.Model.BottomSheetDisciplinas
import com.guiftapps.calculadora.Model.DisciplinaSQL
import com.guiftapps.calculadora.Model.ListAdapterDisciplina
import com.guiftapps.calculadora.Model.NotasSQL
import com.guiftapps.calculadora.Controller.Disciplina
import com.guiftapps.calculadora.R

import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DisciplinasListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DisciplinasListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisciplinasListFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null
    private var listDisciplinas: ListView? = null
    private var ads: AdView? = null
    private var txt: TextView? = null
    private var listAdapterDisciplina: ListAdapterDisciplina? = null
    private var d: DisciplinasListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_disciplinas, container, false)
        InicializeAds(rootView)

        d = this
        listDisciplinas = rootView.findViewById(R.id.listDisciplinas)
        Listar()
        txt = rootView.findViewById(R.id.statusDisciplina)

        val disciplinaSQL = DisciplinaSQL(this.context!!)
        if (disciplinaSQL.Selecionar()!!.count == 0) {
            txt!!.visibility = View.VISIBLE
            Listar()
        } else {
            listAdapterDisciplina!!.clear()
            Toast.makeText(context, "Toque para abrir as opções", Toast.LENGTH_LONG).show()
            Listar()
        }

        listDisciplinas!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val bd = DisciplinaSQL(this.context!!)
            val cursor = bd.Selecionar()
            val lista = ArrayList<Disciplina>()

            if (cursor!!.count == 0) {

            } else {
                while (cursor.moveToNext()) {
                    val disc = Disciplina()
                    disc.id = cursor.getInt(0)
                    disc.disciplina = cursor.getString(1)
                    disc.diaSemana = cursor.getString(2)
                    lista.add(disc)
                }
            }
            val bottomSheetDisciplinas = BottomSheetDisciplinas(lista, position, d!!)
            bottomSheetDisciplinas.show(fragmentManager!!, null)
        }
        return rootView
    }

    private fun InicializeAds(rootView: View) {
        ads = rootView.findViewById(R.id.adView6)
        MobileAds.initialize(context, "ca-app-pub-2865932856120238~9749752037")
        val adView = AdView(context!!)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = getString(R.string.adsCode)
        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)
    }

    fun BottonState(option: String, lista: ArrayList<Disciplina>, position: Int) {
        if (option == "delete") {
            if (DisciplinaSQL(this.context!!).Selecionar()!!.count == 0) {
                txt!!.visibility = View.VISIBLE
                Listar()
            } else {
                listAdapterDisciplina!!.clear()
                Listar()
            }
        } else if (option == "edit") {
            val intent = Intent(activity, SubjectActivity::class.java)
            intent.putExtra("nome", lista[position].disciplina)
            intent.putExtra("semana", lista[position].diaSemana)
            intent.putExtra("id", lista[position].id)
            startActivityForResult(intent, 10)
        } else if (option == "add") {
            var d = lista[position]
            var re = NotasSQL(activity!!.applicationContext).Selecionar(d.id)
            val intent = Intent(activity, BimestralActivity::class.java)
            if (re!!.moveToNext()) {
                intent.putExtra("id_disc", re!!.getInt(1))
            }
            startActivityForResult(intent, 10)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 10) {
            listAdapterDisciplina!!.clear()
            Listar()
        }
    }

    fun Listar() {
        val bd = DisciplinaSQL(context!!)
        val cursor = bd.Selecionar()

        if (cursor!!.count == 0) {
            val lista = ArrayList<Disciplina>()
            listAdapterDisciplina = ListAdapterDisciplina(context!!, lista)
            listDisciplinas!!.adapter = listAdapterDisciplina
        } else {
            val lista = ArrayList<Disciplina>()
            while (cursor.moveToNext()) {
                val disc = Disciplina()
                disc.id = cursor.getInt(0)
                disc.disciplina = cursor.getString(1)
                disc.diaSemana = cursor.getString(2)
                lista.add(disc)
                listAdapterDisciplina = ListAdapterDisciplina(context!!, lista)
                listDisciplinas!!.adapter = listAdapterDisciplina
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisciplinasListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): DisciplinasListFragment {

            return DisciplinasListFragment()
        }
    }
}// Required empty public constructor
