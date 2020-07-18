package map.essblocks;

import entity.player.Player;
import map.Block;

/**
 * 不可破坏的方块
 * @author Administrator
 */
public class UnbreakableBlock extends Block{
    /**
     * 
     */
    public UnbreakableBlock() {
        super("UnbreakableBlock", Long.valueOf(0));
        this.setCanBeBreak(false);
    }

    /**
     * 被玩家破坏
     * @param player 玩家
     */
    @Override
    public void breakBlock(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 被其他方式破坏
     */
    @Override
    public void breakBlock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
