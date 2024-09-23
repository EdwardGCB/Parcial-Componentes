package com.ud.planificador.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ud.planificador.R
import com.ud.planificador.logic.ListaViajes
import com.ud.planificador.logic.Operaciones
import com.ud.planificador.logic.Viaje

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoView(navController: NavController, id: Int) {
    val viaje: Viaje? = Operaciones().BuscarViaje(id, ListaViajes().ObtenerViajes())
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_add, id),
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home")
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Retroceder"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (viaje != null) {
                BottonFloating(navController, viaje.idViaje)
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            if (viaje != null) {
                Contenido(viaje)
            }
        }
    }
}

@Composable
fun BottonFloating(navController: NavController, id: Int) {
    val estado = remember { mutableStateOf(false) }
    val mostrarMensaje = remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = androidx.compose.ui.Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            if (estado.value) {
                Spacer(modifier = Modifier.height(8.dp))
                ExtendedFloatingActionButton(
                    onClick = {
                        navController.navigate("viaje/add/$id")
                    }
                ) {
                    Icon(Icons.Default.Create, contentDescription = "Editar")
                }
                Spacer(modifier = Modifier.height(8.dp))
                ExtendedFloatingActionButton(
                    onClick = {
                        mostrarMensaje.value = !mostrarMensaje.value
                    }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            FloatingActionButton(
                onClick = { estado.value = !estado.value }
            ) {
                Icon(
                    if (!estado.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Desplegar"
                )
            }
            if (mostrarMensaje.value) {
                MensajeConfirmacion(navController, id, mostrarMensaje)
            }
        }
    }
}

@Composable
fun Contenido(viaje: Viaje) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            Text(
                text = stringResource(R.string.destino, viaje.destino),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.start_date, viaje.fechaIn),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.end_date, viaje.fechaFi),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.activity),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.content, viaje.actividades),
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.site),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.content, viaje.lugares),
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        item {
            Text(
                text = stringResource(R.string.price, viaje.presupuesto),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun MensajeConfirmacion(navController: NavController, idViaje: Int, mostrarMensaje: MutableState<Boolean>) {
    Column(modifier = Modifier.padding(16.dp)) {
        AlertDialog(
            icon = {
                Icon(Icons.Default.Delete, "Eliminar")
            },
            onDismissRequest = { mostrarMensaje.value = !mostrarMensaje.value },
            title = {
                Text(
                    text = "¿Esta seguro de eliminar el viaje?"
                )
            },
            dismissButton = {
                Button(onClick = { mostrarMensaje.value = !mostrarMensaje.value }) {
                    Text(text = "Cancelar")
                }
            },
            confirmButton = {
                Button(onClick = {
                    ListaViajes().EliminarViaje(idViaje-1)
                    navController.navigate("home")
                }) {
                    Text(text = "Aceptar")
                }
            }
        )
    }
}