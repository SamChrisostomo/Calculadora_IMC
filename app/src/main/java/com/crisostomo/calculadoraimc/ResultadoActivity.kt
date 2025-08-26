package com.crisostomo.calculadoraimc

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultadoActivity : AppCompatActivity() {
    private lateinit var textPeso: TextView
    private lateinit var textAltura: TextView
    private lateinit var textResultado: TextView
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bundle = intent.extras!!

        textPeso = findViewById<TextView>(R.id.text_peso)
        textAltura = findViewById<TextView>(R.id.text_altura)
        textResultado = findViewById<TextView>(R.id.text_resultado)
    }

    override fun onStart() {
        super.onStart()
        if (bundle.isEmpty) {
            Toast.makeText(this, "Não foi possível calcular o IMC", Toast.LENGTH_LONG).show()
            return
        }

        val pesoString = bundle.getString("peso")
        val alturaString = bundle.getString("altura")

        var peso = pesoString?.toFloat()
        var altura = alturaString?.toFloat()

        if (altura != null && altura > 3.0f) {
            altura *= 0.01f
        }

        textPeso.setText("Peso digitado: %.2f kg".format(peso))
        textAltura.setText("Altura digitada: %.2f m".format(altura))
        calculateImcResult(peso, altura)
    }

    fun calculateImcResult(peso: Float?, altura: Float?) {
        var imc: Float = 0.0F

        if (peso != null && altura != null) {
            imc = peso / (altura * altura)
        }

        val resultado = when {
            imc < 18.5 -> "Abaixo do peso"
            imc in 18.5..24.9 -> "Peso normal"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc in 30.0..34.5 -> "Obesidade grau 1"
            imc in 35.0..39.9 -> "Obesidade grau 2"
            else -> "Obesidade grau 3"
        }

        textResultado.setText("Resultado: %.2f (%s)".format(imc, resultado))
    }
}