package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var etLoginRut: EditText
    private lateinit var etLoginContrasena: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnIrCrearCuenta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etLoginRut = findViewById(R.id.etLoginRut)
        etLoginContrasena = findViewById(R.id.etLoginContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnIrCrearCuenta = findViewById(R.id.btnIrCrearCuenta)

        btnIniciarSesion.setOnClickListener {
            val rut = etLoginRut.text.toString().trim()
            val contrasena = etLoginContrasena.text.toString().trim()

            if (rut.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val databaseRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(rut)

            databaseRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val passDB = snapshot.child("contrasena").value?.toString() ?: ""
                    val nombreDB = snapshot.child("nombre").value?.toString() ?: ""

                    if (passDB == contrasena) {
                        // Enviamos el RUT y el Nombre al Menú
                        val intent = Intent(this, MenuActivity::class.java).apply {
                            putExtra("RUT_USUARIO", rut)
                            putExtra("NOMBRE_USUARIO", nombreDB)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "El RUT no está registrado", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Error de conexión: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnIrCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}