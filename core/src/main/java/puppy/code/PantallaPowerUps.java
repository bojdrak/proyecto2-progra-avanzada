package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class PantallaPowerUps extends PantallaBase {

    private PowerUpManager powerUpManager;
    private Nave4 nave;
    private int siguienteRonda;
    private int vidas;
    private int score;
    private int velXAsteroides;
    private int velYAsteroides;
    private int cantAsteroides;
    private int tiempoRestante = 300; // 5 segundos para elegir (60 fps aprox.)

    public PantallaPowerUps(SpaceNavigation game,
                            PowerUpManager powerUpManager,
                            int siguienteRonda,
                            int vidas,
                            int score,
                            int velXAsteroides,
                            int velYAsteroides,
                            int cantAsteroides) {

        super(game);

        this.powerUpManager = powerUpManager;
        this.siguienteRonda = siguienteRonda;
        this.vidas = vidas;
        this.score = score;
        this.velXAsteroides = velXAsteroides;
        this.velYAsteroides = velYAsteroides;
        this.cantAsteroides = cantAsteroides;

        // Crear nave temporal para recolectar power-ups
        Texture txNave = new Texture(Gdx.files.internal("MainShip3.png"));
        Texture txBala = new Texture(Gdx.files.internal("Rocket2.png"));
        nave = new Nave4(600, 100, txNave, null, txBala, null);
        nave.setVidas(vidas);
    }

    @Override
    protected void actualizarLogica(float delta) {
        // Actualizar power-ups y nave
        powerUpManager.actualizar(nave, null);

        // Control de tiempo
        tiempoRestante--;
        if (tiempoRestante <= 0) {
            avanzarAlSiguienteNivel();
        }

        // Permitir saltar con ESPACIO
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            avanzarAlSiguienteNivel();
        }
    }

    @Override
    protected void dibujar(float delta) {
        game.getBatch().setProjectionMatrix(camera.combined);

        // Título e instrucciones
        game.getFont().draw(game.getBatch(),
            "¡Nivel Completado!", 450, 750);
        game.getFont().draw(game.getBatch(),
            "Recoge un power-up antes de que el tiempo se acabe!",
            350, 700);
        game.getFont().draw(game.getBatch(),
            "Tiempo: " + (tiempoRestante / 60) + " segundos",
            500, 650);

        // Dibujar power-ups
        powerUpManager.dibujar(game.getBatch());

        // Dibujar nave (sin disparar en esta pantalla)
        nave.draw(game.getBatch(), new PantallaJuego(game, 1, 3, 0, 1, 1, 10) {
            @Override
            public boolean agregarBala(Bullet bb) {
                return false; // No disparar en esta pantalla
            }
        });

        // Mostrar power-ups activos actuales
        if (powerUpManager.isInvencibilidadActiva()) {
            game.getFont().draw(game.getBatch(),
                "INVENCIBILIDAD ACTIVA", 450, 100);
        }
        if (powerUpManager.isVelocidadBoostActiva()) {
            game.getFont().draw(game.getBatch(),
                "VELOCIDAD BOOST ACTIVA", 450, 80);
        }
        if (powerUpManager.isMultiplicadorPuntosActivo()) {
            game.getFont().draw(game.getBatch(),
                "MULTIPLICADOR x" + powerUpManager.getMultiplicadorPuntos() + " ACTIVO",
                450, 60);
        }

    }

    private void avanzarAlSiguienteNivel() {
        // Crear nueva pantalla de juego con los mismos parámetros y power-ups aplicados
        PantallaJuego siguienteNivel = new PantallaJuego(
            game,
            siguienteRonda,
            vidas,
            score,
            velXAsteroides,
            velYAsteroides,
            cantAsteroides
        );

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
