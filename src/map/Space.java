package map;

import entity.Entity;

/**
 * 空间
 * @author Administrator
 */
public class Space {
    private Location location;  //在区块中的位置
    private Entity entity;      //实体 如果没有即为Null

    /**
     * 
     * @param location 位置
     */
    public Space(Location location) {
        this.location = location;
    }

    /**
     * 
     * @param location 位置
     * @param entity 实体
     */
    public Space(Location location, Entity entity) {
        this(location);
        this.entity = entity;

    }

    /**
     * 获得位置
     * @return 
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 设置位置
     * @param location 
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 获得实体
     * @return 
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * 设置实体
     * @param entity 
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * 该空间是否有实体
     * @return 是否有实体 
     */
    public boolean hasEntity() {
        return entity != null;
    }

    /**
     * 该空间是否有方块（方块也是实体的一种）
     * @return 是否有方块
     */
    public boolean hasBlock() {
        return entity != null && this.entity instanceof Block;
    }
}
