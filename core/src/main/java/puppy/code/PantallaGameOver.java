package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PantallaGameOver extends PantallaBase {

    public PantallaGameOver(SpaceNavigation game) {
        super(game);
    }

    @Override
    protected void actualizarLogica(float delta) {
        // Si el jugador toca la pantalla o presiona cualquier tecla, reinicia el juego
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new PantallaJuego(game, 1, 3, 0, 1, 1, 10));
            dispose();
        }
    }

    @Override
    protected void dibujar(float delta) {
        // NO begin(), NO end()
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getFont().draw(
            game.getBatch(),
            "Game Over !!! ",
            120, 400, 400, 1, true
        );

        game.getFont().draw(
            game.getBatch(),
            "Presiona cualquier boton para reiniciar ...",
            100, 300
        );
    }


    @Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
