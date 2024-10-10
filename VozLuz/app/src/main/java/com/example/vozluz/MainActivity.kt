package com.example.vozluz

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.constraintlayout.utils.widget.ImageFilterView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var imageFilterView: ImageFilterView
    private val REQUEST_CODE_SPEECH_INPUT = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val speakButton: ImageButton = findViewById(R.id.imageButton)
        imageFilterView = findViewById(R.id.imageFilterView2)

        // Pedir permiso para el micrófono
        checkAudioPermission()

        speakButton.setOnClickListener {
            // Iniciar reconocimiento de voz
            startVoiceRecognition()
        }
    }

    private fun checkAudioPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga un color")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "El reconocimiento de voz no es compatible en este dispositivo",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val spokenText = result?.get(0)?.toLowerCase()

                    // Cambiar el color dependiendo del texto dicho
                    changeBackgroundColor(spokenText)
                }
            }
        }
    }

    private fun changeBackgroundColor(color: String?) {
        when (color?.toLowerCase(Locale.ROOT)) { // Convertimos a minúsculas para evitar problemas de coincidencia
            // Colores primarios
            "rojo" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_red_dark
                )
            )

            "verde" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_green_dark
                )
            )

            "azul" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_blue_dark
                )
            )

            "amarillo" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_orange_light
                )
            )

            // Colores secundarios
            "naranja" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_orange_dark
                )
            )

            "morado", "violeta" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.holo_purple
                )
            )

            // Nuevos colores
            "negro" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.black
                )
            )

            "plomo", "gris", "gray" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray
                )
            )  // Definir el gris en colors.xml
            "celeste", "light blue" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.light_blue
                )
            )  // Definir el celeste en colors.xml
            "blanco" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    android.R.color.white
                )
            )

            "rosado", "pink" -> imageFilterView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.pink
                )
            )  // Definir el rosado en colors.xml

            // Color no reconocido
            else -> Toast.makeText(this, "Color no reconocido", Toast.LENGTH_SHORT).show()
        }
    }
}

