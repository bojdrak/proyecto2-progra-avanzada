package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Screen;

public class PantallaTutorial extends PantallaBase {

    private Texture texturaNave;
    private Texture texturaAsteroide;
    private Sprite spriteNave;
    private Sprite spriteAsteroide;

    public PantallaTutorial(SpaceNavigation game) {
        super(game);

        // Cargar assets del juego
        texturaNave = new Texture(Gdx.files.internal("MainShip3.png"));
        texturaAsteroide = new Texture(Gdx.files.internal("aGreyMedium4.png"));

        spriteNave = new Sprite(texturaNave);
        spriteAsteroide = new Sprite(texturaAsteroide);

        // Posicionar sprites para el tutorial
        spriteAsteroide.setPosition(200, 480);
        spriteNave.setPosition(200, 300);
    }

    @Override
    protected void dibujar(float delta) {
        game.getBatch().setProjectionMatrix(camera.combined);

        // Título
        game.getFont().draw(game.getBatch(), "Tutorial - Space Navigation", 400, 750);

        // Objetivo del juego
        game.getFont().draw(game.getBatch(), "Objetivo:", 100, 700);
        game.getFont().draw(game.getBatch(),
            "Destruir todos los asteroides en cada ronda para avanzar al siguiente",
            120, 650);
        game.getFont().draw(game.getBatch(), "nivel con mas dificultad", 120, 610);

        // Dibujar asteroide con explicación
        spriteAsteroide.draw(game.getBatch());
        game.getFont().draw(game.getBatch(), "Asteroides", 160, 460);
        game.getFont().draw(game.getBatch(), "- Destruyelos con tus balas", 400, 550);
        game.getFont().draw(game.getBatch(), "- +10 puntos por cada asteroide", 400, 500);
        game.getFont().draw(game.getBatch(), "- Se mueven y rebotan en los bordes", 400, 450);

        // Dibujar nave con explicación
        spriteNave.draw(game.getBatch());
        game.getFont().draw(game.getBatch(), "Tu Nave", 170, 280);
        game.getFont().draw(game.getBatch(), "- Tienes 3 vidas iniciales", 400, 350);
        game.getFont().draw(game.getBatch(), "- Pierdes 1 vida al chocar con asteroides", 400, 300);
        game.getFont().draw(game.getBatch(), "- Tienes inmunidad temporal tras ser herido", 400, 250);

        // Controles
        game.getFont().draw(game.getBatch(), "Controles:", 100, 200);
        game.getFont().draw(game.getBatch(),
            "Flechas: Mover nave -- Presionar dos veces: Dash",
            120, 150);
        game.getFont().draw(game.getBatch(), "Z y X: Disparar", 120, 110);

        // Instrucción para continuar
        game.getFont().draw(game.getBatch(),
            "Presiona cualquier tecla para comenzar...",
            650, 55);


    }

    @Override
    protected void actualizarLogica(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
            ss.resize(1200, 800);
            game.setScreen(ss);
            dispose();
        }
    }

    @Override
    public void dispose() {
        texturaNave.dispose();
        texturaAsteroide.dispose();
    }
}
