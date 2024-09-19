package com.ud.planificador


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.ud.planificador.ui.theme.PlanificadorTheme

class AddActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanificadorTheme {
                Text(text = "En construccion")
                //Principal()
            }
        }
    }
}