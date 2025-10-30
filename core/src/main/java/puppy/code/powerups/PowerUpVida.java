package puppy.code.powerups;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Nave4;

public class PowerUpVida extends PowerUp {

    public PowerUpVida(float x, float y, Texture textura) {
        super(x, y, textura, 2.0f);
    }

    @Override
    public void aplicarEfecto(Nave4 nave) {
        nave.setVidas(nave.getVidas() + 1);
    }

    @Override
    public String getNombre() { return "Vida Extra"; }

    @Override
    public int getDuracion() { return 0; } // Efecto instantáneo

    @Override
    public float getMultiplicador() { return 1.0f; }
}
