package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceNavigation extends Game {
    private String nombreJuego = "Space Navigation";
    private SpriteBatch batch;
    private BitmapFont font;

    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2f);

        // Inicializar el GameManager
        GameManager.getInstance().setHighScore(0);

        Screen ss = new PantallaMenu(this);
        this.setScreen(ss);
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        // Limpiar Singletons
        GameManager.getInstance().dispose();
        ResourceManager.getInstance().dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    // Los métodos de high score ahora se delegan al GameManager
    public int getHighScore() {
        return GameManager.getInstance().getHighScore();
    }

    public void setHighScore(int highScore) {
        GameManager.getInstance().setHighScore(highScore);
    }
}
