package entity.animal;

import entity.player.Player;
import entity.SlaughterableEntity;
import interfaces.Growable;
import interfaces.Harvestable;
import interfaces.Hungerable;
import static gamerobot.GameRobot.goods;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 动物实体类
 *
 * @author Administrator
 */
public class Animal extends SlaughterableEntity implements Growable, Harvestable, Hungerable, Cloneable {

    private Map<String, Integer> forage = new HashMap<>();  // 饲料

    /**
     *
     * @param name 名字
     * @param maxHP 血量上限
     * @param times 可以收获的次数
     * @param maxAge 最大成长度
     */
    public Animal(String name, long maxHP, int times, int maxAge) {
        super(name, maxHP, times, maxAge);
    }

    /**
     * @return the forage
     */
    public Map<String, Integer> getForage() {
        return forage;
    }

    /**
     * @param forage the forage to set
     */
    public void setForage(Map<String, Integer> forage) {
        this.forage = forage;
    }

    /**
     *
     * @return @throws CloneNotSupportedException
     */
    @Override
    public Animal clone() throws CloneNotSupportedException {
        Animal plant = (Animal) super.clone();
        plant.setUuid(UUID.randomUUID());
        return plant;
    }

    /**
     * 生长
     */
    @Override
    public void grow() {
        this.setAge(this.getAge() + 100);
    }

    /**
     * 被玩家收获
     *
     * @param player 玩家
     */
    @Override
    public void harvest(Player player) {
        for (Map.Entry<String, Integer> entry : this.getGoods().entrySet()) {
            try {
                player.playerAddGood(goods.get(entry.getKey()).clone(), entry.getValue());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 当饥饿时会发生的事件
     */
    @Override
    public void hunger() {
        if (this.getHP() > 0) {
            this.setHP(this.getHP() - 1);
        }
    }
}
