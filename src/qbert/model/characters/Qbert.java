package qbert.model.characters;

import qbert.model.components.MapComponent;
import qbert.model.states.CharacterState;
import qbert.model.states.QbertStandingState;
import qbert.model.utilities.Position2D;
import qbert.view.DownUpwardCharacterGC;
import qbert.view.PlayerGC;

/**
 * This class models the main character of the game controlled by the user.
 */
public class Qbert extends CharacterImpl implements Player {

    private final PlayerGC graphics;

    /**
     * @param startPos the first {@link Position2D} of the {@link Character} in the map
     * @param speed the {@link Character} movement speed
     * @param graphics the {@link Character}'s {@link CharacterGraphicComponent}
     */
    public Qbert(final Position2D startPos, final float speed, final PlayerGC graphics) {
        super(startPos, speed, graphics);
        this.graphics = graphics;
        this.setCurrentState(this.getStandingState());
    }

    @Override
    public final CharacterState getStandingState() {
        return new QbertStandingState(this);
    }

    @Override
    public final void land(final MapComponent map) {
        map.step(this.getNextPosition());
    }

    @Override
    public final PlayerGC getPlayerGraphicComponent() {
        return this.graphics;
    }

    @Override
    public final DownUpwardCharacterGC getDownUpwardGraphicComponent() {
        return this.graphics;
    }
}
