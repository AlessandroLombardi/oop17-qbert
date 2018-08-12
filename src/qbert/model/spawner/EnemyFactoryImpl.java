package qbert.model.spawner;

import qbert.controller.Controller;
import qbert.model.characters.Character;
import qbert.model.characters.Snake;
import qbert.model.characters.Ugg;
import qbert.model.characters.Wrongway;
import qbert.model.components.sounds.CharacterSC;
import qbert.model.components.sounds.DownUpwardCharacterSC;
import qbert.model.components.sounds.PlayerSC;
import qbert.model.components.sounds.QbertSC;
import qbert.model.characters.Coily;
import qbert.model.characters.GreenBall;
import qbert.model.characters.Player;
import qbert.model.characters.Qbert;
import qbert.model.characters.RedBall;
import qbert.model.characters.SamAndSlick;
import qbert.model.utilities.Dimensions;
import qbert.model.utilities.Position2D;
import qbert.model.utilities.Sprites;
import qbert.model.components.graphics.CharacterGC;
import qbert.model.components.graphics.CoilyGC;
import qbert.model.components.graphics.CoilyGCImpl;
import qbert.model.components.graphics.DownwardCharacterGCImpl;
import qbert.model.components.graphics.LeftwardCharacterGC;
import qbert.model.components.graphics.PlayerGC;
import qbert.model.components.graphics.PlayerGCImpl;
import qbert.model.components.graphics.RightwardCharacterGC;

import java.util.Random;
/**
 * The implementation of {@link EnemyFactory} interface.
 */
public class EnemyFactoryImpl implements EnemyFactory {

    @Override
    public final Player createQbert(final float speed, final Controller controller) {
        final PlayerGC graphics = new PlayerGCImpl(Sprites.qbertFrontStanding, Sprites.qbertFrontMoving, Sprites.qbertBackStanding, Sprites.qbertBackMoving, 
                Sprites.qbertDead, Sprites.qbertOnDisk, new Position2D(Dimensions.getSpawningQBert()));
        final PlayerSC sounds = new QbertSC(controller);

        return new Qbert(Dimensions.getSpawningLogQBert(), speed, graphics, sounds);
    }

    @Override
    public final Snake createCoily(final float speed, final int standingTime, final Player qbert, final Controller controller) {
        final Position2D randomPos = this.getRandomPos();
        final Position2D logicalPos = this.getLogicalPos(randomPos);
        final CoilyGC graphics = new CoilyGCImpl(Sprites.purpleBallStanding, Sprites.coilyFrontStanding, 
                Sprites.coilyFrontMoving, Sprites.purpleBallMoving, Sprites.coilyBackStanding, Sprites.coilyBackMoving, randomPos);
        final CharacterSC sounds = new DownUpwardCharacterSC(controller);

        return new Coily(logicalPos, speed, graphics, sounds, standingTime, qbert);
    }

    @Override
    public final Character createRedBall(final float speed, final int standingTime, final Controller controller) {
        final Position2D randomPos = this.getRandomPos();
        final Position2D logicalPos = this.getLogicalPos(randomPos);
        final CharacterGC graphics = new DownwardCharacterGCImpl(Sprites.redBallStanding, Sprites.redBallMoving, randomPos);

        return new RedBall(logicalPos, speed, graphics, standingTime);
    }

    @Override
    public final Character createGreenBall(final float speed, final int standingTime, final Controller controller) {
        final Position2D randomPos = this.getRandomPos();
        final Position2D logicalPos = this.getLogicalPos(randomPos);
        final CharacterGC graphics = new DownwardCharacterGCImpl(Sprites.greenBallStanding, Sprites.greenBallMoving, randomPos);

        return new GreenBall(logicalPos, speed, graphics, standingTime);
    }

    @Override
    public final Character createSamAndSlick(final float speed, final int standingTime, final Controller controller) {
        final Position2D randomPos = this.getRandomPos();
        final Position2D logicalPos = this.getLogicalPos(randomPos);
        final CharacterGC graphics = randomPos == Dimensions.getSpawningPointLeft()
                ? new DownwardCharacterGCImpl(Sprites.slickStanding, Sprites.slickMoving, randomPos)
                : new DownwardCharacterGCImpl(Sprites.samStanding, Sprites.samMoving, randomPos);

        return new SamAndSlick(logicalPos, speed, graphics, standingTime);
    }

    @Override
    public final Character createWrongway(final float speed, final int standingTime, final Controller controller) {
        final Position2D logicalPos = new Position2D(0, 0);
        final RightwardCharacterGC graphics = new RightwardCharacterGC(Sprites.wrongwayStanding, Sprites.wrongwayMoving, new Position2D(-Dimensions.getSideSpawningHeight(), Dimensions.getBackgroundY() + Dimensions.getBackgroundHeight() - Dimensions.getCubeHeight()));

        return new Wrongway(logicalPos, speed, graphics, standingTime);
    }

    @Override
    public final Character createUgg(final float speed, final int standingTime, final Controller controller) {
        final Position2D logicalPos = new Position2D(26, 0);
        final LeftwardCharacterGC graphics = new LeftwardCharacterGC(Sprites.uggStanding, Sprites.uggMoving, new Position2D(Dimensions.getWindowWidth() + Dimensions.getSideSpawningHeight(), Dimensions.getBackgroundY() + Dimensions.getBackgroundHeight() - Dimensions.getCubeHeight()));

        return new Ugg(logicalPos, speed, graphics, standingTime);
    }

    private Position2D getRandomPos() {
        return new Random().nextInt(2) == 0 ? Dimensions.getSpawningPointLeft() : Dimensions.getSpawningPointRight();
    }

    private Position2D getLogicalPos(final Position2D randPos) {
        return randPos == Dimensions.getSpawningPointLeft() ? Dimensions.getSpawningLogPointLeft() : Dimensions.getSpawningLogPointRight();
    }

}
