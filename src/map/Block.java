package map;

import entity.Entity;
import entity.player.Player;
import java.util.UUID;

/**
 * 方块继承自实体 有HP HP为零时被破坏
 * @author Administrator
 */
public abstract class Block extends Entity {
    private UUID owner;                 // 拥有者
    private boolean canBeBreak = true;  // 能否被破坏

    /**
     * 方块
     * @param name 名字
     * @param maxHP 血量上限
     */
    public Block(String name, Long maxHP) {
        super(name, maxHP);
    }

    /**
     * 破坏
     * @param player 玩家
     */
    // TODO 改名
    public void Break(Player player) {
        if (canBeBreak&&player.getUuid().equals(this.owner)) {
            breakBlock(player);
        }
    }
    
    /**
     * 破坏
     */
    // 改名
    public void Break() {
        if (canBeBreak) {
            breakBlock();
        }
    }

    /**
     * 
     * @return 能否被破坏
     */
    public boolean isCanBeBreak() {
        return canBeBreak;
    }

    /**
     * 设置能否被破坏
     * @param canBeBreak 
     */
    public void setCanBeBreak(boolean canBeBreak) {
        this.canBeBreak = canBeBreak;
    }
    
    /**
     * 被破坏
     * @param player 破坏方块的玩家
     */
    public abstract void breakBlock(Player player);
    
    /**
     * 被破坏
     */
    public abstract void breakBlock();
}
