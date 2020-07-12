package map;

import entity.Entity;
import entity.player.Player;
import java.util.UUID;

/**
 *
 * @author Administrator
 */
//方块继承自实体 有HP HP为零时被破坏
public abstract class Block extends Entity {
    
    private UUID owner;
    private boolean canBeBreak = true;

    public Block(String name, Long maxHP) {
        super(name, maxHP);
    }

    public void Break(Player player) {
        if (canBeBreak&&player.getUuid().equals(this.owner)) {
            breakBlock(player);
        }

    }

    public boolean isCanBeBreak() {
        
        return canBeBreak;
    }

    public void setCanBeBreak(boolean canBeBreak) {
        this.canBeBreak = canBeBreak;
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
