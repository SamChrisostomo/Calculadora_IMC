package com.crisostomo.calculadoraimc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnCalcular: Button
    private lateinit var editPeso: EditText
    private lateinit var editAltura: EditText
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCalcular = findViewById(R.id.btn_calcular)
        editPeso = findViewById<EditText>(R.id.edit_peso)
        editAltura = findViewById<EditText>(R.id.edit_altura)
        intent = Intent(this, ResultadoActivity::class.java)
    }

    override fun onStart() {
        super.onStart()
        btnCalcular.setOnClickListener {
            val pesoString = editPeso.text.toString().replace(",", ".")
            val alturaString = editAltura.text.toString().replace(",", ".")

            val peso = pesoString.toDouble()
            val altura = alturaString.toDouble()
            var alturaFormatada = altura

            if (altura > 3.0) {
                alturaFormatada *= 0.01
            }

            if (peso != null && altura != null) {
                intent.putExtra("peso", peso)
                intent.putExtra("altura", alturaFormatada)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Prencha os campos Peso e Altura corretamente",
                    Toast.LENGTH_LONG
                )
            }
        }
    }
}