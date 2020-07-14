package gameevent;

import entity.player.Player;

/**
 *
 * @author Administrator
 */
public class BuildEvent extends GameEvent {

    public BuildEvent() {
        super("测试");
    }

    @Override
    public void Do(Player player) {
        player.sendMessageToPlayerByFriend("测试");
    }
}
