package entity.animal;

import utils.GsonUtil;
import static utils.GsonUtil.GetStringFromObject;
import static utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 创造动物
 * @author Administrator
 */
public class AnimalCreater {
    private AnimalCreater() {}
    
    /**
     * 从文件获取动物
     * @param filepath 文件路径
     * @return 
     */
    public static Animal getAnimalFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        Animal animal = new Animal("无", 1, 1, 1);
        animal.setName(jobj.getString("name"));
        animal.setAge(jobj.getIntValue("age"));
        animal.setHP(jobj.getIntValue("HP"));
        animal.setMAXHP(jobj.getIntValue("MAXHP"));
        animal.setMaxAge(jobj.getIntValue("maxAge"));
        animal.setGoods((HashMap<String, Integer>) jobj.get("goods"));
        animal.setForage((Map<String, Integer>) jobj.get("forage"));
        animal.setTimes(jobj.getIntValue("times"));
        animal.setUuid(UUID.randomUUID());
        return animal;
    }
    
    /**
     * 把动物保存到文件中
     * @param animal
     * @throws IOException 
     */
    public static void saveAnimalToFile(Animal animal) throws IOException {
        File Animals = new File(ReturnPath() + "/Main/Entity/Animals");
        File playerdata = new File(Animals + "/" + animal.getName() + ".json");
        if (playerdata.exists()) {
            String string = GetStringFromObject(animal);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        } else {
            playerdata.createNewFile();
            String string = GetStringFromObject(animal);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        }
    }
}
