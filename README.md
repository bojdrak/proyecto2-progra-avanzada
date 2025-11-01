# 🚀 Space Navigation
**Proyecto Java (libGDX)** – Juego tipo *arcade shooter* donde controlas una nave que debe esquivar enemigos y recolectar power-ups para alcanzar la mayor puntuación posible.

---

## 📦 Instalación

### Requisitos previos
Asegúrate de tener instalado:
- **Java JDK 17** o superior  
  (Verifica con `java -version`)
- **Gradle** (opcional, ya incluido si usas un IDE como IntelliJ o Eclipse)
- **Git** (opcional, si clonas el repositorio)

### Instalación paso a paso
1. **Descargar el proyecto**
   - Extrae el archivo `juego naves.zip` en una carpeta local.  
     Ejemplo:  
     ```
     C:\Users\<usuario>\Documents\SpaceNavigation
     ```

2. **Abrir el proyecto**
   - Abre la carpeta en tu IDE preferido (IntelliJ IDEA, Eclipse, VSCode con extensión Java).
   - Si el IDE pregunta, selecciona “Importar proyecto Gradle”.

3. **Verificar dependencias**
   - Gradle descargará automáticamente las librerías de **libGDX** al compilar.

4. **Ejecutar**
   - Desde tu IDE: busca la clase  
     ```
     puppy.code.lwjgl3.Lwjgl3Launcher
     ```
     y ejecútala como aplicación Java.  
   - O desde terminal:
     ```
     gradlew desktop:run
     ```
     *(en Linux/Mac usa `./gradlew desktop:run`)*

---

## 🎮 Cómo jugar

### Controles básicos
| Acción | Tecla |
|--------|--------|
| Mover arriba | ↑ o W |
| Mover abajo | ↓ o S |
| Mover izquierda | ← o A |
| Mover derecha | → o D |
| Dash (impulso rápido) | Doble tap en dirección |
| Disparo | Barra espaciadora |
| Pausa | ESC |
| Volver al menú | M |

### Objetivo
Sobrevive el mayor tiempo posible esquivando obstáculos y destruyendo enemigos.  
Recolecta **power-ups** para mejorar tus habilidades y acumula puntaje.

### Power-ups disponibles
- 💛 **Vida extra:** recupera una vida.  
- ⚡ **Velocidad:** incrementa temporalmente la rapidez de movimiento.  
- 🛡️ **Invencibilidad:** no recibes daño por un tiempo limitado.  
- ✴️ **Multiplicador de puntos:** aumenta las ganancias de puntaje.

### Fin del juego
- Si pierdes todas tus vidas, aparecerá la **pantalla de Game Over**.  
- Desde allí puedes volver al menú principal o reiniciar una nueva partida.

---

## 🧩 Estructura del proyecto

```
core/
 ├── puppy/code/
 │   ├── SpaceNavigation.java         # Clase principal del juego
 │   ├── entidades/                   # Nave, balas, enemigos
 │   ├── powerups/                    # Tipos de Power-Ups
 │   ├── pantallas/                   # Menu, Juego, GameOver, etc.
desktop/
 ├── puppy/code/lwjgl3/Lwjgl3Launcher.java  # Lanzador para PC
```

---

## 💾 Configuración adicional

- **Puntuación más alta:** se guarda automáticamente en memoria mientras dura la ejecución (en próximas versiones se persistirá).
- **Texturas y sonidos:** se cargan desde la carpeta `assets/`.

---

## 🧠 Créditos

Desarrollado por **[Tu nombre o equipo]**  
Basado en proyecto inicial de **libGDX Space Navigation**  
Licencia: uso educativo y libre distribución.
