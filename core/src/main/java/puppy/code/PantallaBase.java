package puppy.code;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public abstract class PantallaBase implements Screen {

    protected SpaceNavigation game;
    protected OrthographicCamera camera;
    protected SpriteBatch batch;

    public PantallaBase(SpaceNavigation game) {
        this.game = game;
        this.batch = game.getBatch();
        this.camera = new OrthographicCamera();
        configurarCamara();
    }

    // ==========================
    //  TEMPLATE METHOD
    // ==========================
    @Override
    public final void render(float delta) {
        limpiarPantalla();
        actualizarCamara();

        actualizarLogica(delta);      // Paso 1 (hook)
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        dibujar(delta);               // Paso 2 (hook)
        batch.end();
    }

    // Pasos fijos
    protected void limpiarPantalla() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
    }

    protected void actualizarCamara() {
        camera.update();
    }

    // Hook opcional para cambiar tamaño de cámara
    protected void configurarCamara() {
        camera.setToOrtho(false, 1200, 800);
    }

    // Pasos variables que definen las subclases
    protected abstract void actualizarLogica(float delta);
    protected abstract void dibujar(float delta);

    // Métodos de Screen por defecto vacíos (para no obligar a reimplementarlos)
    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
