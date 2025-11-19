package puppy.code.powerups.factory;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.powerups.*;

public class PowerUpFactoryFacil implements PowerUpFactory {

    private final Texture[] texturas;

    public PowerUpFactoryFacil(Texture[] texturas) {
        this.texturas = texturas;
    }

    @Override
    public PowerUp crearVida(float x, float y) {
        return new PowerUpVida(x, y, texturas[0]);
    }

    @Override
    public PowerUp crearVelocidad(float x, float y) {
        return new PowerUpVelocidad(x, y, texturas[1]);
    }

    @Override
    public PowerUp crearInvencibilidad(float x, float y) {
        return new PowerUpInvencibilidad(x, y, texturas[2]);
    }

    @Override
    public PowerUp crearMultiplicador(float x, float y) {
        return new PowerUpMultiplicadorPuntos(x, y, texturas[3]);
    }
}

