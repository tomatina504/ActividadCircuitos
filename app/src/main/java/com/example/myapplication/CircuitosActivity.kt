package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CircuitosActivity : AppCompatActivity() {

    private lateinit var dbReference: DatabaseReference
    private var nombreUsuario: String = "Usuario"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circuitos)

        // Recuperar exclusivamente el nombre del usuario enviado desde el menú o login
        nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"

        // Referencia directa al nodo "Acciones" en Firebase
        dbReference = FirebaseDatabase.getInstance().getReference("Acciones")

        findViewById<Button>(R.id.btnActivarCircuito).setOnClickListener {
            ejecutarAccion("Activar circuito")
        }

        findViewById<Button>(R.id.btnDesactivarCircuito).setOnClickListener {
            ejecutarAccion("Desactivar circuito")
        }

        findViewById<Button>(R.id.btnVolverCircuito).setOnClickListener {
            finish()
        }
    }

    private fun ejecutarAccion(tipoAccion: String) {
        val idUnico = dbReference.push().key ?: return

        val fechaHoy = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val horaActual = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        // Usamos únicamente la variable nombreUsuario para que guarde solo el nombre (ej. inari)
        val registro = mapOf(
            "titulo" to tipoAccion,
            "fecha" to fechaHoy,
            "hora" to horaActual,
            "usuario" to nombreUsuario,
            "accion" to tipoAccion,
            "distancia" to "Sin dato"
        )

        dbReference.child(idUnico).setValue(registro)
            .addOnSuccessListener {
                Toast.makeText(this, "$tipoAccion registrado por $nombreUsuario", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Error al guardar: ${error.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }
}