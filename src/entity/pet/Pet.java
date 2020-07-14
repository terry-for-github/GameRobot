package entity.pet;

import entity.Entity;

/**
 * 宠物实体类（未开发）
 * @author Administrator
 */
public class Pet extends Entity{
    /**
     * 
     * @param name 名字
     * @param maxHP 血量上限
     */
    public Pet(String name, long maxHP) {
        super(name, maxHP);
    }
}
