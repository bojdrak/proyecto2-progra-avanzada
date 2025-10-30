package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface EntidadJuego {
    void actualizar();
    void dibujar(SpriteBatch batch);
    Rectangle getArea();
}
