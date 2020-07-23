package com.guiftapps.calculadora.View

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.Model.BottomSheetnotas
import com.guiftapps.calculadora.Model.ListAdapterNotas
import com.guiftapps.calculadora.Model.NotasSQL
import com.guiftapps.calculadora.Controller.Notas
import com.guiftapps.calculadora.R

import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NotasBimestraisFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NotasBimestraisFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotasBimestraisFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null
    private var listNotas: ListView? = null
    private var nb: NotasBimestraisFragment? = null
    private var ads: AdView? = null
    private var listAdapterNotas: ListAdapterNotas? = null
    private val alerta: AlertDialog? = null
    private var txt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_notas_bimestrais, container, false)
        nb = this

        listNotas = rootView.findViewById(R.id.list_Nbimestrais)
        txt = rootView.findViewById(R.id.txt2)
        Listar()
        if (NotasSQL(context!!).Selecionar()!!.count == 0) {
            txt!!.visibility = View.VISIBLE
            Listar()
        } else {
            listAdapterNotas!!.clear()
            Listar()
        }

        listNotas!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val bd = NotasSQL(context!!)
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
                val bottomSheetnotas = BottomSheetnotas(lista, position, nb!!)
                bottomSheetnotas.show(fragmentManager!!, null)
            }
        }

        return rootView
    }

    fun BottomState() {
        if (NotasSQL(context!!).Selecionar()!!.count == 0) {
            txt!!.visibility = View.VISIBLE
            Listar()
        } else {
            listAdapterNotas!!.clear()
            Listar()
        }
    }

    fun InicializeAds(rootView: View) {
        ads = rootView.findViewById(R.id.adView9)
        MobileAds.initialize(context, "ca-app-pub-2865932856120238~9749752037")
        val adView = AdView(context!!)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = getString(R.string.adsCode)
        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)
    }

    fun Listar() {
        val bd = NotasSQL(context!!)
        val cursor = bd.Selecionar()
        if (cursor!!.count == 0) {
            val lista = ArrayList<Notas>()
            listAdapterNotas = ListAdapterNotas(context!!, lista)
            listNotas!!.adapter = listAdapterNotas
        } else {
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
                listAdapterNotas = ListAdapterNotas(context!!, lista)
                listNotas!!.adapter = listAdapterNotas
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
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
         * @return A new instance of fragment NotasBimestraisFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): NotasBimestraisFragment {
            return NotasBimestraisFragment()
        }
    }

}// Required empty public constructor
