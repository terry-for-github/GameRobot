/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Map.EssBlocks;

import Entity.Player.Player;
import Map.Block;

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
