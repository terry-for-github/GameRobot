package map;

import java.util.Date;
import java.util.Random;
import map.forestboime.HillChunk;
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

    //获取周围水域大小
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
     * 让地图更加平滑
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
     * 判断并且制作海滩
     *
     * @param map 地图
     * @param x x坐标
     * @param y y坐标
     */
    public void getsandWalls(Chunk[][] map, int x, int y) {
        if (map[x][y] instanceof PlainChunk) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && i < map.length && j >= 0 && j < map[i].length) {
                        if ((i != x || j != y) && map[i][j] instanceof OceanChunk) {
                            if (getSurroundingWaters(map, x, y) > 0) {
                                map[i][j] = new SandChunk(new Location(i, j));
                            }
                        }
                    }
                }
            }
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
                if (getBigSurroundingMountain(map, i, j) ==24) {
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
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[i].length; j++) {
//                if (!map[i][j].hasBeenChecked) {
//                    int x = checkHasWater(map, i, j);
//                    if (x > 100 && x < 500) {
//                        map[i][j] = new RiverChunk(new Location(i, j));
//                    }
//                }
//            }
//        }
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
