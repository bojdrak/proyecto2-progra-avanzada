package puppy.code.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.Nave4;
import puppy.code.EntidadJuego;

public abstract class PowerUp implements EntidadJuego{
    protected Sprite spr;
    protected boolean activo;
    protected float x, y;
    protected float velocidadCaida;

    public PowerUp(float x, float y, Texture textura, float velocidadCaida) {
        this.spr = new Sprite(textura);
        this.spr.setPosition(x, y);
        this.spr.setSize(30, 30);
        this.x = x;
        this.y = y;
        this.velocidadCaida = velocidadCaida;
        this.activo = true;
    }

    // Métodos abstractos
    public abstract void aplicarEfecto(Nave4 nave);
    public abstract String getNombre();
    public abstract int getDuracion();
    public abstract float getMultiplicador();

    @Override
    public void actualizar() {
        if (!activo) return;
        y -= velocidadCaida;
        spr.setPosition(x, y);
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (activo) {
            spr.draw(batch);
        }
    }

    public boolean estaActivo() {
        return activo;
    }

    // SOLO UNA DEFINICIÓN de getArea()
    @Override
    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public void desactivar() {
        this.activo = false;
    }
}
