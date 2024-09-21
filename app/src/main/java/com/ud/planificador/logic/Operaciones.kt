package com.ud.planificador.logic

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

open class Operaciones {
    open fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}