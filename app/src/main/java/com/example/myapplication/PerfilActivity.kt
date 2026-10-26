package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class PerfilActivity : AppCompatActivity() {

    private lateinit var tvRutPerfil: TextView
    private lateinit var tvNombrePerfil: TextView
    private lateinit var tvApellidoPerfil: TextView
    private lateinit var tvCorreoPerfil: TextView
    private lateinit var tvCargoPerfil: TextView
    private lateinit var btnVolverPerfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        tvRutPerfil = findViewById(R.id.tvRutPerfil)
        tvNombrePerfil = findViewById(R.id.tvNombrePerfil)
        tvApellidoPerfil = findViewById(R.id.tvApellidoPerfil)
        tvCorreoPerfil = findViewById(R.id.tvCorreoPerfil)
        tvCargoPerfil = findViewById(R.id.tvCargoPerfil)
        btnVolverPerfil = findViewById(R.id.btnVolverPerfil)

        // Recogemos el RUT que vino desde el Menú
        val rutUsuario = intent.getStringExtra("RUT_USUARIO")

        if (!rutUsuario.isNullOrEmpty()) {
            cargarDatosUsuario(rutUsuario)
        } else {
            Toast.makeText(this, "Error: No se recibió el RUT del usuario", Toast.LENGTH_SHORT).show()
        }

        btnVolverPerfil.setOnClickListener {
            finish() // Cierra la pantalla de perfil y regresa al menú
        }
    }

    private fun cargarDatosUsuario(rut: String) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(rut)

        databaseRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val rutDb = snapshot.child("rut").value?.toString() ?: rut
                val nombreDb = snapshot.child("nombre").value?.toString() ?: ""
                val apellidoDb = snapshot.child("apellido").value?.toString() ?: ""
                val correoDb = snapshot.child("correo").value?.toString() ?: ""
                val cargoDb = snapshot.child("cargo").value?.toString() ?: ""

                tvRutPerfil.text = "RUT: $rutDb"
                tvNombrePerfil.text = "Nombre: $nombreDb"
                tvApellidoPerfil.text = "Apellido: $apellidoDb"
                tvCorreoPerfil.text = "Correo: $correoDb"
                tvCargoPerfil.text = "Cargo: $cargoDb"
            } else {
                Toast.makeText(this, "No se encontraron los datos en Firebase", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error de conexión: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}