package puppy.code;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.math.MathUtils.random;

public class PantallaJuego implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Music gameMusic;
    private int score;
    private int ronda;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;

    private Nave4 nave;
    private ArrayList<Ball2> balls1 = new ArrayList<>();
    private ArrayList<Ball2> balls2 = new ArrayList<>();
    private ArrayList<Bullet> balas = new ArrayList<>();
    private PowerUpManager powerUpManager;

    // Estado del juego
    private enum EstadoJuego { JUGANDO, NIVEL_COMPLETADO }
    private EstadoJuego estadoActual = EstadoJuego.JUGANDO;
    private int tiempoPowerUps = 180; // 3 segundos para recoger power-ups

    public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,
                         int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        this.game = game;
        this.ronda = ronda;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1,0.5f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));

        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

        nave = new Nave4(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
            Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
            new Texture(Gdx.files.internal("Rocket2.png")),
            Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        nave.setVidas(vidas);

        Random r = new Random();
        for (int i = 0; i < cantAsteroides; i++) {
            Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
                50+r.nextInt((int)Gdx.graphics.getHeight()-50),
                20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4),
                new Texture(Gdx.files.internal("aGreyMedium4.png")));
            balls1.add(bb);
            balls2.add(bb);
        }

        powerUpManager = new PowerUpManager();
        powerUpManager.cargarTexturas();
    }

    public void dibujaEncabezado() {
        CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
        game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);

        if (powerUpManager.isMultiplicadorPuntosActivo()) {
            game.getFont().setColor(1, 1, 0, 1);
            game.getFont().draw(batch, "x" + powerUpManager.getMultiplicadorPuntos() + " PUNTOS!",
                Gdx.graphics.getWidth()/2-100, 60);
        }
        if (powerUpManager.isInvencibilidadActiva()) {
            game.getFont().setColor(0, 1, 1, 1);
            game.getFont().draw(batch, "INVENCIBLE!", Gdx.graphics.getWidth()/2-100, 90);
        }
        if (powerUpManager.isVelocidadBoostActiva()) {
            game.getFont().setColor(0, 1, 0, 1);
            game.getFont().draw(batch, "VELOCIDAD!", Gdx.graphics.getWidth()/2-100, 120);
        }
        game.getFont().setColor(1, 1, 1, 1);

        // Mostrar tiempo restante para power-ups
        if (estadoActual == EstadoJuego.NIVEL_COMPLETADO) {
            game.getFont().setColor(1, 1, 0, 1);
            game.getFont().draw(batch, "TIEMPO: " + (tiempoPowerUps/60 + 1),
                Gdx.graphics.getWidth()/2-100, 150);
            game.getFont().setColor(1, 1, 1, 1);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (estadoActual) {
            case JUGANDO:
                renderJugando();
                break;
            case NIVEL_COMPLETADO:
                renderNivelCompletado();
                break;
        }
    }

    private void renderJugando() {
        batch.begin();
        dibujaEncabezado();
        powerUpManager.actualizar(nave, this);
        powerUpManager.dibujar(batch);

        if (!nave.estaHerido()) {
            // Colisiones balas-asteroides
            for (int i = 0; i < balas.size(); i++) {
                Bullet b = balas.get(i);
                b.actualizar();
                for (int j = 0; j < balls1.size(); j++) {
                    if (b.checkCollision(balls1.get(j))) {
                        explosionSound.play();
                        balls1.remove(j);
                        balls2.remove(j);
                        j--;
                        score += (int)(10 * powerUpManager.getMultiplicadorPuntos());
                    }
                }

                if (b.isDestroyed()) {
                    balas.remove(b);
                    i--;
                }
            }

            // Actualizar asteroides
            for (Ball2 ball : balls1) {
                ball.update();
            }

            // Colisiones entre asteroides
            for (int i=0;i<balls1.size();i++) {
                Ball2 ball1 = balls1.get(i);
                for (int j=0;j<balls2.size();j++) {
                    Ball2 ball2 = balls2.get(j);
                    if (i<j) {
                        ball1.checkCollision(ball2);
                    }
                }
            }
        }

        // Dibujar elementos
        for (Bullet b : balas) {
            b.dibujar(batch);
        }
        nave.draw(batch, this);

        for (int i = 0; i < balls1.size(); i++) {
            Ball2 b=balls1.get(i);
            b.draw(batch);
            if (nave.checkCollision(b)) {
                balls1.remove(i);
                balls2.remove(i);
                i--;
            }
        }

        // Game over
        if (nave.estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            game.setScreen(new PantallaGameOver(game));
            dispose();
        }

        batch.end();

        // Verificar si se completó el nivel
        if (balls1.size() == 0 && estadoActual == EstadoJuego.JUGANDO) {
            completarNivel();
        }
    }

    private void renderNivelCompletado() {
        // Actualizar power-ups (se mueven y pueden ser recolectados)
        powerUpManager.actualizar(nave, this);

        batch.begin();

        // Dibujar fondo del juego normal
        dibujaEncabezado();
        powerUpManager.dibujar(batch);
        nave.draw(batch, this);

        // Overlay de nivel completado
        game.getFont().getData().setScale(2.5f);
        game.getFont().setColor(1, 1, 0, 1);
        game.getFont().draw(batch, "¡NIVEL " + ronda + " COMPLETADO!", 200, 500);
        game.getFont().getData().setScale(1.5f);
        game.getFont().draw(batch, "Recoge los power-ups!", 280, 450);
        game.getFont().draw(batch, "Tiempo: " + (tiempoPowerUps/60 + 1) + " segundos", 280, 400);
        game.getFont().draw(batch, "Presiona ESPACIO para saltar", 280, 350);
        game.getFont().setColor(1, 1, 1, 1);
        game.getFont().getData().setScale(2f);

        batch.end();

        // Controlar el tiempo y inputs
        tiempoPowerUps--;

        if (tiempoPowerUps <= 0 || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            avanzarAlSiguienteNivel();
        }
    }

    private void completarNivel() {
        Gdx.app.log("PantallaJuego", "Nivel completado - Generando power-ups");
        try {
            // Generar power-ups
            powerUpManager.generarPowerUps(2 + random.nextInt(2)); // 2-3 power-ups
            estadoActual = EstadoJuego.NIVEL_COMPLETADO;
            tiempoPowerUps = 180; // 3 segundos
            Gdx.app.log("PantallaJuego", "Power-ups generados, mostrando pantalla...");
        } catch (Exception e) {
            Gdx.app.error("PantallaJuego", "Error generando power-ups: " + e.getMessage(), e);
            // Si hay error, avanzar directamente
            avanzarAlSiguienteNivel();
        }
    }

    private void avanzarAlSiguienteNivel() {
        Gdx.app.log("PantallaJuego", "Avanzando al siguiente nivel...");
        int nuevosAsteroides = cantAsteroides + 2;

        game.setScreen(new PantallaJuego(
            game,
            ronda + 1,
            nave.getVidas(),
            score,
            velXAsteroides + 1,
            velYAsteroides + 1,
            nuevosAsteroides
        ));
        dispose();
    }

    public boolean agregarBala(Bullet bb) {
        return balas.add(bb);
    }

    @Override
    public void show() {
        gameMusic.play();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (explosionSound != null) explosionSound.dispose();
        if (gameMusic != null) gameMusic.dispose();
        if (powerUpManager != null) powerUpManager.limpiar();
    }
}
