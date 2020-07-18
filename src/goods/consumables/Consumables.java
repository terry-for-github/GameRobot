package goods.consumables;

import goods.Good;

/**
 * 消耗品
 * @author Administrator
 */
public class Consumables extends Good implements Cloneable {
    /**
     * 消耗品
     * @param name 名字
     * @param use 用途
     */
    public Consumables(String name, String use) {
        super(name, "消耗品", use);
    }

    /**
     * 
     * @return 
     */
    @Override
    public Good clone() throws CloneNotSupportedException {
        return (Consumables) super.clone();
    }
    
    /**
     * 使用
     */
    public void use() {

    }
}
