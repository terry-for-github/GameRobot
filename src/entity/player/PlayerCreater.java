package entity.player;

import utils.GsonUtil;
import gamerobot.GameRobot;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import static utils.Initization.ReturnPath;
import static goods.GoodCreater.stringToGoods;


/**
 * 创造玩家
 * @author Administrator
 */
public class PlayerCreater {
    private PlayerCreater() {}
    
    /**
     * 初始化时 从玩家文件获取玩家
     * @param filepath 文件路径
     * @return 玩家
     */
    public static Player getPlayerFromFile(String filepath) throws IOException {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        
        Player player = new Player(jobj.getString("name"));
        player.setUuid(UUID.fromString(jobj.getString("uuid")));
        player.setHP(jobj.getLongValue("HP"));
        player.setMaxHP(jobj.getLongValue("maxHP"));
        player.setATK(jobj.getLongValue("ATK"));
        player.setDEF(jobj.getLongValue("DEF"));
        player.setMATK(jobj.getLongValue("MATK"));
        player.setMDEF(jobj.getLongValue("MDEF"));
        player.setStrength(jobj.getLongValue("strenth"));
        player.setAgile(jobj.getLongValue("agile"));
        player.setWisdom(jobj.getLongValue("wisdom"));
        player.setASPD(jobj.getLongValue("ASPD"));
        player.setDSPD(jobj.getLongValue("DSPD"));
        player.setExp(jobj.getLongValue("exp"));
        player.setMaxExp(jobj.getLongValue("maxExp"));
        player.setPoints(jobj.getLongValue("points"));
        player.setLevel(jobj.getLongValue("level"));
        player.setMoney(jobj.getLongValue("money"));
        player.setEquip(stringToGoods((Map<String, String>) jobj.get("equip")));
        player.setStore(stringToGoods((Map<String, String>) jobj.get("store")));
        
        return player;
    }
    
    /**
     * 创建一个玩家
     * @param id 玩家qq号
     * @return 是否创造成功
     * @throws IOException 
     */
    public static boolean createPlayer(long id) throws IOException {
        File dataFile = new File(ReturnPath() + "/Saves/Players/" + id);
        if (!dataFile.exists()) {
            dataFile.mkdirs();
        }
        System.out.println(dataFile.getAbsolutePath());
        File file = new File(ReturnPath() + "/Saves/Players/" + id + "/" + id + ".json");
        if (!file.exists()) {
            Player player = new Player(String.valueOf(id));
            GameRobot.players.put(player.getName(), player);
            file.createNewFile();
            GsonUtil.saveStringToJsonFile(GsonUtil.getStringFromObject(player), file);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 把玩家保存为文件
     * @param id 玩家qq号
     * @param player 玩家
     * @throws IOException 
     */
    public static void savePlayerToFile(long id, Player player) throws IOException{
        Gson gson = new Gson();
        File playerData = new File(ReturnPath() + "/Saves/Players/" + id + ".json");
        if(!playerData.exists()){
            playerData.createNewFile();
        }
        GsonUtil.saveStringToJsonFile(gson.toJson(player), playerData);
    }
}
