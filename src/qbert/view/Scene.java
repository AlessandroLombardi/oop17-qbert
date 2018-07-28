package qbert.view;

import java.awt.Graphics;
import java.awt.event.KeyListener;

/**
 * The interface for a common application scene.
 */
public interface Scene extends KeyListener {

    /**
     * A method used when the scene is changed to restore the process of keystroke events.
     */
    void focus();

    /**
     * The method used to render the scene.
     */
    void render();

    /**
     * @param g the {@link Graphics} for the scene
     */
    void draw(Graphics g);
}
