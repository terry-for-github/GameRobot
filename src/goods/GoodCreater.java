package goods;

import goods.consumables.Consumables;
import goods.armor.ArmorType;
import goods.armor.Armor;
import utils.GsonUtil;
import static utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import static gamerobot.GameRobot.goods;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static utils.GsonUtil.getStringFromObject;

/**
 * 创造货物
 * @author Administrator
 */
public class GoodCreater {
    private GoodCreater() {}
    
    /**
     * 从文件初始化物品
     * @param filepath 文件路径
     * @return 货物
     */
    public static Good getGoodFromFile(String filepath) {
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

    /**
     * 从JSON获取物品
     * @param jobj 
     * @return 
     */
    public static Good getGoodFromJsonObject(JSONObject jobj) {
        Consumables consumables = new Consumables("无", "无");
        Armor armor = new Armor("无", "无");
        if (jobj.getString("type").contains("消耗品")) {
            consumables.setName(jobj.getString("name"));
            consumables.setNumber(jobj.getInteger("number"));
            consumables.setType(jobj.getString("type"));
            consumables.setUse(jobj.getString("use"));
            return consumables;
        } else {
            armor.setArmorType(ArmorType.valueOf(jobj.getString("armorType")));
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
    
    /**
     * 将文件获取的HASHMAP转换成物品的MAP
     * @param goods
     * @return 
     */
    public static Map<String, Good> stringToGoods(Map<String, String> goods) {
        Map map = new HashMap<>();
        goods.entrySet().forEach(entry -> {
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
                armor.setArmorType(ArmorType.valueOf(json.getString("armorType")));
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
        });
        return map;
    }
    
    /**
     * 把装备保存为装备
     * @param armor 装备
     * @throws IOException 
     */
    public static void saveArmorToFile(Armor armor) throws IOException {
        File playerData = new File(ReturnPath() + "/Main/Goods/" + armor.getName() + ".json");
        if(!playerData.exists()){
            playerData.createNewFile();
        }
        GsonUtil.saveStringToJsonFile(getStringFromObject(armor), playerData);
    }

    /**
     * 将消耗品保存为文件
     * @param consumables 消耗品
     * @throws IOException 
     */
    public static void saveConsumablesToFile(Consumables consumables) throws IOException {
        File playerData = new File(ReturnPath() + "/Main/Goods/" + consumables.getName() + ".json");
        if(!playerData.exists()){
            playerData.createNewFile();
        }
        GsonUtil.saveStringToJsonFile(getStringFromObject(consumables), playerData);
    }

    public static HashMap<String, Good> getGoodsFromHashMap(HashMap<String, Integer> good) throws CloneNotSupportedException {
        HashMap<String, Good> map = new HashMap<>();
        for (Map.Entry<String, Integer> entry : good.entrySet()) {
            if (goods.get(entry.getKey()).getType().contains("消耗品") || goods.get(entry.getKey()).getType().contains("材料")) {
                Consumables consumables = (Consumables) goods.get(entry.getKey()).clone();
                consumables.setNumber(entry.getValue());
                map.put(entry.getKey(), consumables);
            } else {
                Armor armor = (Armor) goods.get(entry.getKey()).clone();
                armor.setNumber(entry.getValue());
                map.put(entry.getKey(), armor);
            }
        }
        return map;
    }
}
