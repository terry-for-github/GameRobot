/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Goods.Armor;

import Goods.Good;
import java.util.UUID;

/**
 *
 * @author Administrator
 */
//装甲类
public class Armor extends Good implements Cloneable {

    private ArmorType armortype;//装甲种类
    private UUID uuid;//唯一标识符
    private long ATK = 0;//属性加成
    private long DEF = 0;
    private long MATK = 0;
    private long MDEF = 0;
    private double ASPD = 0;
    private double DSPD = 0;
    private long strength = 0;
    private long agile = 0;
    private long wisdom = 0;

    public Armor(String name, String use) {
        super(name, "装备", use);
        this.uuid = UUID.randomUUID();
        this.setName(name + uuid.toString().substring(uuid.toString().length() - 4, uuid.toString().length()));
    }

    @Override
    public Good clone() {
        Armor armor = null;
        armor = (Armor) super.clone();
        return armor;
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
     * @return the armortype
     */
    public ArmorType getArmortype() {
        return armortype;
    }

    /**
     * @param armortype the armortype to set
     */
    public void setArmortype(ArmorType armortype) {
        this.armortype = armortype;
    }

}
