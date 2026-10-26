package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuarioMenu: TextView
    private lateinit var btnManipularCircuito: Button
    private lateinit var btnVerAcciones: Button
    private lateinit var btnVerEstadisticas: Button
    private lateinit var btnVerPerfil: Button
    private lateinit var btnCerrarSesion: Button
    private var rutUsuario: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Enlazamos con los ID exactos de tu XML
        tvNombreUsuarioMenu = findViewById(R.id.tvNombreUsuarioMenu)
        btnManipularCircuito = findViewById(R.id.btnManipularCircuito)
        btnVerAcciones = findViewById(R.id.btnVerAcciones)
        btnVerEstadisticas = findViewById(R.id.btnVerEstadisticas)
        btnVerPerfil = findViewById(R.id.btnVerPerfil)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        // Recogemos los datos que envió el Login
        rutUsuario = intent.getStringExtra("RUT_USUARIO")
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"

        tvNombreUsuarioMenu.text = "Bienvenido, $nombreUsuario"

        // 1. Botón Ver Perfil (Le pasa el RUT)
        btnVerPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            intent.putExtra("RUT_USUARIO", rutUsuario)
            startActivity(intent)
        }

        // 2. Botón Ver Acciones
        btnVerAcciones.setOnClickListener {
            val intent = Intent(this, AccionesActivity::class.java)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario)
            startActivity(intent)
        }

        // 3. Botón Manipular Circuito (¡Agregado el envío del nombre de usuario!)
        btnManipularCircuito.setOnClickListener {
            val intent = Intent(this, CircuitosActivity::class.java)
            intent.putExtra("RUT_USUARIO", rutUsuario)
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario) // <-- Esto faltaba para que llegue el nombre real
            startActivity(intent)
        }

        // 4. Botón Ver Estadísticas
        btnVerEstadisticas.setOnClickListener {
            val intent = Intent(this, EstadisticasActivity::class.java)
            intent.putExtra("RUT_USUARIO", rutUsuario)
            startActivity(intent)
        }

        // 5. Botón Cerrar Sesión (Regresa al Login y limpia la memoria)
        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}