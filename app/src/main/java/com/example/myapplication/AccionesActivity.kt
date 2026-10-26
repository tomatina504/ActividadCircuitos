package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccionesActivity : AppCompatActivity() {

    // 1. Declaramos todas las variables que se usarán en la pantalla
    private lateinit var tvNombreUsuario: TextView
    private lateinit var rvAcciones: RecyclerView
    private lateinit var btnVolver: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acciones)

        // 2. Enlazamos cada elemento con su respectivo ID del XML
        tvNombreUsuario = findViewById(R.id.tvNombreUsuarioAcciones)
        rvAcciones = findViewById(R.id.rvAcciones)
        btnVolver = findViewById(R.id.btnVolverAcciones)

        val nombre = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        tvNombreUsuario.text = nombre

        rvAcciones.layoutManager = LinearLayoutManager(this)

        // 3. Cargamos los datos en tiempo real desde Firebase
        database = FirebaseDatabase.getInstance().getReference("Acciones")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaAcciones = mutableListOf<AccionModel>()
                for (data in snapshot.children) {
                    val accion = data.getValue(AccionModel::class.java)
                    if (accion != null) {
                        listaAcciones.add(accion)
                    }
                }
                rvAcciones.adapter = AccionesAdapter(listaAcciones)
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores de conexión opcional
            }
        })

        btnVolver.setOnClickListener {
            finish()
        }
    }
}