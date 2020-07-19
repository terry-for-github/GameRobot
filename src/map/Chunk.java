package map;

/**
 * 区块
 * @author Administrator
 */
public abstract class Chunk {
    private Location location;          // 区块位置
    private String boime;               // 群系
    private Space[][] spaces;           // 区块的每个空间
    public final int SPACE_SIZE = 11;   // 区块大小
    public boolean hasBeenChecked=false;//初始化时用来判断湖泊是否被遍历

    /**
     * 
     * @param location 位置
     */
    public Chunk(Location location) {
        this.location = location;
        this.spaces = new Space[SPACE_SIZE][SPACE_SIZE];
        for (int i = 0; i < SPACE_SIZE; i++) {
            for (int j = 0; j < SPACE_SIZE; j++) {
                spaces[i][j] = new Space(new Location(i, j));
            }
        }
    }

    public boolean isHasBeenChecked() {
        return hasBeenChecked;
    }

    public void setHasBeenChecked(boolean hasBeenChecked) {
        this.hasBeenChecked = hasBeenChecked;
    }

    /**
     * 获得Spaces[x][y]
     * @param x x坐标
     * @param y y坐标
     * @return Spaces[x][y]
     */
    public Space getSpace(int x, int y) {
        return spaces[x][y];
    }

    /**
     * 获得区块的位置
     * @return 区块位置
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 设置区块位置
     * @param location 要被设置成的位置
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 获得空间数组
     * @return 整个区块的空间数组
     */
    public Space[][] getSpaces() {
        return spaces;
    }

    /**
     * 替换整个空间数组
     * @param spaces 
     */
    // TODO 这是为什么
    public void setSpaces(Space[][] spaces) {
        this.spaces = spaces;
    }

    /**
     * 设置实体
     */
    public abstract void setEntitys();

    /**
     * 获得生态系统
     * @return 
     */
    public String getBoime() {
        return boime;
    }

    /**
     * 设置生态系统
     * @param boime 要被设置成的生态系统
     */
    public void setBoime(String boime) {
        this.boime = boime;
    }

}
