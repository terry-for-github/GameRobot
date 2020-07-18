package entity.player;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Member;
import static gamerobot.GameRobot.players;

/**
 * 管理玩家
 * @author Administrator
 */
public class PlayerManager {
    /**
     * 是否存在这个玩家
     * @param sender 
     * @return 是否存在这个玩家
     */
    public static boolean existThisPlayer(Friend sender) {
        return players.containsKey(String.valueOf(sender.getId()));
    }
    
    /**
     * 是否存在这个玩家
     * @param sender
     * @return 是否存在这个玩家
     */
    public static boolean existThisPlayer(Member sender) {
        return players.containsKey(String.valueOf(sender.getId()));
    }

    
    /**
     * 玩家查看自身装备
     * @param player 玩家
     */
    public static void playerSeeEquip(Player player) {

    }
    
    /**
     * 玩家查看商店
     */
    public static void playerSeeStore() {

    }
}
