package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import java.util.HashMap;

public class ResourceManager {
    private static ResourceManager instance;
    private HashMap<String, Texture> textures;

    private ResourceManager() {
        textures = new HashMap<>();
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public Texture getTexture(String textureName) {
        if (!textures.containsKey(textureName)) {
            loadTexture(textureName);
        }
        return textures.get(textureName);
    }

    private void loadTexture(String textureName) {
        try {
            String path = getTexturePath(textureName);
            Texture texture = new Texture(Gdx.files.internal(path));
            textures.put(textureName, texture);
        } catch (Exception e) {
            Gdx.app.error("ResourceManager", "Error loading texture: " + textureName);
        }
    }

    private String getTexturePath(String textureName) {
        // Mapeo de nombres lógicos a archivos
        switch (textureName) {
            case "nave": return "MainShip3.png";
            case "bala": return "Rocket2.png";
            case "asteroide": return "aGreyMedium4.png";
            case "powerup_vida": return "powerup_vida.png";
            case "powerup_velocidad": return "powerup_velocidad.png";
            case "powerup_invencibilidad": return "powerup_invencibilidad.png";
            case "powerup_puntos": return "powerup_puntos.png";
            default: return textureName;
        }
    }

    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
    }
}
