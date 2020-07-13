package entity.plant;

import entity.player.Player;
import entity.SlaughterableEntity;
import interfaces.Growable;
import interfaces.Harvestable;
import interfaces.Hungerable;
import static gamerobot.GameRobot.goods;
import java.util.Map;
import java.util.UUID;


/**
 *
 * @author Administrator
 */

//植物
public class Plant extends SlaughterableEntity implements Growable, Harvestable, Hungerable, Cloneable {

    public Plant(String name, long MAXHP, int times, int maxage) {
        super(name, MAXHP, times, maxage);
    }

    @Override
    public Plant clone() {
        Plant plant = null;
        try {
            plant = (Plant) super.clone();
            plant.setUuid(UUID.randomUUID());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return plant;
    }

    @Override
    public void grow() {
        this.setAge(this.getAge() + 100);
    }

    @Override
    public void harvest(Player player) {
        for (Map.Entry<String, Integer> entry : this.getGoods().entrySet()) {
            player.PlayerAddGood(goods.get(entry.getKey()).clone(), entry.getValue());
        }

    }

    @Override
    public void hunger() {
        if (this.getHP() > 0) {
            this.setHP(this.getHP() - 1);
        }
    }

}
