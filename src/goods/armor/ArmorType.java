package goods.armor;

/**
 * 装甲种类
 * @author Administrator
 */
public enum ArmorType {
    Helemet("Helemet"),         // 头盔
    Breastplate("Breastplate"), // 胸甲
    Shinguard("Shinguard"),     // 护腿
    Boots("Boots"),             // 靴子
    Weapons("Weapons"),         // 武器
    Ornaments("Ornaments");     // 饰品
    
    private final String name;
    ArmorType(String name) {
        this.name = name;
    }
}
