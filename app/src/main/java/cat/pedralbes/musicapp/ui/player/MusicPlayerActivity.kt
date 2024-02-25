package cat.pedralbes.musicapp.ui.player

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cat.pedralbes.myapp.R
import cat.pedralbes.musicapp.model.Song

class MusicPlayerActivity : AppCompatActivity() {
    private  val songsList: MutableList<Song> = mutableListOf()
    private var int: Int = 0
    private var shouldReset: Boolean = false
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playerButton: ImageButton
    private lateinit var stopButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton

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

        val randomIndex = (0 until songsList.size).random()
        int = randomIndex

        playerButton = findViewById(R.id.PlayerButton)
        stopButton = findViewById(R.id.StopButton)
        prevButton = findViewById(R.id.PrevButton)
        nextButton = findViewById(R.id.NextButton)

        stopButton.setOnClickListener {
            stopSong()
        }

        prevButton.setOnClickListener{
            previousSong()
        }

        nextButton.setOnClickListener {
            nextSong()
        }

        playSong(songsList[randomIndex])
    }

    private fun stopSong(){
        mediaPlayer.stop()
        mediaPlayer.release()
        shouldReset = true
        playerButton.setOnClickListener {
            resumeSong()
        }
        playerButton.setBackgroundResource(R.drawable.play)
    }

    private fun pauseSong(){
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
            shouldReset = false
            playerButton.setOnClickListener {
                resumeSong()
            }
            playerButton.setBackgroundResource(R.drawable.play)
        }
    }

    private fun resumeSong(){
        if(!shouldReset){
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
                playerButton.setOnClickListener {
                    pauseSong()
                }
                playerButton.setBackgroundResource(R.drawable.pause)
            }
        } else{
            playSong(songsList[int])
// To Simplify
            playerButton.setOnClickListener {
                pauseSong()
            }
            playerButton.setBackgroundResource(R.drawable.pause)
        }
    }

    private fun previousSong() {
        if (int > 0) {
            int -= 1
        } else {
            int = songsList.size - 1
        }

        if(shouldReset){
            playSong(songsList[int])
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()
            playSong(songsList[int])
        }

    }

    private fun nextSong() {
        if (int < songsList.size - 1) {
            int += 1
        } else {
            int = 0
        }
        if(shouldReset){
            playSong(songsList[int])
        } else {
            mediaPlayer.stop()
            mediaPlayer.release()
            playSong(songsList[int])
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun playSong(song: Song) {
        mediaPlayer = MediaPlayer.create(this, song.audioResourceID)

        mediaPlayer.setOnCompletionListener {
            int += 1

            if(int > songsList.size){
                int = 0
            }

            playSong(songsList[int])
        }

        val coverBitmap = getCoverBitmap(song)
        if (coverBitmap != null) {
            val coverImageView: ImageView = findViewById(R.id.Cover)
            coverImageView.setImageBitmap(coverBitmap)
        } else {
            // Si no hay portada, puedes establecer una imagen predeterminada aquí
        }

        playerButton.setOnClickListener {
            pauseSong()
        }
        playerButton.setBackgroundResource(R.drawable.pause)

        mediaPlayer.start()
    }

    private fun getCoverBitmap(song: Song): Bitmap? {
        val mmr = MediaMetadataRetriever()
        try {
            mmr.setDataSource(applicationContext, Uri.parse("android.resource://${packageName}/${song.audioResourceID}"))
            val rawArt = mmr.embeddedPicture
            if (rawArt != null) {
                return BitmapFactory.decodeByteArray(rawArt, 0, rawArt.size)
            }
        } catch (e: Exception) {
            Log.e("MusicPlayerActivity", "Error al obtener la portada del archivo de audio: ${e.message}")
        } finally {
            mmr.release()
        }
        return null
    }
}