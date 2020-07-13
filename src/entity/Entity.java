package entity;

import java.util.UUID;

/**
 * 实体
 * @author Administrator
 */
public abstract class Entity {
    private String name;    // 名称
    private long MAXHP;     // 最大血量
    private long HP;        // 当前血量
    private UUID uuid;      // 唯一标识符
    
    /**
     * 
     * @param name 名字
     * @param MAXHP 血量上线
     */
    public Entity(String name, long MAXHP) {
        this.name = name;
        this.MAXHP = MAXHP;
        this.HP = MAXHP;
        this.uuid = UUID.randomUUID();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the MAXHP
     */
    public long getMAXHP() {
        return MAXHP;
    }

    /**
     * @param MAXHP the MAXHP to set
     */
    public void setMAXHP(long MAXHP) {
        this.MAXHP = MAXHP;
    }

    /**
     * @return the HP
     */
    public long getHP() {
        return HP;
    }

    /**
     * @param HP the HP to set
     */
    public void setHP(long HP) {
        this.HP = HP;
    }

    /**
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * @param uuid the uuid to set
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
