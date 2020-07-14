package entity.mobs;

import entity.CombatableEntity;
import goods.armor.Armor;
import goods.Good;
import interfaces.AttackSingle;
import interfaces.BuffAddable;
import interfaces.Equipable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 怪物类
 * @author Administrator
 */
public class Mob extends CombatableEntity implements Cloneable, AttackSingle, BuffAddable, Equipable {
    private Map<String, Good> equip;    // 怪物装备
    private Map<String, Good> goods;    // 怪物掉落物(请提前设置好数量)
    private long money;                 // 怪物掉落金币数量
    private long exp;                   // 击杀之后获取经验值
    
    /**
     * 
     * @param name 名字
     * @param ATK 攻击力
     * @param DEF 防御力
     * @param MATK 法术强度
     * @param MDEF 魔法抗性
     * @param ASPD 攻击速度
     * @param DSPD 施法速度
     * @param strength 力量
     * @param agile 敏捷
     * @param wisdom 智力
     */
    public Mob(String name, long ATK, long DEF, long MATK, long MDEF, double ASPD, double DSPD, long strength, long agile, long wisdom) {
        super(name, ATK, DEF, MATK, MDEF, ASPD, DSPD, strength, agile, wisdom);
        this.exp = 0;
        this.money = 0;
        this.goods = new HashMap<>();
    }

    /**
     * 穿上装备
     * @param armor 装备
     */
    @Override
    public void equip(Armor armor){
         equip.put(armor.getArmortype().toString(), armor);
    }
    
    /**
     * 怪物A攻击怪物B
     * @param A 怪物A
     * @param B 怪物B
     * @return 造成的伤害
     */
    @Override
    public int attackSingle(CombatableEntity A, CombatableEntity B) {
        return 0;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Mob mob = (Mob)super.clone();
        mob.setUuid(UUID.randomUUID());
        return mob;
    }

    /**
     * @return the money
     */
    public long getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(long money) {
        this.money = money;
    }

    /**
     * @return the exp
     */
    public long getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(long exp) {
        this.exp = exp;
    }

    /**
     * @return the goods
     */
    public Map<String, Good> getGoods() {
        return goods;
    }

    /**
     * @param goods the goods to set
     */
    public void setGoods(Map<String, Good> goods) {
        this.goods = goods;
    }
}
