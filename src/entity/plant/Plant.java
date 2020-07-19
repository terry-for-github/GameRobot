package entity.plant;

import entity.player.Player;
import entity.SlaughterableEntity;
import interfaces.Growable;
import interfaces.Harvestable;
import interfaces.Hungerable;
import static gamerobot.GameRobot.goods;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 植物
 *
 * @author Administrator
 */
public class Plant extends SlaughterableEntity implements Growable, Harvestable, Hungerable, Cloneable {

    /**
     *
     * @param name 名字
     * @param maxHP 血量上限
     * @param times 可收获次数
     * @param maxAge 最大成长度
     */
    public Plant(String name, long maxHP, int times, int maxAge) {
        super(name, maxHP, times, maxAge);
    }

    /**
     *
     * @return @throws CloneNotSupportedException
     */
    @Override
    public Plant clone() throws CloneNotSupportedException {
        Plant plant = (Plant) super.clone();
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
     * 玩家收获作物
     *
     * @param player 玩家
     */
    @Override
    public void harvest(Player player) {
        for (Map.Entry<String, Integer> entry : this.getGoods().entrySet()) {
            try {
                player.playerAddGood(goods.get(entry.getKey()).clone(), entry.getValue());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Plant.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * 饥饿掉血
     */
    @Override
    public void hunger() {
        if (this.getHP() > 0) {
            this.setHP(this.getHP() - 1);
        }
    }
}
