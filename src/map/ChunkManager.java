package map;

import java.util.Date;
import java.util.Random;
import map.forestboime.HillChunk;
import map.forestboime.LakeChunk;
import map.forestboime.MountainChunk;
import map.forestboime.OceanChunk;
import map.forestboime.PlainChunk;
import map.forestboime.SandChunk;

/**
 * 管理区块
 *
 * @author Administrator
 */
public class ChunkManager {

    public ChunkManager() {
    }

    /**
     * 生成一个int[x][y]二维数组表示地图， 每个位置按概率随机写入0/1 其中0表示非墙，1表示该位置有墙, 2表示沙滩
     *
     * @param x 地图x坐标上限（Excluded）
     * @param y 地图y坐标上限（Excluded）
     * @return 返回
     */
    public Chunk[][] randomFillMap(int x, int y) {
        Random random = new Random(new Date().getTime());
        Chunk[][] map = new Chunk[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map[i][j] = (random.nextInt(100) < 47) ? new OceanChunk(new Location(i, j)) : new PlainChunk(new Location(i, j));

            }
        }
        return map;
    }

    /**
     * 获得坐标(x ,y)周围八个位置墙的数量
     *
     * @param map 地图
     * @param x x坐标
     * @param y y坐标
     * @return 墙的数量
     */
    public int getSurroundingWalls(Chunk[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        if (!(map[i][j] instanceof OceanChunk)) {
                            wallCount++;
                        }
                    }
                }
            }
        }
        return wallCount;
    }

    /**
     * 获取周围更大范围平地的数量
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    public int getBigSurroundingPlain(Chunk[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 3; i <= x + 3; i++) {
            for (int j = y - 3; j <= y + 3; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        if (map[i][j] instanceof PlainChunk || map[i][j] instanceof HillChunk) {
                            wallCount++;
                        }
                    }
                }
            }
        }
        return wallCount;
    }

    /**
     * 获取周围更大范围山的数量
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    public int getBigSurroundingMountain(Chunk[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 2; i <= x + 2; i++) {
            for (int j = y - 2; j <= y + 2; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        if (map[i][j] instanceof HillChunk || map[i][j] instanceof MountainChunk) {
                            wallCount++;
                        }
                    }
                }
            }
        }
        return wallCount;
    }

    /**
     * 获取周围山的数量
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    public int getSurroundingMountain(Chunk[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        if (map[i][j] instanceof MountainChunk) {
                            wallCount++;
                        }
                    }
                }
            }
        }
        return wallCount;
    }

    /**
     * 获取周围山地的数量
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    public int getBigSurroundingHill(Chunk[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        if (map[i][j] instanceof HillChunk) {
                            wallCount++;
                        }
                    }
                }
            }
        }
        return wallCount;
    }

    /**
     * 获取周围平原数量
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    public int getSurroundingPlain(Chunk[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        if (map[i][j] instanceof PlainChunk) {
                            wallCount++;
                        }
                    }
                }
            }
        }
        return wallCount;
    }

    /**
     *
     * 获取某个方块周围的水的数量
     *
     * @param map
     * @param x
     * @param y
     * @return
     */
    public int getSurroundingWaters(Chunk[][] map, int x, int y) {
        int wallCount = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (i != x || j != y) {
                        if (map[i][j] instanceof OceanChunk) {
                            wallCount++;
                        }
                    }
                }
            }
        }
        return wallCount;
    }

    /**
     * 平滑山与山地
     *
     * @param map
     */
    public void smoothMountains(Chunk[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                if (map[i][j] instanceof MountainChunk && getSurroundingMountain(map, i, j) == 0) {
                    map[i][j] = new HillChunk(new Location(i, j));
                }
                int surroundingTiles = getSurroundingMountain(map, i, j);
                if (surroundingTiles > 4) {
                    map[i][j] = new MountainChunk(new Location(i, j));
                } else if (surroundingTiles < 4 && surroundingTiles > 0) {
                    map[i][j] = new HillChunk(new Location(i, j));
                }

            }
        }

    }

    /**
     * 平滑海与陆地的交界
     *
     * @param map
     */
    public void smoothMap(Chunk[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int surroundingTiles = getSurroundingWalls(map, i, j);
                if (surroundingTiles > 4) {
                    map[i][j] = new PlainChunk(new Location(i, j));
                } else if (surroundingTiles < 4) {
                    map[i][j] = new OceanChunk(new Location(i, j));
                }

            }
        }
    }

    //平滑平原与山丘的交界
    public void smoothPlainMap(Chunk[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] instanceof HillChunk && getBigSurroundingHill(map, i, j) == 0) {
                    map[i][j] = new PlainChunk(new Location(i, j));
                }
                int surroundingTiles = getBigSurroundingHill(map, i, j);
                if (surroundingTiles > 4) {
                    map[i][j] = new HillChunk(new Location(i, j));
                } else if (surroundingTiles < 4 && surroundingTiles > 0) {
                    map[i][j] = new PlainChunk(new Location(i, j));
                }

            }
        }
    }

    /**
     * 为整个地图制作沙滩
     *
     * @param map
     */
    public void createBeach(Chunk[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                canCreateSand(map, i, j);
            }
        }
        //清除过多的沙滩
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] instanceof SandChunk && getSurroundingWaters(map, i, j) == 0) {
                    map[i][j] = new PlainChunk(new Location(i, j));
                }
            }
        }
    }

    /**
     * 判断并且制作海滩
     *
     * @param map 地图
     * @param x x坐标
     * @param y y坐标
     */
    public void canCreateSand(Chunk[][] map, int x, int y) {
        if (map[x][y] instanceof PlainChunk) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                        if ((i != x || j != y)) {
                            if (map[i][j] instanceof OceanChunk && getSurroundingWaters(map, x, y) > 0) {
                                map[i][j] = new SandChunk(new Location(i, j));
                            }
                        }
                    }
                }
            }
        }
    }

    public int checkHasSand(Chunk[][] map, boolean[][] vis, int x, int y) {
        if (vis[x][y] || !(map[x][y] instanceof PlainChunk)) {
            return 0;
        }
        int count = 0;
        vis[x][y] = true;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    if (map[i][j] instanceof SandChunk) {
                        count++;
                    } else {
                        count += checkHasSand(map, vis, i, j);
                    }

                }
            }
        }
        return count;
    }

    public boolean surrondHasSandChunk(Chunk[][] map, int x, int y) {
        boolean[][] vis = new boolean[map.length][map[0].length];
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    count += checkHasSand(map, vis, i, j);
                }
            }
        }

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    //删除小于某个大小的湖泊
    public void deleteTooMuchLake(Chunk[][] map) {
        boolean[][] vis = new boolean[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int x = checkHasWater(map, vis, i, j);
                if ((x > 0 && x < 450)) {
                    deleteWater(map, i, j);
                }
            }
        }
    }

    public void deleteHill(Chunk[][] map, int x, int y) {
        if (!(map[x][y] instanceof PlainChunk)) {
            return;
        }
        map[x][y] = new HillChunk(new Location(x, y));
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    deleteHill(map, i, j);
                }
            }
        }
    }

    public void fillLake(Chunk[][] map, int x, int y) {
        if (!(map[x][y] instanceof PlainChunk)) {
            return;
        }
        map[x][y] = new LakeChunk(new Location(x, y));
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    fillLake(map, i, j);
                }
            }
        }
    }

    public void createLake(Chunk[][] map) {
        boolean[][] vis = new boolean[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int x = checkHasPlaint(map, vis, i, j);
                if (x > 0 && x < 10) {
                    if (!surrondHasSandChunk(map, i, j)) {
                        deleteHill(map, i, j);
                    }
                } else {
                    if (!surrondHasSandChunk(map, i, j)) {
                        fillLake(map, i, j);
                    }
                }

            }
        }
    }

    public int checkHasPlaint(Chunk[][] map, boolean[][] vis, int x, int y) {
        if (vis[x][y] || !(map[x][y] instanceof PlainChunk)) {
            return 0;
        }
        int count = 1;
        vis[x][y] = true;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    count += checkHasPlaint(map, vis, i, j);
                }
            }
        }
        return count;
    }

    public void getHill(Chunk[][] map) {
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (getBigSurroundingPlain(map, i, j) > 45) {
                    map[i][j] = new HillChunk(new Location(i, j));
                }
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] instanceof HillChunk) {
                    if (random.nextInt(100) < 46) {
                        map[i][j] = new PlainChunk(new Location(i, j));
                    }
                }

            }
        }

        for (int i = 0; i < 4; i++) {
            smoothPlainMap(map);
        }
    }

    public void getMountain(Chunk[][] map) {
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (getBigSurroundingMountain(map, i, j) == 24) {
                    map[i][j] = new MountainChunk(new Location(i, j));
                }
            }
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] instanceof MountainChunk) {
                    if (random.nextInt(100) < 43) {
                        map[i][j] = new HillChunk(new Location(i, j));
                    }
                }

            }
        }

        for (int i = 0; i < 4; i++) {
            smoothMountains(map);
        }
    }

    public void deleteWater(Chunk[][] map, int x, int y) {
        if (!(map[x][y] instanceof OceanChunk)) {
            return;
        }
        map[x][y] = new PlainChunk(new Location(x, y));
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    deleteWater(map, i, j);
                }
            }
        }
    }

    public void getRiver(Chunk[][] map) {
        int rivernumber = new Random().nextInt(4);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

            }
        }
    }

    //递归监测从某格开始水域大小
    public int checkHasWater(Chunk[][] map, boolean[][] vis, int x, int y) {
        if (vis[x][y] || !(map[x][y] instanceof OceanChunk)) {
            return 0;
        }
        int count = 1;
        vis[x][y] = true;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                    count += checkHasWater(map, vis, i, j);
                }
            }
        }
        return count;
    }

}
