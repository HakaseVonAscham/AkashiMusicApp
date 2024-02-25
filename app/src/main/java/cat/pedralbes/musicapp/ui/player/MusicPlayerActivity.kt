package cat.pedralbes.musicapp.ui.player

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cat.pedralbes.myapp.R
import cat.pedralbes.musicapp.model.Song

class MusicPlayerActivity : AppCompatActivity() {
    private  val songsList: MutableList<Song> = mutableListOf()
    private var int: Int = 0
    private var mediaPlayer: MediaPlayer = TODO()
    private val playerButton: ImageButton = findViewById(R.id.PlayerButton)
    private val stopButton: ImageButton = findViewById(R.id.StopButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)

        // Songs load
        val song1 = Song("collide","nightcore",R.raw.collide)
        val song2 = Song("cry","nightcore",R.raw.cry_wolf)
        val song3 = Song("cry2","nightcore",R.raw.cry_wolf_2)
        val song4 = Song("floating","nightcore",R.raw.floating_in_the_wind)
        val song5 = Song("forget","nightcore",R.raw.forget_to_forget)
        val song6 = Song("girl","nightcore",R.raw.girl_with_dreams)
        val song7 = Song("glass","nightcore",R.raw.glass_heart)
        val song8 = Song("hello","nightcore",R.raw.hello_maria_ost)
        val song9 = Song("got","nightcore",R.raw.i_got_you)
        val song10 = Song("name","nightcore",R.raw.in_the_name_of_love)
        val song11 = Song("together","nightcore",R.raw.lets_get_together)
        val song12 = Song("make","nightcore",R.raw.make_me_move)
        val song13 = Song("miracles","nightcore",R.raw.miracles)
        val song14 = Song("ride","nightcore",R.raw.ride)
        val song15 = Song("wide","nightcore",R.raw.wide_awake)

        // Song to list
        songsList.add(song1)
        songsList.add(song2)
        songsList.add(song3)
        songsList.add(song4)
        songsList.add(song5)
        songsList.add(song6)
        songsList.add(song7)
        songsList.add(song8)
        songsList.add(song9)
        songsList.add(song10)
        songsList.add(song11)
        songsList.add(song12)
        songsList.add(song13)
        songsList.add(song14)
        songsList.add(song15)

        playSong(songsList[int])

        stopButton.setOnClickListener {
            stopSong()
        }

        playerButton.setOnClickListener {
            pauseSong()
        }
    }

    private fun stopSong(){
        mediaPlayer.stop()
        mediaPlayer.release()

        playerButton.setOnClickListener {
            resumeSong()
        }
        playerButton.setBackgroundResource(R.drawable.play)
    }

    private fun pauseSong(){
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()

            playerButton.setOnClickListener {
                resumeSong()
            }
            playerButton.setBackgroundResource(R.drawable.play)
        }
    }

    private fun resumeSong(){
        if(!mediaPlayer.isPlaying){
            mediaPlayer.start()

            playerButton.setOnClickListener {
                pauseSong()
            }
            playerButton.setBackgroundResource(R.drawable.pause)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar los recursos de los MediaPlayers (en este caso no se necesita)
    }

    private fun playSong(song: Song) {
        // Crea un MediaPlayer con el recurso de audio
        mediaPlayer = MediaPlayer.create(this, song.audioResourceID)

        // Configura un listener para manejar el ciclo de vida del MediaPlayer
        mediaPlayer.setOnCompletionListener {
            int += 1

            if(int > songsList.size){
                int = 0
            }

            playSong(songsList[int])
        }

        // Obtén el bitmap de la portada de los metadatos del archivo MP3
        val metaDataRetriever = MediaMetadataRetriever()
        metaDataRetriever.setDataSource(resources.openRawResourceFd(song.audioResourceID).fileDescriptor)
        val coverBytes = metaDataRetriever.embeddedPicture
        val coverBitmap = coverBytes?.let { BitmapFactory.decodeByteArray(it, 0, it.size) }


        val coverImageView: ImageView = findViewById(R.id.Cover)
        coverImageView.setImageBitmap(coverBitmap)
        // Aquí puedes usar el coverBitmap como desees, por ejemplo, para mostrarlo en una ImageView

        // Inicia la reproducción de la canción
        mediaPlayer.start()
    }
}