package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public class PantallaMenu extends PantallaBase {

    public PantallaMenu(SpaceNavigation game) {
        super(game);
    }

    @Override
    protected void actualizarLogica(float delta) {
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaTutorial(game);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }

    @Override
    protected void dibujar(float delta) {

        game.getBatch().setProjectionMatrix(camera.combined);

        game.getFont().draw(game.getBatch(),
            "Bienvenido a Space Navigation !",
            140, 400);
        game.getFont().draw(game.getBatch(),
            "Un juego de destruccion de asteroides.",
            100, 300);

    }
}
