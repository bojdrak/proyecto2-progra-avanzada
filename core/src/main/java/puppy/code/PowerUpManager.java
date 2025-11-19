package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.powerups.*;
import java.util.ArrayList;
import java.util.Random;
import puppy.code.powerups.factory.*;

public class PowerUpManager {
    private ArrayList<PowerUp> powerUps;
    private Texture[] texturasPowerUps;
    private Random random;
    private PowerUpFactory factory;

    // Efectos temporales activos
    private boolean invencibilidadActiva = false;
    private int tiempoInvencibilidad = 0;
    private boolean velocidadBoostActiva = false;
    private int tiempoVelocidadBoost = 0;
    private boolean multiplicadorPuntosActivo = false;
    private int tiempoMultiplicadorPuntos = 0;
    private float multiplicadorPuntos = 1.0f;

    public PowerUpManager() {
        powerUps = new ArrayList<>();
        random = new Random();
        texturasPowerUps = new Texture[4];
        factory = new PowerUpFactoryFacil(texturasPowerUps);
    }
    public void setFactory(PowerUpFactory factory) {
        this.factory = factory;
    }

    public void cargarTexturas() {
        try {
            texturasPowerUps[0] = new Texture(Gdx.files.internal("powerup_vida.png"));
            texturasPowerUps[1] = new Texture(Gdx.files.internal("powerup_velocidad.png"));
            texturasPowerUps[2] = new Texture(Gdx.files.internal("powerup_invencibilidad.png"));
            texturasPowerUps[3] = new Texture(Gdx.files.internal("powerup_puntos.png"));
        } catch (Exception e) {
            Gdx.app.error("PowerUpManager", "Error cargando texturas de power-ups: " + e.getMessage());
            // Cargar texturas placeholder o manejar el error
            crearTexturasPlaceholder();
        }
    }

    private void crearTexturasPlaceholder() {
        for (int i = 0; i < texturasPowerUps.length; i++) {
            if (texturasPowerUps[i] == null) {
                // Crear una textura simple de 32x32 pixels con colores diferentes
                Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);

                switch (i) {
                    case 0: pixmap.setColor(Color.RED); break;       // Vida - Rojo
                    case 1: pixmap.setColor(Color.GREEN); break;     // Velocidad - Verde
                    case 2: pixmap.setColor(Color.CYAN); break;      // Invencibilidad - Cian
                    case 3: pixmap.setColor(Color.YELLOW); break;    // Puntos - Amarillo
                }

                pixmap.fillCircle(16, 16, 15);
                texturasPowerUps[i] = new Texture(pixmap);
                pixmap.dispose();

                Gdx.app.log("PowerUpManager", "Textura placeholder creada para índice: " + i);
            }
        }
    }

    public void generarPowerUps(int cantidad) {
        Gdx.app.log("PowerUpManager", "Generando " + cantidad + " power-ups");
        for (int i = 0; i < cantidad; i++) {
            float x = 100 + random.nextInt(800); // Posición X dentro de la pantalla
            float y = 400 + random.nextInt(300); // Posición Y en la parte superior

            int tipo = random.nextInt(4);
            PowerUp powerUp = crearPowerUp(tipo, x, y);

            if (powerUp != null) {
                powerUps.add(powerUp);
                Gdx.app.log("PowerUpManager", "Power-up tipo " + tipo + " creado en (" + x + "," + y + ")");
            }
        }
        Gdx.app.log("PowerUpManager", "Total power-ups activos: " + powerUps.size());
    }

    private PowerUp crearPowerUp(int tipo, float x, float y) {
        if (factory == null) {
            return null;
        }

        switch (tipo) {
            case 0:
                return factory.crearVida(x, y);
            case 1:
                return factory.crearVelocidad(x, y);
            case 2:
                return factory.crearInvencibilidad(x, y);
            case 3:
                return factory.crearMultiplicador(x, y);
            default:
                return null;
        }
    }

    public void actualizar(Nave4 nave, PantallaJuego juego) {
        // Actualizar power-ups existentes
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp p = powerUps.get(i);
            p.actualizar();

            if (p.estaActivo() && p.getArea().overlaps(nave.getArea())) {
                aplicarPowerUp(p, nave);
                powerUps.remove(i);
                i--;
                continue;
            }

            // Eliminar inactivos
            if (!p.estaActivo()) {
                powerUps.remove(i);
                i--;
            }
        }

        // Actualizar efectos temporales
        actualizarEfectosTemporales();
    }

    private void aplicarPowerUp(PowerUp powerUp, Nave4 nave) {
        powerUp.aplicarEfecto(nave);

        if (powerUp instanceof PowerUpVida) {
            // Efecto instantáneo, ya se aplicó en aplicarEfecto()
        } else if (powerUp instanceof PowerUpInvencibilidad) {
            invencibilidadActiva = true;
            tiempoInvencibilidad = powerUp.getDuracion();
        } else if (powerUp instanceof PowerUpVelocidad) {
            velocidadBoostActiva = true;
            tiempoVelocidadBoost = powerUp.getDuracion();
        } else if (powerUp instanceof PowerUpMultiplicadorPuntos) {
            multiplicadorPuntosActivo = true;
            tiempoMultiplicadorPuntos = powerUp.getDuracion();
            multiplicadorPuntos = powerUp.getMultiplicador();
        }
    }

    private void actualizarEfectosTemporales() {
        if (invencibilidadActiva && --tiempoInvencibilidad <= 0) {
            invencibilidadActiva = false;
        }
        if (velocidadBoostActiva && --tiempoVelocidadBoost <= 0) {
            velocidadBoostActiva = false;
        }
        if (multiplicadorPuntosActivo && --tiempoMultiplicadorPuntos <= 0) {
            multiplicadorPuntosActivo = false;
            multiplicadorPuntos = 1.0f;
        }
    }

    public void dibujar(SpriteBatch batch) {
        for (PowerUp p : powerUps) {
            p.dibujar(batch);
        }
    }

    // Getters para los efectos activos
    public boolean isInvencibilidadActiva() { return invencibilidadActiva; }
    public boolean isVelocidadBoostActiva() { return velocidadBoostActiva; }
    public boolean isMultiplicadorPuntosActivo() { return multiplicadorPuntosActivo; }
    public float getMultiplicadorPuntos() { return multiplicadorPuntos; }

    public void limpiar() {
        powerUps.clear();
        for (Texture tex : texturasPowerUps) {
            if (tex != null) tex.dispose();
        }
    }
}
