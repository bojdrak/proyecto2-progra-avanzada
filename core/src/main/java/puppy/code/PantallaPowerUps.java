package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class PantallaPowerUps implements Screen {

    private SpaceNavigation game;
    private OrthographicCamera camera;
    private PowerUpManager powerUpManager;
    private Nave4 nave;
    private int siguienteRonda;
    private int vidas;
    private int score;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private int tiempoRestante = 300; // 5 segundos para elegir

    public PantallaPowerUps(SpaceNavigation game, PowerUpManager powerUpManager,
                            int siguienteRonda, int vidas, int score,
                            int velXAsteroides, int velYAsteroides, int cantAsteroides) {
        this.game = game;
        this.powerUpManager = powerUpManager;
        this.siguienteRonda = siguienteRonda;
        this.vidas = vidas;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);

        // Crear nave temporal para recolectar power-ups
        Texture txNave = new Texture(Gdx.files.internal("MainShip3.png"));
        Texture txBala = new Texture(Gdx.files.internal("Rocket2.png"));
        nave = new Nave4(600, 100, txNave, null, txBala, null);
        nave.setVidas(vidas);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        SpriteBatch batch = game.getBatch();
        batch.setProjectionMatrix(camera.combined);

        // Actualizar power-ups y nave
        powerUpManager.actualizar(nave, null);

        // Dibujar todo
        batch.begin();

        // Título e instrucciones
        game.getFont().draw(batch, "¡Nivel Completado!", 450, 750);
        game.getFont().draw(batch, "Recoge un power-up antes de que el tiempo se acabe!", 350, 700);
        game.getFont().draw(batch, "Tiempo: " + (tiempoRestante / 60) + " segundos", 500, 650);

        // Dibujar power-ups
        powerUpManager.dibujar(batch);

        // Dibujar nave
        nave.draw(batch, new PantallaJuego(game, 1, 3, 0, 1, 1, 10) {
            @Override
            public boolean agregarBala(Bullet bb) {
                return false; // No disparar en esta pantalla
            }
        });

        // Mostrar power-ups activos actuales
        if (powerUpManager.isInvencibilidadActiva()) {
            game.getFont().draw(batch, "INVENCIBILIDAD ACTIVA", 450, 100);
        }
        if (powerUpManager.isVelocidadBoostActiva()) {
            game.getFont().draw(batch, "VELOCIDAD BOOST ACTIVA", 450, 80);
        }
        if (powerUpManager.isMultiplicadorPuntosActivo()) {
            game.getFont().draw(batch, "MULTIPLICADOR x" + powerUpManager.getMultiplicadorPuntos() + " ACTIVO", 450, 60);
        }

        batch.end();

        // Control de tiempo
        tiempoRestante--;
        if (tiempoRestante <= 0) {
            avanzarAlSiguienteNivel();
        }

        // Permitir saltear con ESPACIO
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            avanzarAlSiguienteNivel();
        }
    }

    private void avanzarAlSiguienteNivel() {
        // Pasar los efectos activos al siguiente nivel
        PowerUpManager nuevoPowerUpManager = new PowerUpManager();

        // Crear nueva pantalla de juego con los power-ups activos
        PantallaJuego siguienteNivel = new PantallaJuego(game, siguienteRonda, vidas, score,
            velXAsteroides, velYAsteroides, cantAsteroides);

        // Transferir efectos activos (necesitarías agregar setters en PowerUpManager)
        // nuevoPowerUpManager.transferirEfectos(this.powerUpManager);

        siguienteNivel.resize(1200, 800);
        game.setScreen(siguienteNivel);
        dispose();
    }

    @Override
    public void show() {}

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
        // Limpiar recursos
        if (nave != null) {
            // Limpiar texturas de la nave si es necesario
        }
    }
}
