package map;

import entity.Entity;

/**
 *
 * @author Administrator
 */
public class Space {

    private Location location;//在区块中的位置
    private Entity entity;//实体 如果没有即为Null

    public Space(Location location) {
        this.location = location;
    }

    public Space(Location location, Entity entity) {
        this.location = location;
        this.entity = entity;

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public boolean hasEntity() {
        if (null != entity) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasBlock() {
        if (hasEntity() && this.entity instanceof Block) {
            return true;
        } else {
            return false;
        }
    }
}
