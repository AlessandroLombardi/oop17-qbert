package qbert.view.animations;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import qbert.model.utilities.Position2D;

/**
 * A JUnit class to test basic animations.
 */
public class TestSimpleAnimation {

    private final Random random = new Random();
    private static final float SPEED = 7;

    private List<Position2D> returnArrayOfRandomPosition2D(final int size) {
        final List<Position2D> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(this.returnRandomPosition2D());
        }
        return result;
    }

    private Position2D returnRandomPosition2D() {
        return new Position2D(this.random.nextInt(10), this.random.nextInt(10));
    }

    /**
     * Test {@link DisplaceAnimation}.
     */
    @Test
    public void testDisplaceAnimation() {
        final int size = this.random.nextInt(10) + 1;
        Position2D source = returnRandomPosition2D();
        final List<Position2D> target = returnArrayOfRandomPosition2D(size);
        for (final Position2D t : target) {
            final Animation anim = new DisplaceAnimation(source, t);
            while (!anim.hasFinished()) {
                source = anim.updateAnimation(TestSimpleAnimation.SPEED);
            }
        }
        assertEquals(source, target.get(size - 1));
    }

    /**
     * Test {@link Down} and {@link Up}.
     */
    @Test
    public void testMoveDownAnimation() {
        Position2D source = returnRandomPosition2D();
        final int size = this.random.nextInt(5) + 10;

        for (int i = 0; i < size; i++) {
            final Position2D targetDown = new Position2D(source.getX(), this.random.nextInt(10) + source.getY());
            final Position2D targetUp = new Position2D(source.getX(), source.getY() - this.random.nextInt(10));
            Animation anim = new MoveAnimation.Down(source, targetDown);

            while (!anim.hasFinished()) {
                source = anim.updateAnimation(TestSimpleAnimation.SPEED);
            }
            assertEquals(source, targetDown);

            anim = new MoveAnimation.Up(source, targetUp);
            while (!anim.hasFinished()) {
                source = anim.updateAnimation(TestSimpleAnimation.SPEED);
            }
            assertEquals(source, targetUp);
        }
    }

    /**
     * Test {@link ArcClockwise} and {@link ArcCounterClockwise}.
     */
    @Test
    public void testMoveAnimation() {
        Position2D source = returnRandomPosition2D();
        final int size = this.random.nextInt(5) + 10;

        for (int i = 0; i < size; i++) {
            final Position2D target = new Position2D(source.getX() + this.random.nextInt(20), source.getY());
            final Position2D targetCounter = new Position2D(source.getX() - this.random.nextInt(20), source.getY());
            Animation anim = new MoveAnimation.ArcClockwise(source, target);

            while (!anim.hasFinished()) {
                source = anim.updateAnimation(TestSimpleAnimation.SPEED);
            }
            assertEquals(source, target);

            anim = new MoveAnimation.ArcCounterclockwise(source, targetCounter);
            while (!anim.hasFinished()) {
                source = anim.updateAnimation(TestSimpleAnimation.SPEED);
            }
            assertEquals(source, targetCounter);
        }
    }

    /**
     * Test {@link StandingAnimation}.
     */
    @Test
    public void testStandingAnimation() {
        final Position2D source = returnRandomPosition2D();
        final Animation anim = new StandingAnimation(source);
        assertTrue(anim.hasFinished());
    }
}