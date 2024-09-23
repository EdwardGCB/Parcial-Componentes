package com.ud.planificador.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ud.planificador.R
import com.ud.planificador.logic.ListaViajes
import com.ud.planificador.logic.Operaciones
import com.ud.planificador.logic.Viaje


@Composable
fun AddView(navController: NavController, id: Int?) {
    CardFormulario(navController, id)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardFormulario(navController: NavController, id: Int?) {
    val destino = remember { mutableStateOf("") }
    val fechaIn = remember { mutableStateOf("") }
    val fechaFi = remember { mutableStateOf("") }
    val actividad = remember { mutableStateOf("") }
    val lugares = remember { mutableStateOf("") }
    val presupuesto = remember { mutableDoubleStateOf(0.0) }
    val num = remember { mutableIntStateOf(0) }
    id?.let { num.intValue = it }
    if (num.value != 0){
        val viaje: Viaje? = Operaciones().BuscarViaje(num.value, ListaViajes().ObtenerViajes())

        if (viaje != null) {
            destino.value = viaje.destino
            fechaIn.value = viaje.fechaIn
            fechaFi.value = viaje.fechaFi
            actividad.value = viaje.actividades
            lugares.value = viaje.lugares
            presupuesto.value = viaje.presupuesto
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (num.intValue == 0) stringResource(R.string.sub_title_add) else stringResource(
                            R.string.numViaje, num.value
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, "Regresar Home")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = destino.value,
                    onValueChange = {
                        destino.value = it
                    },
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                    ),
                    keyboardActions = KeyboardActions(onAny = {}),
                    label = {
                        Text(
                            text = stringResource(R.string.nombre_destino),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                )
                FieldFechas(title = stringResource(R.string.start_date, ""), fechaIn)
                FieldFechas(title = stringResource(R.string.end_date, ""), fechaFi)
                FieldScroll(title = stringResource(R.string.activity), actividad)
                FieldScroll(title = stringResource(R.string.site), lugares)
                PresupuestoScrolling(presupuesto)

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    if (num.intValue == 0) BottonAgregar(
                        destino,
                        fechaIn,
                        fechaFi,
                        actividad,
                        lugares,
                        presupuesto,
                        navController
                    ) else BottonModificar(
                        num,
                        destino,
                        fechaIn,
                        fechaFi,
                        actividad,
                        lugares,
                        presupuesto,
                        navController
                    )
                }
            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldFechas(title: String, date: MutableState<String>) {
    val mostrarListaFechas = remember { mutableStateOf(false) }
    val estadoFecha = rememberDatePickerState()
    date.value = estadoFecha.selectedDateMillis?.let { Operaciones().convertMillisToDate(it) } ?: ""
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = date.value,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onValueChange = { mostrarListaFechas.value = !mostrarListaFechas.value },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { mostrarListaFechas.value = !mostrarListaFechas.value }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Seleccionar fecha"
                )
            }
        },
        label = {
            Text(text = title)
        }
    )

    if (mostrarListaFechas.value) {
        DatePicker(
            state = estadoFecha,
            showModeToggle = false
        )
    }
}

@Composable
fun FieldScroll(title: String, text: MutableState<String>) {
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        label = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}

@Composable
fun BottonAgregar(
    destino: MutableState<String>,
    fechaIn: MutableState<String>,
    fechaFi: MutableState<String>,
    actividad: MutableState<String>,
    lugares: MutableState<String>,
    presupuesto: MutableState<Double>,
    navController: NavController
) {
    val confirmar = remember { mutableStateOf(false) }
    val alerta = remember { mutableStateOf(false) }
    Button(onClick = {
        if (Operaciones().EspacionVacios(
                destino,
                fechaIn,
                fechaFi,
                actividad,
                lugares,
                presupuesto
            )
        ) {
            alerta.value = !alerta.value
        } else {
            val viaje = Viaje(
                Operaciones().IdViaje(ListaViajes().ObtenerViajes()),
                destino.value,
                presupuesto.value,
                fechaIn.value,
                fechaFi.value,
                actividad.value,
                lugares.value
            )
            ListaViajes().AgregarViaje(viaje)
            confirmar.value = !confirmar.value
        }
    }) {
        Text(text = stringResource(R.string.add))
        Icon(Icons.Default.Send, "Envio")
        MensajeEspacioBlanco(alerta)
        MensajeConfirmacion(navController, confirmar, "Viaje agregado correctamente")
    }
}

@Composable
fun BottonModificar(
    idViaje: MutableState<Int>,
    destino: MutableState<String>,
    fechaIn: MutableState<String>,
    fechaFi: MutableState<String>,
    actividad: MutableState<String>,
    lugares: MutableState<String>,
    presupuesto: MutableState<Double>,
    navController: NavController
) {
    val confirmar = remember { mutableStateOf(false) }
    val alerta = remember { mutableStateOf(false) }
    Button(onClick = {
        if (Operaciones().EspacionVacios(
                destino,
                fechaIn,
                fechaFi,
                actividad,
                lugares,
                presupuesto
            )
        ) {
            alerta.value = !alerta.value
        } else {
            val viaje = Viaje(
                idViaje.value,
                destino.value,
                presupuesto.value,
                fechaIn.value,
                fechaFi.value,
                actividad.value,
                lugares.value
            )
            ListaViajes().ActualizarViaje(viaje)
            confirmar.value = !confirmar.value
        }
    }) {
        Text(text = stringResource(R.string.modify))
        Icon(Icons.Default.Send, "Envio")
        MensajeEspacioBlanco(alerta)
        MensajeConfirmacion(navController, confirmar, "Viaje modificado correctamente")
    }
}


@Composable
fun MensajeConfirmacion(navController: NavController, mostrarMensaje: MutableState<Boolean>, mensaje: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        if(mostrarMensaje.value){
            AlertDialog(
                icon = {
                    Icon(Icons.Default.CheckCircle, "Correcto")
                },
                onDismissRequest = { mostrarMensaje.value = !mostrarMensaje.value },
                title = {
                    Text(
                        text = mensaje
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        navController.navigate("Home")
                    }) {
                        Text(text = "Aceptar")
                    }
                }
            )
        }
    }
}


@Composable
fun MensajeEspacioBlanco(mostrarMensaje: MutableState<Boolean>) {
    Column(modifier = Modifier.padding(16.dp)) {
        if(mostrarMensaje.value){
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Clear, "Alerta")
                },
                onDismissRequest = { mostrarMensaje.value = !mostrarMensaje.value },
                title = {
                    Text(
                        text = "Todos los campos son obligatorios"
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        mostrarMensaje.value = !mostrarMensaje.value
                    }) {
                        Text(text = "Aceptar")
                    }
                }
            )
        }
    }
}

@Composable
fun PresupuestoScrolling(presupuesto: MutableState<Double>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Presupuesto actual: ${presupuesto.value}")

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = presupuesto.value.toFloat(),
            onValueChange = { nuevoPresupuesto ->
                presupuesto.value = nuevoPresupuesto.toDouble()
            },
            valueRange = 0f..5000f,
            steps = 100
        )
        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = presupuesto.value.toString(),
            onValueChange = { nuevoPresupuesto ->
                presupuesto.value = nuevoPresupuesto.toDoubleOrNull() ?: 0.0
            }
        )
    }
}
