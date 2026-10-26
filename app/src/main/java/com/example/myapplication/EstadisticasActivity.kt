package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EstadisticasActivity : AppCompatActivity() {

    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvActivaciones: TextView
    private lateinit var tvDesactivaciones: TextView
    private lateinit var tvObstaculos: TextView
    private lateinit var tvDistanciaTotal: TextView
    private lateinit var btnVolver: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estadisticas)

        tvNombreUsuario = findViewById(R.id.tvNombreUsuarioEstadisticas)
        tvActivaciones = findViewById(R.id.tvActivaciones)
        tvDesactivaciones = findViewById(R.id.tvDesactivaciones)
        tvObstaculos = findViewById(R.id.tvObstaculos)
        tvDistanciaTotal = findViewById(R.id.tvDistanciaTotal)
        btnVolver = findViewById(R.id.btnVolverEstadisticas)

        val nombre = intent.getStringExtra("NOMBRE_USUARIO") ?: "Usuario"
        tvNombreUsuario.text = nombre

        database = FirebaseDatabase.getInstance().getReference("Estadisticas")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    tvActivaciones.text = snapshot.child("activaciones").value.toString()
                    tvDesactivaciones.text = snapshot.child("desactivaciones").value.toString()
                    tvObstaculos.text = snapshot.child("obstaculos").value.toString()
                    tvDistanciaTotal.text = snapshot.child("distanciaTotal").value.toString() + " cm"
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        btnVolver.setOnClickListener { finish() }
    }
}