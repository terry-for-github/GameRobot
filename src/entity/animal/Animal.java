/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Animal;

import Entity.Player.Player;
import Entities.SlaughterableEntity;
import Interfaces.Growable;
import Interfaces.Harvestable;
import Interfaces.Hungerable;
import static gamerobot.GameRobot.goods;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 *
 * @author Administrator
 */
//动物实体类
public class Animal extends SlaughterableEntity implements Growable, Harvestable, Hungerable, Cloneable {

    private Map<String, Integer> forage = new HashMap<>();//饲料

    public Animal(String name, long MAXHP, int times, int maxage) {
        super(name, MAXHP, times, maxage);
    }

    @Override
    public Animal clone() {
        Animal plant = null;
        try {
            plant = (Animal) super.clone();
            plant.setUuid(UUID.randomUUID());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return plant;
    }

    @Override
    public void Grow() {
        this.setAge(this.getAge()+100);
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
     * @return the forage
     */
}
