/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEvent;

import Entity.Player.Player;

/**
 *
 * @author Administrator
 */
public class BuildEvent extends GameEvent{
    
    public BuildEvent() 
    {
        super("测试");
    }
    
    @Override
    public void Do(Player player)
    {
        player.SendMessageToPlayer("测试");
    }
}
