package permission;

import entity.player.Player;
import gameevent.GameEvent;
import gamerobot.GameRobot;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public abstract class PermissionGroup {

    private String name;
    private Map<Long, String> members;
    private Map<String, String> events;

    public PermissionGroup(String name) {
        this.name = name;
        this.events = new HashMap();
        this.members = new HashMap();
    }

    public void AddEvent(GameEvent event) {
        if (!this.events.containsKey(event.getMessage())) {
            this.getEvents().put(event.getMessage(), event.getMessage());
            for (Map.Entry<Long, String> entry : members.entrySet()) {
                event.AddPermission(GameRobot.players.get(entry.getValue()));
            }
        }
    }

    public void RemoveEvent(GameEvent event) {
        if (this.getEvents().containsKey(event.getMessage())) {
            this.getEvents().remove(event.getMessage(), event);
            for (Map.Entry<Long, String> entry : members.entrySet()) {
                event.RemvoePermission(GameRobot.players.get(entry.getValue()));
            }
        }
    }

    public void AddMember(Player player) {
        if (!this.members.containsValue(player.getName())) {
            this.getMembers().put(Long.valueOf(player.getName()), player.getName());
            for (Map.Entry<String, String> entry : this.events.entrySet()) {
                GameRobot.gameEvents.get(entry.getKey()).AddPermission(GameRobot.players.get(entry.getValue()));
            }

        }
    }

    public void RemvoeMember(Player player) {
        if (this.getMembers().containsValue(player.getName())) {
            this.getMembers().remove(Long.valueOf(player.getName()), player.getName());
            for (Map.Entry<String, String> entry : this.events.entrySet()) {
                GameRobot.gameEvents.get(entry.getKey()).RemvoePermission(GameRobot.players.get(entry.getValue()));
            }
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
