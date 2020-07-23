package com.guiftapps.calculadora.View

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.R


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PFFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [PFFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PFFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var notaPF: EditText? = null
    private var calcPF: Button? = null
    private var result: TextView? = null
    private var status: TextView? = null
    private var ads: AdView? = null

    private var mListener: OnFragmentInteractionListener? = null

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
        val rootView = inflater.inflate(R.layout.fragment_prova_final, container, false)
        MobileAds.initialize(context, "ca-app-pub-2865932856120238~9749752037")

        status = rootView.findViewById(R.id.statuspff)
        result = rootView.findViewById(R.id.Resultado)
        notaPF = rootView.findViewById(R.id.nota_pf)
        calcPF = rootView.findViewById(R.id.CalcularPF)
        calcPF!!.setOnClickListener { PFs() }


        ads = rootView.findViewById(R.id.adView)
        val adView = AdView(context!!)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = getString(R.string.adsCode)

        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)

        return rootView
    }

    fun PFs() {
        var nota = 0.0
        try {
            nota = java.lang.Double.parseDouble(notaPF!!.text.toString())
        } catch (e: NumberFormatException) {

        }

        val notaFinal: Double
        if (notaPF!!.text.toString() == "") {
            notaPF!!.setText("0")
        }
        if (nota < 6) {
            notaFinal = 10 - nota
            result!!.text = notaFinal.toString()
            status!!.text = "Você precisa de:"
            result!!.setTextColor(Color.parseColor("RED"))
        } else {
            val alerta = AlertDialog.Builder(context!!)
            alerta.setTitle("Nota inválida!")
            alerta.setMessage("Sua nota tem que ser menor que 6 (seis)!")
            val meualerta = alerta.create()
            meualerta.show()
            notaPF!!.text = null
            result!!.text = "0"
            result!!.setTextColor(Color.parseColor("BLACK"))
            status!!.text = "Resultado:"
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
         * @return A new instance of fragment PFFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): PFFragment {

            return PFFragment()

        }
    }
}// Required empty public constructor
