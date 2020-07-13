package entity.mobs;

import goods.Good;
import static goods.GoodCreater.StringToGoods;
import utils.GsonUtil;
import static utils.GsonUtil.GetStringFromObject;
import static utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import static gamerobot.GameRobot.goods;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


/**
 *
 * @author Administrator
 */
public class MobCreater {
    
    //从文件获取怪物
    public static Mob GetMobFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        String name = jobj.getString("name");
        long HP = jobj.getLongValue("HP");
        long MAXHP = jobj.getLongValue("MAXHP");
        long ATK = jobj.getLongValue("ATK");
        long DEF = jobj.getLongValue("DEF");
        long MATK = jobj.getLongValue("MATK");
        long MDEF = jobj.getLongValue("MDEF");
        long strength = jobj.getLongValue("strength");
        long agile = jobj.getLongValue("agile");
        long wisdom = jobj.getLongValue("wisdom");
        long money = jobj.getLongValue("money");
        long exp = jobj.getLongValue("exp");
        Map<String, Good> goods = StringToGoods((Map<String, String>) jobj.get("goods"));
        double ASPD = jobj.getDouble("ASPD");
        double DSPD = jobj.getDouble("DSPD");

        Mob mob = new Mob(name, ATK, DEF, MATK, MDEF, strength, agile, wisdom, ASPD, DSPD);
        mob.setExp(exp);
        mob.setMoney(money);
        mob.setGoods(goods);
        mob.setUuid(UUID.randomUUID());
        mob.setHP(HP);
        mob.setMAXHP(MAXHP);
        return mob;
    }

    public static void SaveMobToFile(Mob mob) throws IOException {
        File Mobs = new File(ReturnPath() + "/Main/Entity/Mobs");
        Gson gson = new Gson();
        File playerdata = new File(Mobs + "/" + mob.getName() + ".json");
        if (playerdata.exists()) {
            String string = GetStringFromObject(mob);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        } else {
            playerdata.createNewFile();
            String string = GetStringFromObject(mob);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        }
    }

    public static String CreateMob(String string, String option, Mob mob) throws IOException {
        String message = "";
        if (string.contains("名称")) {
            mob.setName(option);
        } else if (string.contains("攻击")) {
            mob.setATK(Long.valueOf(option));
        } else if (string.contains("防御")) {
            mob.setDEF(Long.valueOf(option));
        } else if (string.contains("法强")) {
            mob.setMATK(Long.valueOf(option));
        } else if (string.contains("魔抗")) {
            mob.setMDEF(Long.valueOf(option));
        } else if (string.contains("力量")) {
            mob.setStrength(Long.valueOf(option));
        } else if (string.contains("敏捷")) {
            mob.setAgile(Long.valueOf(option));
        } else if (string.contains("智力")) {
            mob.setWisdom(Long.valueOf(option));
        } else if (string.contains("攻速")) {
            mob.setASPD(Double.valueOf(option));
        } else if (string.contains("施法速度")) {
            mob.setDSPD(Double.valueOf(option));
        } else if (string.contains("经验")) {
            mob.setExp(Long.valueOf(option));
        } else if (string.contains("金钱")) {
            mob.setMoney(Long.valueOf(option));
        }else if (string.contains("重置")) {
           
        }
        
        else if (string.contains("展示")) {
            String goods = "";
            for (Map.Entry<String, Good> entry : mob.getGoods().entrySet()) {
                goods = goods + entry.getKey() + entry.getValue().getNumber() + "\n";
            }

            message = message + "名称  " + mob.getName() + "\n" + "攻击  " + mob.getATK()+"\n" + "防御  " + "\n" + mob.getDEF() + "\n" + "法强  " + mob.getMATK() + "\n" + "魔抗  " + mob.getMDEF() + "\n" + "力量  " + mob.getStrength() + "\n" + "敏捷  " + mob.getAgile() + "\n" + "智力  " + mob.getWisdom() + "\n" + "攻速  " + mob.getASPD() + "\n" + "施法速度  " + mob.getDSPD() + "\n" + "经验值  " + mob.getExp() + "\n" + "金币  " + mob.getMoney()
                    + "物品  " + goods;
        } else if (string.contains("物品")) {
            mob.getGoods().put(option, goods.get(option).clone());
        } else if (string.contains("保存")) {
            SaveMobToFile(mob);
        } else if (mob.getGoods().containsKey(string)) {
            mob.getGoods().get(string).setNumber(Integer.valueOf(option));
        }
        return message;
    }

}
