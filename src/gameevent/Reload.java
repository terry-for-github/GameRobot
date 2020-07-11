/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameEvent;

import Entity.Player.Player;
import Utils.Initization;
import gamerobot.GameRobot;
import static gamerobot.GameRobot.groups;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.mamoe.mirai.contact.Group;

/**
 *
 * @author Administrator
 */

//重载事件

public class Reload extends GameEvent
{
    
    public Reload() {
        super("/reload");
    }
    

    @Override
    public void Do(Group group, Player player) {
        if(groups.get("所有者").getMembers().containsValue(player.getName()))
        {
            group.sendMessage("正在重载");
            try {
                Initization.Reload();
            } catch (InstantiationException ex) {
                Logger.getLogger(Reload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Reload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(Reload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Reload.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Reload.class.getName()).log(Level.SEVERE, null, ex);
            }
             group.sendMessage("重载完毕");
        }
        else
        {
            group.sendMessage("你没有权限");
        }
    }

}
