package entity.mobs;

import goods.Good;
import utils.GsonUtil;
import static utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import static utils.GsonUtil.getStringFromObject;
import static goods.GoodCreater.stringToGoods;

/**
 * 创造怪物
 * @author Administrator
 */
public class MobCreater {
    private MobCreater() {}
    
    /**
     * 从文件获取怪物
     * @param filepath 文件路径
     * @return 怪物
     */
    public static Mob getMobFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        String name = jobj.getString("name");
        long HP = jobj.getLongValue("HP");
        long maxHP = jobj.getLongValue("maxHP");
        long ATK = jobj.getLongValue("ATK");
        long DEF = jobj.getLongValue("DEF");
        long MATK = jobj.getLongValue("MATK");
        long MDEF = jobj.getLongValue("MDEF");
        long strength = jobj.getLongValue("strength");
        long agile = jobj.getLongValue("agile");
        long wisdom = jobj.getLongValue("wisdom");
        long money = jobj.getLongValue("money");
        long exp = jobj.getLongValue("exp");
        Map<String, Good> goods = stringToGoods((Map<String, String>) jobj.get("goods"));
        double ASPD = jobj.getDouble("ASPD");
        double DSPD = jobj.getDouble("DSPD");

        Mob mob = new Mob(name, ATK, DEF, MATK, MDEF, ASPD, DSPD, strength, agile, wisdom);
        mob.setExp(exp);
        mob.setMoney(money);
        mob.setGoods(goods);
        mob.setUuid(UUID.randomUUID());
        mob.setHP(HP);
        mob.setMaxHP(maxHP);
        return mob;
    }
    
    /**
     * 将怪物保存为文件
     * @param mob 怪物
     * @throws IOException 
     */
    public static void saveMobToFile(Mob mob) throws IOException {
        File playerData = new File(ReturnPath() + "/Main/Entity/Mobs/" + mob.getName() + ".json");
        if(!playerData.exists()){
            playerData.createNewFile();
        }
        GsonUtil.saveStringToJsonFile(getStringFromObject(mob), playerData);
    }
}
