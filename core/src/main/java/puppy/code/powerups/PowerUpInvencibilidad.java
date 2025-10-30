package puppy.code.powerups;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Nave4;

public class PowerUpInvencibilidad extends PowerUp {

    public PowerUpInvencibilidad(float x, float y, Texture textura) {
        super(x, y, textura, 2.0f);
    }

    @Override
    public void aplicarEfecto(Nave4 nave) {
        // El efecto se maneja en PantallaJuego
    }

    @Override
    public String getNombre() { return "Invencibilidad"; }

    @Override
    public int getDuracion() { return 180; } // 3 segundos

    @Override
    public float getMultiplicador() { return 1.0f; }
}
