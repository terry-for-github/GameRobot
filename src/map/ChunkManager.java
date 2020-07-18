package map;

import java.util.Date;
import java.util.Random;

/**
 * 管理区块
 * @author Administrator
 */
public class ChunkManager {
    private ChunkManager() {}

    /**
     * 生成一个int[x][y]二维数组表示地图， 每个位置按概率随机写入0/1
     * 其中0表示非墙，1表示该位置有墙, 2表示沙滩
     * @param x 地图x坐标上限（Excluded）
     * @param y 地图y坐标上限（Excluded）
     * @return 返回
     */
    public static int[][] randomFillMap(int x, int y) {
        Random random = new Random(new Date().getTime());
        int[][] map = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = (random.nextInt(100) < 48) ? 1 : 0;
            }
        }
        return map;
    }

    /**
     * 获得坐标(x ,y)周围八个位置墙的数量
     * @param map 地图
     * @param x x坐标
     * @param y y坐标
     * @return 墙的数量
     */
    public static int getSurroundingWalls(int[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        wallCount += map[i][j];
                    }
                } else {
                    wallCount++;
                }
            }
        }
        return wallCount;
    }
    
    /**
     * 让地图更加平滑
     * @param map 
     */
    public static void smoothMap(int[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int surroundingTiles = getSurroundingWalls(map, i, j);
                if (surroundingTiles > 4) {
                    map[i][j] = 1;
                } else if (surroundingTiles < 4) {
                    map[i][j] = 0;
                }
            }
        }
    }

    /**
     * 判断并且制作海滩
     * @param map 地图
     * @param x x坐标
     * @param y y坐标
     */
    public static void getsandWalls(int[][] map, int x, int y) {
        if(map[x][y] == 1){
            return;
        }
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if ((i != x || j != y) && map[i][j] == 1) {
                        map[i][j]=2;
                    }
                }
            }
        }
    }
}
