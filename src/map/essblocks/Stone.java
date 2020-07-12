package map.essblocks;

import entity.player.Player;
import map.Block;

/**
 *
 * @author Administrator
 */
public class Stone extends Block {

    public Stone() {
        super("Stone", Long.valueOf(10));
    }

    
    //被其他方式破坏
    @Override
    public void breakBlock() {
    }

    //被玩家破坏时
    @Override
    public void breakBlock(Player player) {
//        player.PlayerAddGood(, 1);
    }
}
