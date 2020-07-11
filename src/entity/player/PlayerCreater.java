/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Player;

import Goods.Good;
import static Goods.GoodCreater.StringToGoods;
import Utils.GsonUtil;
import gamerobot.GameRobot;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


/**
 *
 * @author Administrator
 */
public class PlayerCreater {
    
    
    //初始化时 从玩家文件获取玩家
    public static Player GetPlayerFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        String name = jobj.getString("name");
        UUID uuid = UUID.fromString(jobj.getString("uuid"));
        long HP = jobj.getLongValue("HP");
        long MAXHP = jobj.getLongValue("MAXHP");
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
        long maxexp = jobj.getLongValue("maxexp");
        long points = jobj.getLongValue("points");
        long level = jobj.getLongValue("level");
        long money = jobj.getLongValue("money");
        Map<String, Good> Equip = StringToGoods((Map<String, String>) jobj.get("Equip"));
        Map<String, Good> Store = StringToGoods((Map<String, String>) jobj.get("Store"));

        Player player = new Player(name);

        player.setUuid(uuid);
        player.setHP(HP);
        player.setMAXHP(MAXHP);
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
        player.setMaxexp(maxexp);
        player.setPoints(points);
        player.setLevel(level);
        player.setMoney(money);
        player.setEquip(Equip);
        player.setStore(Store);

        return player;
    }
    
    
    
    //创建一个玩家
    public static boolean CreatePlayer(long id) throws IOException {
        File f = new File("");
        String cf = "";
        cf = f.getCanonicalPath();
        File datafile = new File(cf + "/data/Saves/Players/" + id);
        if (!datafile.exists()) {
            datafile.mkdirs();
        }
        File file = new File(cf + "/data/Saves/Players/" + id + "/" + id + ".json");
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

    public static void SavePlayerToFile(long id, Player player) throws IOException {
        Gson gson = new Gson();
        File f = new File("");
        String cf = "";
        cf = f.getCanonicalPath();
        File playerdata = new File(cf + "/data/Saves/Players/" + id + ".json");
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
