package qbert.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class Level {

    private Map<Integer, Map<Integer, Tile>> tiles;
    private List<Character> gameCharacters;
    private int mapHeight = 7;
    private BufferedImage background;

    public Level() {
        this.createLevelTiles();
        this.importBackground();
    }

    public Tile getTile(final int x, final int y) {
        return tiles.get(x).get(y);
    }
    
    public BufferedImage getBackground() {
        return this.background;
    }

    private void createLevelTiles() {
        tiles = new HashMap<>();
        for (int i = 1; i <= this.mapHeight; i++) {
            Map<Integer, Tile> tmpMap = new HashMap<>();
            for (int j = 1; j <= this.mapHeight; j++) {
                if (j <= i) {
                    tmpMap.put(j, new Tile(10, 10));
                }
                tiles.put(i, tmpMap);
            }
        }
    }
    
    private void importBackground() {
        final URL mapSpriteUrl = this.getClass().getResource("/background.png");
        try {
            this.background = ImageIO.read(mapSpriteUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
