package entity.animal;

import utils.GsonUtil;
import static utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static utils.GsonUtil.getStringFromObject;

/**
 * 创造动物
 * @author Administrator
 */
public class AnimalCreater {
    private AnimalCreater() {}
    
    /**
     * 从文件获取动物
     * @param filepath 文件路径
     * @return 动物
     */
    public static Animal getAnimalFromFile(String filepath) throws IOException {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        Animal animal = new Animal("无", 1, 1, 1);
        animal.setName(jobj.getString("name"));
        animal.setAge(jobj.getIntValue("age"));
        animal.setHP(jobj.getIntValue("HP"));
        animal.setMaxHP(jobj.getIntValue("maxHP"));
        animal.setMaxAge(jobj.getIntValue("maxAge"));
        animal.setGoods((HashMap<String, Integer>) jobj.get("goods"));
        animal.setForage((Map<String, Integer>) jobj.get("forage"));
        animal.setTimes(jobj.getIntValue("times"));
        animal.setUuid(UUID.randomUUID());
        return animal;
    }
    
    /**
     * 把动物保存到文件中
     * @param animal 动物
     * @throws IOException 
     */
    public static void saveAnimalToFile(Animal animal) throws IOException {
        File playerData = new File(ReturnPath() + "/Main/Entity/Animals/" + animal.getName() + ".json");
        if (!playerData.exists()) {
            playerData.createNewFile();
        }
        GsonUtil.saveStringToJsonFile(getStringFromObject(animal), playerData);
    }
}
