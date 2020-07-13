package entity;

import java.util.HashMap;

/**
 * 可以收获的实体
 * @author Administrator
 */
public abstract class SlaughterableEntity extends Entity {
    private HashMap<String, Integer> good = new HashMap<>();    // 收获获得的物品与数量
    private int times;                                          // 可被收获的次数
    private long age;                                           // 当前成长度
    private long maxAge;                                        // 最大成长度

    /**
     * 
     * @param name 名字
     * @param MAXHP 血量上线
     * @param times 可以被收获的次数
     * @param maxAge 最大成长度
     */
    public SlaughterableEntity(String name, long MAXHP, int times, long maxAge) {
        super(name, MAXHP);
        this.times = times;
        this.age = 0;
        this.maxAge = maxAge;
    }

    /**
     * @return the times
     */
    public int getTimes() {
        return times;
    }

    /**
     * @param times the times to set
     */
    public void setTimes(int times) {
        this.times = times;
    }

    /**
     * @return the age
     */
    public long getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(long age) {
        this.age = age;
    }

    /**
     * @return the maxAge
     */
    public long getMaxAge() {
        return maxAge;
    }

    /**
     * @param maxAge the maxAge to set
     */
    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * @return the goods
     */
    public HashMap<String, Integer> getGoods() {
        return good;
    }

    /**
     * @param goods the goods to set
     */
    public void setGoods(HashMap<String, Integer> goods) {
        this.good = goods;
    }
}
