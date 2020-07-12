/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.essblocks;

import entity.player.Player;
import map.Block;

/**
 *
 * @author Administrator
 */
public class UnbreakableBlock extends Block{

    public UnbreakableBlock() {
        super("UnbreakableBlock", Long.valueOf(0));
        this.setCanBeBreak(false);
    }

    @Override
    public void breakBlock(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void breakBlock() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
