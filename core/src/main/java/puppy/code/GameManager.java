package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import java.util.HashMap;

public class GameManager {
    // Instancia única del Singleton
    private static GameManager instance;

    // Datos globales del juego
    private int highScore;
    private HashMap<String, Sound> soundEffects;
    private HashMap<String, Music> backgroundMusic;
    private boolean soundEnabled;
    private boolean musicEnabled;

    // Constructor privado
    private GameManager() {
        highScore = 0;
        soundEffects = new HashMap<>();
        backgroundMusic = new HashMap<>();
        soundEnabled = true;
        musicEnabled = true;
        loadResources();
    }

    // Método público para obtener la instancia
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private void loadResources() {
        // Cargar recursos de audio una sola vez
        try {
            soundEffects.put("explosion", Gdx.audio.newSound(Gdx.files.internal("explosion.ogg")));
            soundEffects.put("hurt", Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")));
            soundEffects.put("pop", Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));

            backgroundMusic.put("game", Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")));
            backgroundMusic.get("game").setLooping(true);

        } catch (Exception e) {
            Gdx.app.error("GameManager", "Error loading resources: " + e.getMessage());
        }
    }

    // Métodos de acceso para high score
    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            // Podríamos guardar automáticamente en preferences aquí
        }
    }

    // Métodos para gestión de audio
    public void playSound(String soundName) {
        if (soundEnabled && soundEffects.containsKey(soundName)) {
            soundEffects.get(soundName).play();
        }
    }

    public void playMusic(String musicName) {
        if (musicEnabled && backgroundMusic.containsKey(musicName)) {
            backgroundMusic.get(musicName).play();
        }
    }

    public void stopMusic(String musicName) {
        if (backgroundMusic.containsKey(musicName)) {
            backgroundMusic.get(musicName).stop();
        }
    }

    // Getters y setters para configuración
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }

    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        if (!enabled) {
            for (Music music : backgroundMusic.values()) {
                music.stop();
            }
        }
    }

    // Limpieza de recursos
    public void dispose() {
        for (Sound sound : soundEffects.values()) {
            sound.dispose();
        }
        for (Music music : backgroundMusic.values()) {
            music.dispose();
        }
        soundEffects.clear();
        backgroundMusic.clear();
    }
}
