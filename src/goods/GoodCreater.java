package goods;

import goods.consumables.Consumables;
import goods.armor.ArmorType;
import goods.armor.Armor;
import utils.GsonUtil;
import static utils.GsonUtil.GetStringFromObject;
import static utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import static gamerobot.GameRobot.goods;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Administrator
 */
public class GoodCreater {
    
    
    //从文件初始化物品
    public static Good GetGoodFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        Consumables consumables = new Consumables("无", "无");
        Armor armor = new Armor("无", "无");

        if (jobj.getString("type").contains("消耗品")) {
            consumables.setName(jobj.getString("name"));
            consumables.setNumber(jobj.getInteger("number"));
            consumables.setType(jobj.getString("type"));
            consumables.setUse(jobj.getString("use"));
            return consumables;
        } else {
            armor.setUuid(UUID.fromString(jobj.getString("uuid")));
            armor.setName(jobj.getString("name"));
            armor.setNumber(1);
            armor.setType(jobj.getString("type"));
            armor.setUse(jobj.getString("use"));
            armor.setATK(jobj.getLongValue("ATK"));
            armor.setDEF(jobj.getLongValue("DEF"));
            armor.setMATK(jobj.getLongValue("MATK"));
            armor.setMDEF(jobj.getLongValue("MDEF"));
            armor.setASPD(jobj.getDoubleValue("ASPD"));
            armor.setDSPD(jobj.getDoubleValue("DSPD"));
            armor.setStrength(jobj.getLongValue("strength"));
            armor.setAgile(jobj.getLongValue("agile"));
            armor.setWisdom(jobj.getLongValue("wisdom"));
            return armor;
        }
    }

    
    //从JSON获取物品
    public static Good GetGoodFromJsonObject(JSONObject jobj) {
        Consumables consumables = new Consumables("无", "无");
        Armor armor = new Armor("无", "无");
        if (jobj.getString("type").contains("消耗品")) {
            consumables.setName(jobj.getString("name"));
            consumables.setNumber(jobj.getInteger("number"));
            consumables.setType(jobj.getString("type"));
            consumables.setUse(jobj.getString("use"));
            return consumables;
        } else {
            armor.setArmortype(ArmorType.valueOf(jobj.getString("armortype")));
            armor.setUuid(UUID.fromString(jobj.getString("uuid")));
            armor.setName(jobj.getString("name"));
            armor.setNumber(1);
            armor.setType(jobj.getString("type"));
            armor.setUse(jobj.getString("use"));
            armor.setATK(jobj.getLongValue("ATK"));
            armor.setDEF(jobj.getLongValue("DEF"));
            armor.setMATK(jobj.getLongValue("MATK"));
            armor.setMDEF(jobj.getLongValue("MDEF"));
            armor.setASPD(jobj.getDoubleValue("ASPD"));
            armor.setDSPD(jobj.getDoubleValue("DSPD"));
            armor.setStrength(jobj.getLongValue("strength"));
            armor.setAgile(jobj.getLongValue("agile"));
            armor.setWisdom(jobj.getLongValue("wisdom"));
            return armor;
        }
    }
    
    //将文件获取的HASHMAP转换成物品的MAP
    public static Map<String, Good> StringToGoods(Map<String, String> goods) {
        Map map = new HashMap<>();
        for (Map.Entry<String, String> entry : goods.entrySet()) {
            Consumables consumables = new Consumables("无", "无");
            Armor armor = new Armor("无", "无");
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(entry.getValue()));
            if (json.getString("type").contains("消耗品")) {
                consumables.setName(json.getString("name"));
                consumables.setNumber(json.getIntValue("number"));
                consumables.setType(json.getString("type"));
                consumables.setUse(json.getString("use"));
                map.put(entry.getKey(), consumables);
            } else {
                armor.setUuid(UUID.fromString(json.getString("uuid")));
                armor.setArmortype(ArmorType.valueOf(json.getString("armortype")));
                armor.setName(json.getString("name"));
                armor.setNumber(json.getIntValue("number"));
                armor.setType(json.getString("type"));
                armor.setUse(json.getString("use"));
                armor.setATK(json.getLongValue("ATK"));
                armor.setDEF(json.getLongValue("DEF"));
                armor.setMATK(json.getLongValue("MATK"));
                armor.setMDEF(json.getLongValue("MDEF"));
                armor.setASPD(json.getDoubleValue("ASPD"));
                armor.setDSPD(json.getDoubleValue("DSPD"));
                armor.setStrength(json.getLongValue("strength"));
                armor.setAgile(json.getLongValue("agile"));
                armor.setWisdom(json.getLongValue("wisdom"));
                map.put(entry.getKey(), armor);
            }

        }
        return map;
    }
    
    
    
    public static void SaveArmorToFile(Armor armor) throws IOException {
        File Goods = new File(ReturnPath() + "/Main/Goods");

        File playerdata = new File(Goods + "/" + armor.getName() + ".json");
        if (playerdata.exists()) {
            String string = GetStringFromObject(armor);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        } else {
            playerdata.createNewFile();
            String string = GetStringFromObject(armor);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        }
    }

    public static void SaveConsumablesToFile(Consumables consumables) throws IOException {
        File Goods = new File(ReturnPath() + "/Main/Goods");
        Gson gson = new Gson();
        File playerdata = new File(Goods + "/" + consumables.getName() + ".json");
        if (playerdata.exists()) {
            String string = GetStringFromObject(consumables);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        } else {
            playerdata.createNewFile();
            String string = GetStringFromObject(consumables);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        }
    }

    public static HashMap<String, Good> GetGoodsFromHashMap(HashMap<String, Integer> good) {
        HashMap<String, Good> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry : good.entrySet()) {
            Consumables consumables = new Consumables("无", "无");
            Armor armor = new Armor("无", "无");
            if (goods.get(entry.getKey()).getType().contains("消耗品") || goods.get(entry.getKey()).getType().contains("材料")) {
                consumables = (Consumables) goods.get(entry.getKey()).clone();
                consumables.setNumber(entry.getValue());
                map.put(entry.getKey(), consumables);
            } else {
                armor = (Armor) goods.get(entry.getKey()).clone();
                armor.setNumber(entry.getValue());
                map.put(entry.getKey(), armor);
            }
        }
        return map;
    }
}
