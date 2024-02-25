package cat.pedralbes.musicapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cat.pedralbes.myapp.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenPlayer = findViewById<Button>(R.id.btnOpenPlayer)
        btnOpenPlayer.setOnClickListener { // Aquí puedes abrir la actividad del reproductor de música
            val intent = Intent(this@MainActivity, MusicPlayerActivity::class.java)
            startActivity(intent)
        }
    }
}