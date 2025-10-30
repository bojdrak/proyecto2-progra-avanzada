package puppy.code.powerups;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Nave4;

public class PowerUpMultiplicadorPuntos extends PowerUp {

    public PowerUpMultiplicadorPuntos(float x, float y, Texture textura) {
        super(x, y, textura, 2.0f);
    }

    @Override
    public void aplicarEfecto(Nave4 nave) {
        // El efecto se maneja en PantallaJuego
    }

    @Override
    public String getNombre() { return "Doble Puntos"; }

    @Override
    public int getDuracion() { return 240; } // 4 segundos

    @Override
    public float getMultiplicador() { return 2.0f; }
}
