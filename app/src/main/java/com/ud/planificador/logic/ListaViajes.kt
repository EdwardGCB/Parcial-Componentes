package com.ud.planificador.logic

import androidx.compose.runtime.MutableState

open class ListaViajes {
    private val viajes: MutableList<Viaje> = mutableListOf()

    open fun AgregarViaje(viaje: Viaje) {
        viajes.add(viaje)
    }

    open fun ActualizarViaje(viaje: Viaje){
        viajes[viaje.idViaje-1] = viaje
    }

    open fun EliminarViaje(indice: Int){
        viajes.removeAt(indice)
    }

    open fun ObtenerViajes(): List<Viaje> {
        if (viajes.isEmpty()) {
            val viaje1 = Viaje(
                1,
                "París",
                10000.0,
                "20/09/2024",
                "01/10/2024",
                "Visitar la Torre Eiffel y disfrutar de las vistas panorámicas.\n" +
                        "Recorrer el Museo del Louvre para admirar obras maestras como la Mona Lisa.\n" +
                        "Pasear por el encantador barrio de Montmartre y visitar la Basílica del Sagrado Corazón.\n" +
                        "Degustar croissants y macarons en una cafetería parisina.",
                "Torre Eiffel\n" +
                        "Museo del Louvre\n" +
                        "Montmartre\n" +
                        "Notre-Dame\n" +
                        "El Sena"
            )
            val viaje2 = Viaje(
                2,
                "Tokio",
                20000.0,
                "01/06/2025",
                "12/07/2025",
                "Explorar el animado distrito de Shibuya y cruzar el famoso cruce peatonal.\n" +
                        "Sumergirse en la cultura japonesa en el Templo Senso-ji en Asakusa.\n" +
                        "Probar sushi fresco en el mercado de pescado Tsukiji.\n" +
                        "Visitar el Museo Ghibli si eres fanático de las películas de Studio Ghibli.",
                "Torre de Tokio\n" +
                        "Barrio de Shibuya\n" +
                        "Templo Senso-ji en Asakusa\n" +
                        "Mercado de Tsukiji\n" +
                        "Museo Ghibli"
            )
            val viaje3 = Viaje(
                3,
                "Nueva York",
                30000.0,
                "01/08/2025",
                "12/09/2025",
                "Caminar por el Central Park y disfrutar de un picnic.\n" +
                        "Ver un espectáculo de Broadway en Times Square.\n" +
                        "Visitar el Museo Metropolitano de Arte (Met) para apreciar su vasta colección.\n" +
                        "Recorrer el High Line, un parque elevado construido sobre antiguas vías de tren.",
                "Times Square\n" +
                        "Quinta Avenida (5th Avenue)\n" +
                        "Central Park\n" +
                        "Estátua de la Libertad\n" +
                        "Museo Metropolitano de Arte (Met)"

            )
            AgregarViaje(viaje1)
            AgregarViaje(viaje2)
            AgregarViaje(viaje3)
        }
        return viajes.toList()
    }
}