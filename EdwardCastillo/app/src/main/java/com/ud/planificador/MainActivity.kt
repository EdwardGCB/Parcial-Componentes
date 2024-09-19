package com.ud.planificador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.planificador.ui.theme.PlanificadorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanificadorTheme {
                Principal()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    private fun Principal() {
        var searchText = remember { mutableStateOf("") }
        val envio = remember { mutableStateOf(false) }
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
                FloatingActionButton(onClick = { goToAdd("agregar") }) {
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
                BarraBusqueda(valor = searchText, envio = envio)
                LazyColumn {
                    items(count = 7) {
                        TarjetaViaje(
                            numero = it,
                            startDate = "17/09/2024",
                            endDate = "17/12/2024",
                            destination = "Cali"
                        )
                    }
                }
            }
        }
    }

    private fun goToAdd(tarea: String) {
        val intent = Intent(this, AddActivity::class.java).apply { putExtra("tarea", tarea) }
        startActivity(intent)
    }

    private fun goToInfo(numero: Int) {
        val intent =
            Intent(this, AgendasActivity::class.java).apply { putExtra("Numero", numero )}
        startActivity(intent)
    }


    @Composable
    private fun BarraBusqueda(valor: MutableState<String>, envio: MutableState<Boolean>) {
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
                        text = stringResource(id = R.string.search),
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
                    envio.value = true
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
    private fun TarjetaViaje(
        numero: Int,
        startDate: String,
        endDate: String,
        destination: String
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
                    text = stringResource(id = R.string.numViaje, (numero+1)),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.destino, destination),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(id = R.string.start_date, startDate),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.end_date, endDate),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick = { goToInfo(numero) }) {
                        Text(text = stringResource(id = R.string.details))
                    }
                }
            }
        }
    }
}






