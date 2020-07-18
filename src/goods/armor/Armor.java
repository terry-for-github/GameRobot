package goods.armor;

import goods.Good;
import java.util.UUID;

/**
 * 装甲类
 * @author Administrator
 */
public class Armor extends Good implements Cloneable {
    private ArmorType armorType;    // 装甲种类
    private UUID uuid;              // 唯一标识符
    //属性加成
    private long ATK = 0;           // 攻击力
    private long DEF = 0;           // 防御力
    private long MATK = 0;          // 施法强度
    private long MDEF = 0;          // 魔法防御
    private double ASPD = 0;        // 攻击速度
    private double DSPD = 0;        // 施法速度
    private long strength = 0;      // 力量
    private long agile = 0;         // 敏捷
    private long wisdom = 0;        // 智慧

    /**
     * 装甲
     * @param name 名字
     * @param use 用途
     */
    public Armor(String name, String use) {
        super(name, "装备", use);
        this.uuid = UUID.randomUUID();
        this.setName(name + uuid.toString().substring(uuid.toString().length() - 4, uuid.toString().length()));
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Good clone() throws CloneNotSupportedException {
        return (Armor) super.clone();
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

    /**
     * @return the armorType
     */
    public ArmorType getArmorType() {
        return armorType;
    }

    /**
     * @param armorType the armorType to set
     */
    public void setArmorType(ArmorType armorType) {
        this.armorType = armorType;
    }

}
