package map;

import entity.Entity;
import entity.player.Player;

/**
 *
 * @author Administrator
 */
//方块继承自实体 有HP HP为零时被破坏
public abstract class Block extends Entity {

    private boolean canBeBreak = true;

    public Block(String name, Long maxHP) {
        super(name, maxHP);
    }

    public void Break(Player player) {
        if (canBeBreak) {
            breakBlock(player);
        }

    }

    public void Break() {
        if (canBeBreak) {
            breakBlock();
        }

    }

    //被破坏发生的事情
    public abstract void breakBlock(Player player);

    public abstract void breakBlock();

}
