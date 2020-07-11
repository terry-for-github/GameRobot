/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Goods.Armor;

import java.util.HashMap;

/**
 *
 * @author Administrator
 */

//装甲种类
public enum ArmorType {
    //头盔 胸甲 护腿 靴子 武器 饰品
    Helemet("Helemet"), Breastplate("Breastplate"), Shinguard("Shinguard"), Boots("Boots"), Weapons("Weapons"), Ornaments("Ornaments");
    private String name;

    ArmorType(String name) {
        this.name = name;
    }

}
