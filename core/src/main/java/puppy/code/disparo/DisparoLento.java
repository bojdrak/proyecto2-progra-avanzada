package puppy.code.disparo;

import puppy.code.Bullet;
import puppy.code.Nave4;
import puppy.code.PantallaJuego;

public class DisparoLento implements EstrategiaDisparo {

    @Override
    public void disparar(Nave4 nave, PantallaJuego juego) {
        float x = nave.getX() + nave.getAncho() / 2f - 5;
        float y = nave.getY() + nave.getAlto() - 5;

        Bullet bala = new Bullet(
            x,
            y,
            0, 5,
            nave.getTexturaBala()
        );
        juego.agregarBala(bala);
        nave.getSoundBala().play();
    }
}
