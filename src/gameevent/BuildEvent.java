package gameevent;

import entity.player.Player;

/**
 * 创建事件
 * @author Administrator
 */
public class BuildEvent extends GameEvent {
    /**
     * 
     */
    public BuildEvent() {
        super("测试");
    }
    
    /**
     * 执行操作
     * @param player 玩家
     */
    @Override
    public void Do(Player player) {
        player.sendMessageToPlayerByFriend("测试");
    }
}
