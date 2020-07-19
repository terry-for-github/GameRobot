package map;

/**
 * 位置
 * @author Administrator
 */
public class Location {
    private int x;  // x坐标
    private int y;  // y坐标
    private boolean hasBeenChecked=false;
   
    /**
     * 
     * @param x x坐标
     * @param y y坐标
     */
    public Location(int x,int y){
        this.x = x;
        this.y = y;
    }

    /**
     * 获得x
     * @return 
     */
    public int getX() {
        return x;
    }

    /**
     * 设置x
     * @param x 
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * 获得y
     * @return 
     */
    public int getY() {
        return y;
    }

    /**
     * 设置y
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
    }
}
