package permission;

import entity.player.Player;
import gameevent.GameEvent;
import gamerobot.GameRobot;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限组
 * @author Administrator
 */
public abstract class PermissionGroup {
    private String name;                // 权限组名称
    private Map<Long, String> members;  // 该权限组成员
    private Map<String, String> events; // 事件

    /**
     * 
     * @param name 名称
     */
    public PermissionGroup(String name) {
        this.name = name;
        this.events = new HashMap();
        this.members = new HashMap();
    }

    /**
     * 添加事件
     * @param event 事件
     */
    public void addEvent(GameEvent event) {
        if (!this.events.containsKey(event.getMessage())) {
            this.getEvents().put(event.getMessage(), event.getMessage());
            members.entrySet().forEach(entry -> {
                event.addPermission(GameRobot.players.get(entry.getValue()));
            });
        }
    }

    /**
     * 移除事件
     * @param event 事件
     */
    public void RemoveEvent(GameEvent event) {
        if (this.getEvents().containsKey(event.getMessage())) {
            this.getEvents().remove(event.getMessage(), event);
            members.entrySet().forEach(entry -> {
                event.removePermission(GameRobot.players.get(entry.getValue()));
            });
        }
    }

    /**
     * 添加成员
     * @param player 玩家
     */
    public void addMember(Player player) {
        if (!this.members.containsValue(player.getName())) {
            this.getMembers().put(Long.valueOf(player.getName()), player.getName());
            for (Map.Entry<String, String> entry : this.events.entrySet()) {
                GameRobot.gameEvents.get(entry.getKey()).addPermission(GameRobot.players.get(entry.getValue()));
            }

        }
    }

    /**
     * 移除成员
     * @param player 玩家
     */
    public void removeMember(Player player) {
        if (this.getMembers().containsValue(player.getName())) {
            this.getMembers().remove(Long.valueOf(player.getName()), player.getName());
            this.events.entrySet().forEach(entry -> {
                GameRobot.gameEvents.get(entry.getKey()).removePermission(GameRobot.players.get(entry.getValue()));
            });
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the members
     */
    public Map<Long, String> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(Map<Long, String> members) {
        this.members = members;
    }

    /**
     * @return the events
     */
    public Map<String, String> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(Map<String, String> events) {
        this.events = events;
    }
}
