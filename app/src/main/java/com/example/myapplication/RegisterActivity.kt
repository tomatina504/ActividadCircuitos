package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var etRut: EditText
    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etCorreo: EditText
    private lateinit var spCargo: Spinner
    private lateinit var etContrasena: EditText
    private lateinit var etRepetirContrasena: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Enlazar vistas
        etRut = findViewById(R.id.etRegisterRut)
        etNombre = findViewById(R.id.etRegisterNombre)
        etApellido = findViewById(R.id.etRegisterApellido)
        etCorreo = findViewById(R.id.etRegisterCorreo)
        spCargo = findViewById(R.id.spRegisterCargo)
        etContrasena = findViewById(R.id.etRegisterContrasena)
        etRepetirContrasena = findViewById(R.id.etRegisterRepetirContrasena)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnVolver = findViewById(R.id.btnVolverLogin)

        // Cargar opciones en el Spinner de Cargo
        val opcionesCargo = arrayOf("Seleccione Cargo", "Operador", "Administrador", "Supervisor")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, opcionesCargo)
        spCargo.adapter = adapter

        // Acción del botón Registrar en Firebase Realtime Database
        btnRegistrar.setOnClickListener {
            val rut = etRut.text.toString().trim()
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val cargo = spCargo.selectedItem.toString()
            val pass = etContrasena.text.toString().trim()
            val passRepeat = etRepetirContrasena.text.toString().trim()

            if (rut.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != passRepeat) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar usuario en Firebase bajo el nodo "Usuarios" usando el RUT limpiando puntos o guion
            val cleanRut = rut.replace(".", "").replace("-", "")
            val database = FirebaseDatabase.getInstance().getReference("Usuarios")

            val usuario = Usuario(cleanRut, nombre, apellido, correo, cargo, pass)

            database.child(cleanRut).setValue(usuario).addOnSuccessListener {
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                finish() // Cierra la pantalla y vuelve al Login
            }.addOnFailureListener {
                Toast.makeText(this, "Error al registrar usuario: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }
}