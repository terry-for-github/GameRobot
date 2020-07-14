package entity;

import goods.armor.Armor;
import interfaces.AttackSingle;
import interfaces.Equipable;

/**
 * 可作战的实体
 * @author Administrator
 */
public abstract class CombatableEntity extends Entity implements Cloneable, AttackSingle, Equipable{
    private long ATK;       // 攻击力
    private long DEF;       // 防御力
    private long MATK;      // 法术强度
    private long MDEF;      // 魔法抗性
    private double ASPD;    // 攻击速度
    private double DSPD;    // 施法速度
    private long strength;  // 力量
    private long agile;     // 敏捷
    private long wisdom;    // 智力
    
    /**
     * 
     * @param name 名字
     */
    public CombatableEntity(String name) {
        this(name, 1, 1, 1, 1, 1, 1, 1, 1, 1);
    }
    
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
    public CombatableEntity(String name, long ATK, long DEF, long MATK, long MDEF, double ASPD, double DSPD, long strength, long agile, long wisdom){
        super(name, 100);
        this.ATK = ATK;
        this.DEF = DEF;
        this.MATK = MATK;
        this.MDEF = MDEF;
        this.ASPD = ASPD;
        this.DSPD = DSPD;
        this.strength = strength;
        this.agile = agile;
        this.wisdom = wisdom;
    }

    /**
     * @return the ATK
     */
    public long getATK() {
        return ATK;
    }

    /**
     * @param ATK the ATK to set
     */
    public void setATK(long ATK) {
        this.ATK = ATK;
    }

    /**
     * @return the DEF
     */
    public long getDEF() {
        return DEF;
    }

    /**
     * @param DEF the DEF to set
     */
    public void setDEF(long DEF) {
        this.DEF = DEF;
    }

    /**
     * @return the MATK
     */
    public long getMATK() {
        return MATK;
    }

    /**
     * @param MATK the MATK to set
     */
    public void setMATK(long MATK) {
        this.MATK = MATK;
    }

    /**
     * @return the MDEF
     */
    public long getMDEF() {
        return MDEF;
    }

    /**
     * @param MDEF the MDEF to set
     */
    public void setMDEF(long MDEF) {
        this.MDEF = MDEF;
    }

    /**
     * @return the ASPD
     */
    public double getASPD() {
        return ASPD;
    }

    /**
     * @param ASPD the ASPD to set
     */
    public void setASPD(double ASPD) {
        this.ASPD = ASPD;
    }

    /**
     * @return the DSPD
     */
    public double getDSPD() {
        return DSPD;
    }

    /**
     * @param DSPD the DSPD to set
     */
    public void setDSPD(double DSPD) {
        this.DSPD = DSPD;
    }

    /**
     * @return the strength
     */
    public long getStrength() {
        return strength;
    }

    /**
     * @param strength the strength to set
     */
    public void setStrength(long strength) {
        this.strength = strength;
    }

    /**
     * @return the agile
     */
    public long getAgile() {
        return agile;
    }

    /**
     * @param agile the agile to set
     */
    public void setAgile(long agile) {
        this.agile = agile;
    }

    /**
     * @return the wisdom
     */
    public long getWisdom() {
        return wisdom;
    }

    /**
     * @param wisdom the wisdom to set
     */
    public void setWisdom(long wisdom) {
        this.wisdom = wisdom;
    }

    /**
     * 实体A攻击实体B，不支持
     * @param A 实体A
     * @param B 实体B
     * @return 造成的伤害
     */
    @Override
    public int attackSingle(CombatableEntity A, CombatableEntity B) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 穿上装备
     * @param armor 
     */
    @Override
    public void equip(Armor armor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
