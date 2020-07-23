package com.guiftapps.calculadora.Controller

class Materia {
    var nomeMateria: String? = null
    var status: String? = null
    var bim1: Double = 0.toDouble()
    var bim2: Double = 0.toDouble()
    var bim3: Double = 0.toDouble()
    private var nFinal: Double = 0.toDouble()
    var id: Int = 0

    fun getnFinal(): Double {
        return nFinal
    }

    fun setnFinal(nFinal: Double) {
        this.nFinal = nFinal
    }
}
