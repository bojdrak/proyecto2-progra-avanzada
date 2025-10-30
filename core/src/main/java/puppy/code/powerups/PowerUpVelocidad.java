package puppy.code.powerups;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Nave4;

public class PowerUpVelocidad extends PowerUp {

    public PowerUpVelocidad(float x, float y, Texture textura) {
        super(x, y, textura, 2.0f);
    }

    @Override
    public void aplicarEfecto(Nave4 nave) {
        // El efecto se maneja en PantallaJuego
    }

    @Override
    public String getNombre() { return "Velocidad Boost"; }

    @Override
    public int getDuracion() { return 300; } // 5 segundos

    @Override
    public float getMultiplicador() { return 1.0f; }
}
