package map;

import java.util.Date;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class ChunkManager {

    public static int[][] RandomFillMap(int x, int y) {
        Random random = new Random(new Date().getTime());
        int[][] map = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = (random.nextInt(101) < 50) ? 1 : 0;
            }
        }
        return map;
    }

    public static int GetSurroundingWalls(int[][] map, int posX, int posY) {
        int wallCount = 0;

        for (int i = posX - 1; i <= posX + 1; i++) {
            for (int j = posY - 1; j <= posY + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != posX || j != posY) {
                        wallCount += map[i][j];
                    }
                } else {
                    wallCount++;
                }
            }
        }
        return wallCount;
    }

    public static void SmoothMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int surroundingTiles = GetSurroundingWalls(map, i, j);
                if (surroundingTiles > 4) {
                    map[i][j] = 1;
                } else if (surroundingTiles < 4) {
                    map[i][j] = 0;
                }
            }
        }
    }

}
