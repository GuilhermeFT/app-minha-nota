package com.guiftapps.calculadora.View

import android.app.Activity
import android.content.Intent
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
import android.widget.Toast

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.Model.MateriaSQL
import com.guiftapps.calculadora.Model.ListAdapterMateria
import com.guiftapps.calculadora.Controller.Materia
import com.guiftapps.calculadora.R

import java.text.DecimalFormat
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HistoricoFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HistoricoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoricoFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null
    private var ads: AdView? = null
    private var Listar: ListView? = null
    private var listAdapterMateria: ListAdapterMateria? = null
    private var alerta: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_historico, container, false)
        MobileAds.initialize(context, "ca-app-pub-2865932856120238~9749752037")

        Listar = rootView.findViewById(R.id.ListV)
        ads = rootView.findViewById(R.id.adView3)
        val adView = AdView(context!!)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = getString(R.string.adsCode)
        val bd = MateriaSQL(context!!)
        val txt = rootView.findViewById<TextView>(R.id.txt)
        Listar()

        if (bd.Listar()!!.count == 0) {
            txt.visibility = View.VISIBLE
            Listar()
        } else {
            listAdapterMateria!!.clear()
            Toast.makeText(context, "Toque para abrir as opções", Toast.LENGTH_LONG).show()
            Listar()
        }
        ads = rootView.findViewById(R.id.adView3)
        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)

        Listar!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val bd = MateriaSQL(context!!)
            val cursor = bd.Listar()
            if (cursor!!.count == 0) {

            } else {
                val lista = ArrayList<Materia>()
                while (cursor.moveToNext()) {
                    val mat = Materia()
                    mat.nomeMateria = cursor.getString(1)
                    mat.bim1 = cursor.getDouble(2)
                    mat.bim2 = cursor.getDouble(3)
                    mat.bim3 = cursor.getDouble(4)
                    mat.setnFinal(cursor.getDouble(5))
                    mat.status = cursor.getString(6)
                    lista.add(mat)
                }

                val builder = AlertDialog.Builder(context!!)
                builder.setTitle("O que deseja fazer com " + lista[position].nomeMateria + "?")
                builder.setMessage(" Selecione uma ação desejada em relação e esta nota selecionada!")

                builder.setPositiveButton("Excluir") { arg0, arg1 ->
                    val bd = MateriaSQL(context!!)
                    if (bd.Deletar(lista[position].nomeMateria!!)) {
                        Toast.makeText(context, "excluido com sucesso!", Toast.LENGTH_SHORT).show()
                        listAdapterMateria!!.clear()
                        Listar()
                    }
                }
                builder.setNeutralButton("Cancelar") { dialog, which -> alerta!!.dismiss() }
                builder.setNegativeButton("Editar") { arg0, arg1 ->
                    val intent = Intent(context, CalcularActivity::class.java)
                    intent.putExtra("nome", lista[position].nomeMateria)
                    intent.putExtra("b1", lista[position].bim1)
                    intent.putExtra("b2", lista[position].bim2)
                    intent.putExtra("b3", lista[position].bim3)
                    startActivityForResult(intent, 101)
                }
                alerta = builder.create()
                alerta!!.show()
            }
        }

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            listAdapterMateria!!.clear()
            Listar()
        }
    }

    fun Listar() {
        val bd = MateriaSQL(context!!)
        val cursor = bd.Listar()
        val format = DecimalFormat("0.00")
        if (cursor!!.count == 0) {
            val lista = ArrayList<Materia>()
            listAdapterMateria = ListAdapterMateria(context!!, lista)
            Listar!!.adapter = listAdapterMateria
        } else {
            val lista = ArrayList<Materia>()
            while (cursor.moveToNext()) {
                val mat = Materia()
                mat.nomeMateria = cursor.getString(1)
                mat.bim1 = cursor.getDouble(2)
                mat.bim2 = cursor.getDouble(3)
                mat.bim3 = cursor.getDouble(4)
                mat.status = cursor.getString(6)
                mat.setnFinal(cursor.getDouble(5))
                lista.add(mat)
                listAdapterMateria = ListAdapterMateria(context!!, lista)
                Listar!!.adapter = listAdapterMateria
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
         * @return A new instance of fragment HistoricoFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): HistoricoFragment {
            return HistoricoFragment()
        }
    }
}// Required empty public constructor
