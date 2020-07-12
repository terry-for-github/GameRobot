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
 *
 * @author Administrator
 */
//玩家实体类
public class Player extends CombatableEntity implements Cloneable, AttackSingle, Equipable, BuffAddable {

    private Map<String, Good> Equip; //玩家装备
    private Map<String, Good> Store;//玩家的背包
    private long money;//玩家的金币数量
    private long level;//玩家等级
    private long exp;//玩家当前经验值
    private long maxexp;//这一等级要升级所需最大经验值
    private long points;//可分配属性点数

    public Player(String name) {
        super(name);
        this.level = 1;
        this.exp = 0;
        this.maxexp = 100;
        this.points = 0;
        this.money = 0;
        this.Equip = new ConcurrentHashMap<>();
        this.Store = new ConcurrentHashMap<>();
    }

    //复活玩家方法
    public void Revivid() {
        if (this.getHP() == 0) {
            Player player = this;
            Date date = new Date();
            date.setTime(date.getTime() + 600000);
            SendMessageToPlayerByFriend("你正在复活中\n" + date);
            Timer timer = new Timer();
            Timer timer1 = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    player.setHP(player.getMAXHP());
                    SendMessageToPlayerByFriend("你已经复活了");
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

    //为玩家穿上装备
    @Override
    public void Equip(Armor armor) {
        if (Equip.containsKey(armor.getArmortype().toString())) {
            Store.put(Equip.get(armor.getArmortype().toString()).getName(), Equip.get(armor.getArmortype().toString()));
            Equip.put(armor.getArmortype().toString(), armor);
        } else {
            Equip.put(armor.getArmortype().toString(), armor);
        }
    }

    //玩家获取经验值
    public void GetExp(long exp) {
        this.setExp(this.getExp() + exp);
        PlayerLevelUp(this);//判断是否能够升级 若能则升级
    }

    //玩家获得金币
    public void AddMoney(long money) {
        this.money = this.money + money;
    }

    //玩家扣除金币
    public boolean RemoveMoney(long money) {
        if (this.money >= money) {
            this.money = this.money - money;
            return true;
        } else {
            return false;
        }
    }

    //判断玩家是否能够升级 若能升级则升级
    public static void PlayerLevelUp(Player player) {
        if (player.getExp() >= player.getMaxexp()) {
            player.setLevel(player.getLevel() + 1);
            player.setPoints(player.getPoints() + 5);
            player.setExp(player.getExp() - player.getMaxexp());
            player.setMaxexp((long) (player.getMaxexp() * 1.25));
            //递归调用 防止经验溢出后不能再次升级
            if (player.getExp() >= player.getMaxexp()) {
                player.setExp(player.getExp() - player.getMaxexp());
                PlayerLevelUp(player);
            }
        }
    }

    //判断玩家是否拥有足够的货物
    public boolean HaveEnoughGood(Good good, int number) {
        if (this.Store.containsKey(good.getName()) && this.Store.get(good.getName()).getNumber() >= number) {
            return true;
        } else {
            return false;
        }
    }

    //为玩家增加一定数量货物
    public boolean PlayerAddGood(Good good, int number) {
        //装甲名称需要额外的显示方式 不然会合一块 导致 被损耗的装备被折叠在一起
        if (good instanceof Armor) {
            good.setName(good.getName() + ((Armor) good).getUuid().timestamp());
        }
        good.setNumber(number);
        if (this.getStore().containsKey(good.getName())) {
            Good thegood = this.getStore().get(good.getName());
            thegood.setNumber(thegood.getNumber() + number);
        } else {
            this.getStore().put(good.getName(), good);
        }
        return true;
    }

    //移除玩家的数量 若有足够数量则移除 不过则返回false
    public boolean PlayerRemoveGood(Good good, int number) {
        if (HaveEnoughGood(good, number)) {
            Good thegood = this.getStore().get(good.getName());
            if (thegood.getNumber() == number) {
                thegood.setNumber(thegood.getNumber() - number);
            } else if (thegood.getNumber() > number) {
                this.getStore().remove(good.getName());
            }
            return true;

        } else {
            return false;
        }

    }

    public Friend GetQQ() {
        return bot.getFriend(Long.valueOf(this.getName()));
    }

    //攻击个体实体
    @Override
    public int AttackSingle(CombatableEntity A, CombatableEntity B) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player clone() throws CloneNotSupportedException {
        Player player = null;
        try {
            player = (Player) super.clone();
        } catch (CloneNotSupportedException e) {
        }

        return player;
    }

    //向玩家发送一条信息（有好友情况下）
    public boolean SendMessageToPlayerByFriend(String message) {
        message = "======================\n     " + message + "     \n======================";
        if (bot.getFriends().contains(Long.parseLong(this.getName()))) {
            bot.getFriend(Long.valueOf(this.getName())).sendMessage(message);
            return true;
        } else {
            return false;
        }
    }

    public boolean SendMessageToPlayerByGroup(String message, Group group) {
        message = "======================\n     " + message + "     \n======================";
        group.sendMessage(message);
        return true;
    }

    /**
     * @return the Equip
     */
    public Map<String, Good> getEquip() {
        return Equip;
    }

    /**
     * @param Equip the Equip to set
     */
    public void setEquip(Map<String, Good> Equip) {
        this.Equip = Equip;
    }

    /**
     * @return the Store
     */
    public Map<String, Good> getStore() {
        return Store;
    }

    /**
     * @param Store the Store to set
     */
    public void setStore(Map<String, Good> Store) {
        this.Store = Store;
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
     * @return the maxexp
     */
    public long getMaxexp() {
        return maxexp;
    }

    /**
     * @param maxexp the maxexp to set
     */
    public void setMaxexp(long maxexp) {
        this.maxexp = maxexp;
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
