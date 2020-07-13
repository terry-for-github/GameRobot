package gameevent;

import entity.player.Player;
import net.mamoe.mirai.contact.Group;
import gamerobot.GameRobot;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
//事件
public abstract class GameEvent {

    private String message;//事件触发关键字
    private boolean isopen = true;//是否开启
    private boolean open = true;//是否所有人都能用
    private Map<Long, Boolean> permissions = new HashMap();//权限  对应的Long是玩家的QQ号

    public GameEvent(String message) {
        this.message = message;
        GameRobot.gameEvents.put(message, this);
    }
    
    //添加此事件权限
    public void AddPermission(Player player) {
        if (!permissions.containsKey(Long.valueOf(player.getName()))) {
            getPermissions().put(Long.valueOf(player.getName()), Boolean.TRUE);
        }
    }
    
    //移除此事件权限
    public void RemvoePermission(Player player) {
        if (getPermissions().containsKey(Long.valueOf(player.getName()))) {
            getPermissions().put(Long.valueOf(player.getName()), Boolean.FALSE);
        }
    }
    
    //五种触发方式（可适当更改）
    public void Do(Player player) {

    }

    public void Do(Group group) {

    }

    public void Do(Player player, String message) {

    }

    public void Do(Group group, Player player) {

    }

    public void Do(Group group, String message) {

    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the permissions
     */
    public Map<Long, Boolean> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Map<Long, Boolean> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the isopen
     */
    public boolean isIsopen() {
        return isopen;
    }

    /**
     * @param isopen the isopen to set
     */
    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }

    /**
     * @return the open
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @param open the open to set
     */
    public void setOpen(boolean open) {
        this.open = open;
    }
}
