/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Plant;

import Entity.Player.Player;
import Entities.SlaughterableEntity;
import Interfaces.Growable;
import Interfaces.Harvestable;
import Interfaces.Hungerable;
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
    public void Grow() {
        this.setAge(this.getAge() + 100);
    }

    @Override
    public void Harvest(Player player) {
        for (Map.Entry<String, Integer> entry : this.getGoods().entrySet()) {
            player.PlayerAddGood(goods.get(entry.getKey()).clone(), entry.getValue());
        }

    }

    @Override
    public void Hunger() {
        if (this.getHP() > 0) {
            this.setHP(this.getHP() - 1);
        }
    }

}
