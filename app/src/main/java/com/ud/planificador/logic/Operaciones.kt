package com.ud.planificador.logic

import androidx.compose.runtime.MutableState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class Operaciones {
    open fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    open fun EspacionVacios(
        destino: MutableState<String>,
        fechaIn: MutableState<String>,
        fechaFi: MutableState<String>,
        actividad: MutableState<String>,
        lugares: MutableState<String>,
        presupuesto: MutableState<Double>
    ): Boolean{
        return (
                destino.value == "" ||
                fechaIn.value == "" ||
                fechaFi.value == "" ||
                actividad.value == "" ||
                lugares.value == "" ||
                presupuesto.value == 0.0
                )
    }

    open fun FiltrarViajes(filtro: MutableState<String>, viajes2: List<Viaje>): List<Viaje> {
        return viajes2.filter { it.destino.contains(filtro.value, ignoreCase = true) }
    }

    open fun BuscarViaje(idViaje: Int, viajes2: List<Viaje>): Viaje? {
        return viajes2.find { it.idViaje == idViaje }
    }

    open fun IdViaje(viaje: List<Viaje>): Int{
        return viaje.size+1
    }
}