package map.essblocks;

import entity.player.Player;
import map.Block;

/**
 * 石头
 * @author Administrator
 */
public class Stone extends Block {
    /**
     * 石头
     */
    public Stone() {
        super("Stone", Long.valueOf(10));
    }

    /**
     * 被其他方式破坏
     */
    @Override
    public void breakBlock() {
        
    }

    /**
     * 被玩家破坏
     * @param player 玩家
     */
    @Override
    public void breakBlock(Player player) {
//        player.PlayerAddGood(, 1);
    }
}
