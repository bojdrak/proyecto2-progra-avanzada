package puppy.code.powerups.factory;

import puppy.code.powerups.PowerUp;

public interface PowerUpFactory {
    PowerUp crearVida(float x, float y);
    PowerUp crearVelocidad(float x, float y);
    PowerUp crearInvencibilidad(float x, float y);
    PowerUp crearMultiplicador(float x, float y);
}
