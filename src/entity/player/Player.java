package entity.player;

import entity.CombatableEntity;
import goods.armor.Armor;
import goods.Good;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import interfaces.AttackSingle;
import interfaces.BuffAddable;
import interfaces.Equipable;
import static gamerobot.GameRobot.bot;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

/**
 * 玩家实体类
 * @author Administrator
 */
public class Player extends CombatableEntity implements Cloneable, AttackSingle, Equipable, BuffAddable {

    private Map<String, Good> equip;    // 玩家装备
    private Map<String, Good> store;    // 玩家的背包
    private long money;                 // 玩家的金币数量
    private long level;                 // 玩家等级
    private long exp;                   // 玩家当前经验值
    private long maxExp;                // 这一等级要升级所需最大经验值
    private long points;                // 可分配属性点数

    /**
     * 
     * @param name 玩家名称
     */
    public Player(String name) {
        super(name);
        this.level = 1;
        this.exp = 0;
        this.maxExp = 100;
        this.points = 0;
        this.money = 0;
        this.equip = new ConcurrentHashMap<>();
        this.store = new ConcurrentHashMap<>();
    }

    /**
     * 复活玩家
     */
    public void revive() {
        if (this.getHP() == 0) {
            Player player = this;
            Date date = new Date();
            date.setTime(date.getTime() + 600000);
            sendMessageToPlayerByFriend("你正在复活中\n" + date);
            Timer timer = new Timer();
            Timer timer1 = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    player.setHP(player.getMaxHP());
                    sendMessageToPlayerByFriend("你已经复活了");
                    timer1.cancel();
                    timer.cancel();
                }
            }, 600000);

            timer1.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (player.getHP() > 0) {
                        timer.cancel();
                        timer1.cancel();
                    }
                }
            }, 0, 100);
        }
    }

    /**
     * 穿上防具
     * @param armor 防具
     */
    @Override
    public void equip(Armor armor) {
        if (equip.containsKey(armor.getArmortype().toString())) {
            store.put(equip.get(armor.getArmortype().toString()).getName(), equip.get(armor.getArmortype().toString()));
            equip.put(armor.getArmortype().toString(), armor);
        } else {
            equip.put(armor.getArmortype().toString(), armor);
        }
    }

    /**
     * 获得经验值
     * @param exp 经验值
     */
    public void getExp(long exp) {
        this.setExp(this.getExp() + exp);
        playerLevelUp(this);// 判断是否能够升级 若能则升级
    }

    /**
     * 获得金币
     * @param money 金币数量
     */
    public void addMoney(long money) {
        this.money = this.money + money;
    }

    /**
     * 扣除金币
     * @param money 金币数量
     * @return 是否扣除成功
     */
    public boolean removeMoney(long money) {
        if (this.money >= money) {
            this.money -= money;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断玩家是否能够升级 若能升级则升级
     * @param player 玩家
     */
    public static void playerLevelUp(Player player) {
        if (player.getExp() >= player.getMaxExp()) {
            player.setLevel(player.getLevel() + 1);
            player.setPoints(player.getPoints() + 5);
            player.setExp(player.getExp() - player.getMaxExp());
            player.setMaxExp((long) (player.getMaxExp() * 1.25));
            // 递归调用 防止经验溢出后不能再次升级
            playerLevelUp(player);
        }
    }

    /**
     * 判断玩家是否拥有足够的货物
     * @param good 货物
     * @param number 货物数量
     * @return 是否拥有足够货物
     */
    public boolean haveEnoughGood(Good good, int number) {
        if (this.store.containsKey(good.getName()) && this.store.get(good.getName()).getNumber() >= number) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 为玩家增加一定数量货物
     * @param good 货物
     * @param number 数量
     */
    public void playerAddGood(Good good, int number) {
        // TODO 待改进
        // 装甲名称需要额外的显示方式 不然会合一块 导致 被损耗的装备被折叠在一起
        if (good instanceof Armor) {
            good.setName(good.getName() + ((Armor) good).getUuid().timestamp());
        }
        good.setNumber(number);
        if (this.getStore().containsKey(good.getName())) {
            Good theGood = this.getStore().get(good.getName());
            theGood.setNumber(theGood.getNumber() + number);
        } else {
            this.getStore().put(good.getName(), good);
        }
    }

    /**
     * 移除玩家货物的数量 若有足够数量则移除 不过则返回false
     * @param good 货物
     * @param number 数量
     * @return 是否扣除成功
     */
    public boolean playerRemoveGood(Good good, int number) {
        if (haveEnoughGood(good, number)) {
            Good theGood = this.getStore().get(good.getName());
            if (theGood.getNumber() == number) {
                theGood.setNumber(theGood.getNumber() - number);
            } else if (theGood.getNumber() > number) {
                this.getStore().remove(good.getName());
            }
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 返回玩家的qq号
     * @return 
     */
    public Friend getQQ() {
        return bot.getFriend(Long.valueOf(this.getName()));
    }

    /**
     * 玩家攻击其他实体
     * @param A 玩家
     * @param B 其他实体
     * @return 造成的伤害
     */
    @Override
    public int attackSingle(CombatableEntity A, CombatableEntity B) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }

    /**
     * 向玩家发送一条信息（有好友情况下）
     * @param message 信息
     * @return 是否发送成功
     */
    public boolean sendMessageToPlayerByFriend(String message) {
        message = "======================\n     " + message + "     \n======================";
        if (bot.getFriends().contains(Long.parseLong(this.getName()))) {
            bot.getFriend(Long.valueOf(this.getName())).sendMessage(message);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 向群组发送一条信息
     * @param message 信息
     * @param group 群组
     * @return 是否发送成功
     */
    public boolean sendMessageToPlayerByGroup(String message, Group group) {
        group.sendMessage("======================\n     " + message + "     \n======================");
        return true;
    }

    /**
     * @return the equip
     */
    public Map<String, Good> getEquip() {
        return equip;
    }

    /**
     * @param equip the Equip to set
     */
    public void setEquip(Map<String, Good> equip) {
        this.equip = equip;
    }

    /**
     * @return the store
     */
    public Map<String, Good> getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(Map<String, Good> store) {
        this.store = store;
    }

    /**
     * @return the level
     */
    public long getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(long level) {
        this.level = level;
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
     * @return the maxExp
     */
    public long getMaxExp() {
        return maxExp;
    }

    /**
     * @param maxExp the maxExp to set
     */
    public void setMaxExp(long maxExp) {
        this.maxExp = maxExp;
    }

    /**
     * @return the points
     */
    public long getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(long points) {
        this.points = points;
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
}
