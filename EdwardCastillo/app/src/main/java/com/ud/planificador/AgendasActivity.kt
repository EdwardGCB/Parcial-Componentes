package com.ud.planificador

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.planificador.ui.theme.PlanificadorTheme

class AgendasActivity : ComponentActivity(){
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
        val estado = remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.title_add),
                            style = MaterialTheme.typography.displaySmall
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            /* Debo validar que el cliente quiere
                            borrar la informacion que deseaba crear*/
                            goToBack()
                        }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Retroceder"
                            )
                        }
                    }
                )
            }

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                Contenido()
            }

        }
    }

    private fun goToBack() {
        val intent = Intent(this, AddActivity::class.java)
        startActivity(intent)
    }

    @Composable
    private fun Contenido() {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Text(
                    text = stringResource(id = R.string.destino, "Cali"),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.start_date, "18/09/2024"),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(id = R.string.end_date, "18/12/2024"),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ){
                    Text(
                        text = stringResource(id = R.string.activity),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.content),
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
                ){
                    Text(
                        text = stringResource(id = R.string.site),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.content),
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            item {
                Text(text = stringResource(id = R.string.price),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth())
            }
        }
    }
}