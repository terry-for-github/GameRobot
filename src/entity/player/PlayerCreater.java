package entity.player;

import goods.Good;
import static goods.GoodCreater.StringToGoods;
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


/**
 *
 * @author Administrator
 */
public class PlayerCreater {
    private PlayerCreater() {}
    //初始化时 从玩家文件获取玩家
    public static Player getPlayerFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        String name = jobj.getString("name");
        UUID uuid = UUID.fromString(jobj.getString("uuid"));
        long HP = jobj.getLongValue("HP");
        long maxHP = jobj.getLongValue("maxHP");
        long ATK = jobj.getLongValue("ATK");
        long DEF = jobj.getLongValue("DEF");
        long MATK = jobj.getLongValue("MATK");
        long MDEF = jobj.getLongValue("MDEF");
        long strength = jobj.getLongValue("strength");
        long agile = jobj.getLongValue("agile");
        long wisdom = jobj.getLongValue("wisdom");
        double ASPD = jobj.getDouble("ASPD");
        double DSPD = jobj.getDouble("DSPD");
        long exp = jobj.getLongValue("exp");
        long maxExp = jobj.getLongValue("maxExp");
        long points = jobj.getLongValue("points");
        long level = jobj.getLongValue("level");
        long money = jobj.getLongValue("money");
        Map<String, Good> equip = StringToGoods((Map<String, String>) jobj.get("equip"));
        Map<String, Good> store = StringToGoods((Map<String, String>) jobj.get("store"));

        Player player = new Player(name);

        player.setUuid(uuid);
        player.setHP(HP);
        player.setMaxHP(maxHP);
        player.setATK(ATK);
        player.setDEF(DEF);
        player.setMATK(MATK);
        player.setMDEF(MDEF);
        player.setStrength(strength);
        player.setAgile(agile);
        player.setWisdom(wisdom);
        player.setASPD(ASPD);
        player.setDSPD(DSPD);
        player.setExp(exp);
        player.setMaxExp(maxExp);
        player.setPoints(points);
        player.setLevel(level);
        player.setMoney(money);
        player.setEquip(equip);
        player.setStore(store);

        return player;
    }
    
    
    
    //创建一个玩家
    public static boolean createPlayer(long id) throws IOException {
        File datafile = new File(ReturnPath() + "/Saves/Players/" + id);
        if (!datafile.exists()) {
            datafile.mkdirs();
        }
        File file = new File(ReturnPath() + "/Saves/Players/" + id + "/" + id + ".json");
        if (!file.exists()) {
            Player player = new Player(String.valueOf(id));
            GameRobot.players.put(player.getName(), player);
            file.createNewFile();
            GsonUtil.SaveStringToJsonFile(GsonUtil.GetStringFromObject(player), file);
            return true;
        } else {
            return false;
        }
    }

    public static void savePlayerToFile(long id, Player player) throws IOException {
        Gson gson = new Gson();
        File playerdata = new File(ReturnPath() + "/Saves/Players/" + id + ".json");
        if (playerdata.exists()) {
            String string = gson.toJson(player);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        } else {
            playerdata.createNewFile();
            String string = gson.toJson(player);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        }
    }
}
