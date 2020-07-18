package permission;

import gameevent.GameEvent;
import utils.GsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import java.io.File;
import java.util.Map;

/**
 * 权限管理
 * @author Administrator
 */
public class PermissionManager {
    private PermissionManager() {}

    public static Map<String, String> getEvents(File file) {
        String message = GsonUtil.readJsonFile(file.getPath());
        JSONObject jobj = JSON.parseObject(message);
        return ((Map<String, String>) jobj.get("events"));
    }

    public static Map<Long, String> getMembers(File file) {
        String message = GsonUtil.readJsonFile(file.getPath());
        JSONObject jobj = JSON.parseObject(message);
        return ((Map<Long, String>)jobj.get("members"));
    }

    public static void saveGroup(PermissionGroup group, File file) {
        Gson gson = new Gson();
        String string = gson.toJson(group);
        GsonUtil.saveStringToJsonFile(string, file);
    }

    public static Map<Long, Boolean> getPermission(File file) {
        String message = GsonUtil.readJsonFile(file.getPath());
        JSONObject jobj = JSON.parseObject(message);
        Map<Long, Boolean> map = (Map<Long, Boolean>) jobj.get("permissions");
        return map;
    }

    public static Boolean getIsOpen(File file) {
        String message = GsonUtil.readJsonFile(file.getPath());
        JSONObject jobj = JSON.parseObject(message);
        boolean flag = jobj.getBoolean("isopen");
        return flag;
    }

    public static Boolean getOpen(File file) {
        String message = GsonUtil.readJsonFile(file.getPath());
        JSONObject jobj = JSON.parseObject(message);
        boolean flag = jobj.getBoolean("open");
        return flag;
    }

    public static void savePerMission(GameEvent gameEvent, File file) {
        GsonUtil.saveStringToJsonFile(new Gson().toJson(gameEvent), file);
    }
}
