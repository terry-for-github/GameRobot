package goods.consumables;

import goods.Good;

/**
 *
 * @author Administrator
 */
//消耗品
public class Consumables extends Good implements Cloneable {

    public Consumables(String name, String use) {
        super(name, "消耗品", use);
    }

    @Override
    public Good clone() {
        Consumables consumables = null;
        consumables = (Consumables) super.clone();
        return consumables;
    }

    public void Use() {

    }
}
