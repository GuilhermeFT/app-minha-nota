package com.guiftapps.calculadora.View

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.guiftapps.calculadora.Model.DisciplinaSQL
import com.guiftapps.calculadora.R
import kotlinx.android.synthetic.main.fragment_main.view.*

import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }
    private var mainActivity:MainActivity? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)

        nct = context
        matH = v.findViewById(R.id.mathoje)
        matA = v.findViewById(R.id.matamanha)
        v.btn_notabimestral.setOnClickListener{
            startActivity(Intent(activity!!.applicationContext, BimestralActivity::class.java))
        }
        v.btn_notafinal.setOnClickListener{
            startActivity(Intent(activity!!.applicationContext, CalcularActivity::class.java))
        }
        v.btn_sobre.setOnClickListener{
            startActivity(Intent(activity!!.applicationContext, InformacaoActivity::class.java))
        }
        v.btn_disciplina.setOnClickListener{
            startActivity(Intent(activity!!.applicationContext, SubjectActivity::class.java))
        }
        v.btn_opcoes.setOnClickListener{
            mainActivity!!.openDrawer()
        }

        MostrarMatérias()
        return v

    }

    fun setMain(m:MainActivity) {
        mainActivity = m
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
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): MainFragment {

            return MainFragment()
        }

        private var matH: TextView? = null
        private var matA: TextView? = null
        private var nct: Context? = null

        fun MostrarMatérias() {

            val date = Date()
            val c = GregorianCalendar()
            c.time = date
            var day = DateFormatSymbols().weekdays[c.get(Calendar.DAY_OF_WEEK)]
            var sem = ""
            var daymodify = day.split("\\-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            var dsc = DisciplinaSQL(nct!!).Selecionar()
            if (dsc != null) {
                while (dsc.moveToNext()) {
                    val dias: Array<String>
                    if (dsc.getString(2) != null) {
                        dias = dsc.getString(2).split("\\ ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                        for (i in dias.indices) {
                            if (dias[i].equals(daymodify[0], ignoreCase = true)) {
                                sem += dsc.getString(1) + ";\n"
                            }
                        }
                    }
                }
            }
            if (sem != "") {
                matH!!.text = sem
                sem = ""
            } else {
                matH!!.text = "Não há matérias para hoje"
            }

            c.add(Calendar.DAY_OF_WEEK, 1)
            day = DateFormatSymbols().weekdays[c.get(Calendar.DAY_OF_WEEK)]
            daymodify = day.split("\\-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            dsc = DisciplinaSQL(nct!!).Selecionar()

            if (dsc != null) {
                while (dsc.moveToNext()) {
                    val dias: Array<String>
                    if (dsc.getString(2) != null) {
                        dias = dsc.getString(2).split("\\ ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                        for (i in dias.indices) {
                            if (dias[i].equals(daymodify[0], ignoreCase = true)) {
                                sem += dsc.getString(1) + ";\n"
                            }
                        }
                    }
                }
            }

            if (sem != "") {
                matA!!.text = sem
                sem = ""
            } else {
                matA!!.text = "Não há matérias para amanhã"
            }
        }
    }
}// Required empty public constructor
