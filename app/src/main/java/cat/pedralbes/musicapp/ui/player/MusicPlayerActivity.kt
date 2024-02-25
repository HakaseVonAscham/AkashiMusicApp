package cat.pedralbes.musicapp.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.pedralbes.myapp.R
import cat.pedralbes.musicapp.model.Song

class MusicPlayerActivity : AppCompatActivity() {
    private  val songsList: MutableList<Song> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)

    }
}