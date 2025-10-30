package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Nave4 {

    private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    // Nuevas variables para movimiento snappy y dash
    private float velocidadBase = 8f;
    private float velocidadDash = 20f;
    private boolean enDash = false;
    private int tiempoDash = 5; // Duración del dash en frames
    private int tiempoDashActual = 0;
    private boolean iframesDash = false;
    private int tiempoIframesDash = 5; // Iframes durante el dash

    // Control de doble tap
    private long ultimoTapIzquierda = 0;
    private long ultimoTapDerecha = 0;
    private long ultimoTapArriba = 0;
    private long ultimoTapAbajo = 0;
    private final long tiempoDobleTap = 200; // medido en ms

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setBounds(x, y, 45, 45);
    }

    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x = spr.getX();
        float y = spr.getY();

        if (!herido && !enDash) {
            procesarInputMovimiento();
        }

        if (!herido) {
            // Aplicar movimiento
            if (enDash) {
                ejecutarDash();
            } else {
                aplicarMovimientoNormal();
            }

            // Mantener dentro de bordes
            mantenerEnBordes();
            spr.draw(batch);
        } else {
            // Efecto de herido
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        // Disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5,
                spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5,
                spr.getY() + spr.getHeight() - 5, 0, 3, txBala);
            juego.agregarBala(bala);
            soundBala.play();
        }
    }

    private void procesarInputMovimiento() {
        // Reiniciar velocidad cada frame para movimiento snappy
        xVel = 0;
        yVel = 0;

        // Movimiento horizontal
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xVel = -velocidadBase;
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                long ahora = System.currentTimeMillis();
                if (ahora - ultimoTapIzquierda < tiempoDobleTap) {
                    iniciarDash(-velocidadDash, 0);
                }
                ultimoTapIzquierda = ahora;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xVel = velocidadBase;
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                long ahora = System.currentTimeMillis();
                if (ahora - ultimoTapDerecha < tiempoDobleTap) {
                    iniciarDash(velocidadDash, 0);
                }
                ultimoTapDerecha = ahora;
            }
        }

        // Movimiento vertical
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yVel = -velocidadBase;
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                long ahora = System.currentTimeMillis();
                if (ahora - ultimoTapAbajo < tiempoDobleTap) {
                    iniciarDash(0, -velocidadDash);
                }
                ultimoTapAbajo = ahora;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yVel = velocidadBase;
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                long ahora = System.currentTimeMillis();
                if (ahora - ultimoTapArriba < tiempoDobleTap) {
                    iniciarDash(0, velocidadDash);
                }
                ultimoTapArriba = ahora;
            }
        }

        // Dash diagonal (combinaciones)
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            long ahora = System.currentTimeMillis();
            if (ahora - ultimoTapIzquierda < tiempoDobleTap || ahora - ultimoTapArriba < tiempoDobleTap) {
                iniciarDash(-velocidadDash, velocidadDash);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            long ahora = System.currentTimeMillis();
            if (ahora - ultimoTapDerecha < tiempoDobleTap || ahora - ultimoTapArriba < tiempoDobleTap) {
                iniciarDash(velocidadDash, velocidadDash);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            long ahora = System.currentTimeMillis();
            if (ahora - ultimoTapIzquierda < tiempoDobleTap || ahora - ultimoTapAbajo < tiempoDobleTap) {
                iniciarDash(-velocidadDash, -velocidadDash);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            long ahora = System.currentTimeMillis();
            if (ahora - ultimoTapDerecha < tiempoDobleTap || ahora - ultimoTapAbajo < tiempoDobleTap) {
                iniciarDash(velocidadDash, -velocidadDash);
            }
        }
    }

    private void aplicarMovimientoNormal() {
        spr.setPosition(spr.getX() + xVel, spr.getY() + yVel);
    }

    private void iniciarDash(float dashXVel, float dashYVel) {
        enDash = true;
        tiempoDashActual = tiempoDash;
        xVel = dashXVel;
        yVel = dashYVel;
        iframesDash = true;
    }

    private void ejecutarDash() {
        spr.setPosition(spr.getX() + xVel, spr.getY() + yVel);
        tiempoDashActual--;

        // Reducir iframes durante el dash
        if (tiempoDashActual <= tiempoDash - tiempoIframesDash) {
            iframesDash = false;
        }

        // Terminar dash
        if (tiempoDashActual <= 0) {
            enDash = false;
            iframesDash = false;
            xVel = 0;
            yVel = 0;
        }
    }

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    private void mantenerEnBordes() {
        float x = spr.getX();
        float y = spr.getY();

        if (x < 0) {
            spr.setX(0);
            if (enDash) {
                enDash = false;
                iframesDash = false;
            }
        }
        if (x + spr.getWidth() > Gdx.graphics.getWidth()) {
            spr.setX(Gdx.graphics.getWidth() - spr.getWidth());
            if (enDash) {
                enDash = false;
                iframesDash = false;
            }
        }
        if (y < 0) {
            spr.setY(0);
            if (enDash) {
                enDash = false;
                iframesDash = false;
            }
        }
        if (y + spr.getHeight() > Gdx.graphics.getHeight()) {
            spr.setY(Gdx.graphics.getHeight() - spr.getHeight());
            if (enDash) {
                enDash = false;
                iframesDash = false;
            }
        }
    }

    public boolean checkCollision(Ball2 b) {
        // No colisionar durante iframes del dash o cuando está herido
        if (herido || iframesDash) {
            return false;
        }

        if (b.getArea().overlaps(spr.getBoundingRectangle())) {
            // Rebote solo si no está en dash
            if (!enDash) {
                if (xVel == 0) xVel += b.getXSpeed() / 2;
                if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int) xVel / 2);
                xVel = -xVel;
                b.setXSpeed(-b.getXSpeed());

                if (yVel == 0) yVel += b.getySpeed() / 2;
                if (b.getySpeed() == 0) b.setySpeed(b.getySpeed() + (int) yVel / 2);
                yVel = -yVel;
                b.setySpeed(-b.getySpeed());
            }

            // Actualizar vidas y herir (solo si no está en dash)
            if (!enDash) {
                vidas--;
                herido = true;
                tiempoHerido = tiempoHeridoMax;
                sonidoHerido.play();
                if (vidas <= 0)
                    destruida = true;
            }
            return true;
        }
        return false;
    }

    public boolean estaDestruido() {
        return !herido && destruida;
    }

    public boolean estaHerido() {
        return herido;
    }

    public boolean estaEnDash() {
        return enDash;
    }

    public boolean tieneIframes() {
        return iframesDash || herido;
    }

    public int getVidas() { return vidas; }
    public int getX() { return (int) spr.getX(); }
    public int getY() { return (int) spr.getY(); }
    public void setVidas(int vidas2) { vidas = vidas2; }
}
