package com.ud.planificador.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ud.planificador.R
import com.ud.planificador.logic.ListaViajes
import com.ud.planificador.logic.Operaciones
import com.ud.planificador.logic.Viaje

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalView(navController: NavController) {
    val searchText = remember { mutableStateOf("") }
    var viajes: List<Viaje> = listOf()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.subTitle),
                        style = MaterialTheme.typography.displaySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("viaje/add/${0}")
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            BarraBusqueda(searchText)
            LazyColumn {
                if (searchText.value == "") viajes = ListaViajes().ObtenerViajes() else viajes =
                    Operaciones().FiltrarViajes(searchText, ListaViajes().ObtenerViajes())
                items(viajes.size) {
                    val i = it
                    TarjetaViaje(
                        viajes[i].idViaje,
                        viajes[i].destino,
                        viajes[i].fechaIn,
                        viajes[i].fechaFi,
                        viajes[i].presupuesto,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun BarraBusqueda(valor: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        TextField(
            value = valor.value,
            onValueChange = {
                valor.value = it
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.search),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            modifier = Modifier
                .alignByBaseline()
                .weight(0.9F)
                .padding(start = 6.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
            ),
            keyboardActions = KeyboardActions(onAny = {

            })
        )
        IconButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Busqueda"
            )
        }
    }
}


@Composable
fun TarjetaViaje(
    idViaje: Int,
    destino: String,
    fechaIn: String,
    fechaFi: String,
    presupuesto: Double,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.numViaje, idViaje),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.destino, destino),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.start_date, fechaIn),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.end_date, fechaFi),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.price, presupuesto),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = { navController.navigate("viaje/$idViaje") }) {
                    Text(text = stringResource(id = R.string.details))
                }
            }
        }
    }
}