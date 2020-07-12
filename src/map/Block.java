package map;

import entity.Entity;
import entity.player.Player;

/**
 *
 * @author Administrator
 */

//方块继承自实体 有HP HP为零时被破坏
public abstract class Block extends Entity{
    
    public Block(String name,Long maxHP)
    {
        super(name,maxHP);
    }
   
   
    //被破坏发生的事情
    public abstract void breakBlock(Player player);
    public abstract void breakBlock();
    
   
    
}
