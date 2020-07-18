package goods;

/**
 * 货物
 * @author Administrator
 */
public abstract class Good implements Cloneable {
    private int number;     // 数量
    private String type;    // 种类
    private String use;     // 用途（描述？）description
    private String name;    // 名称

    /**
     * 货物
     * @param name 名字
     * @param type 类型
     * @param use  使用
     */
    public Good(String name, String type, String use) {
        this.name = name;
        this.type = type;
        this.use = use;
        this.number = 1;
    }
    
    /**
     * 
     * @return
     * @throws CloneNotSupportedException 
     */
     @Override
    public Good clone() throws CloneNotSupportedException {
        return (Good)super.clone();
    }
    
    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the use
     */
    public String getUse() {
        return use;
    }

    /**
     * @param use the use to set
     */
    public void setUse(String use) {
        this.use = use;
    }
}
