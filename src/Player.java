import java.awt.event.KeyEvent;

public class Player {
    public SceneObject controlledObject;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public Player(SceneObject obj) {
        this.controlledObject = obj;
    }

    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
        }
    }

    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
        }
    }

    public void update() {
        float speed = 0.02f;

        if (leftPressed) controlledObject.yaw(-speed);
        if (rightPressed) controlledObject.yaw(speed);
        if (upPressed) controlledObject.pitch(-speed);
        if (downPressed) controlledObject.pitch(speed);
    }
}
