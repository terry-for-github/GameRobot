/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import Entities.Entity;
import Entity.Player.Player;

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
