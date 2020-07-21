package entity.plant;

import utils.GsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import static utils.GsonUtil.getStringFromObject;
import static utils.Initialization.returnPath;

/**
 * 创造作物
 * @author Administrator
 */
public class PlantCreater {
    private PlantCreater() {}
    
    /**
     * 从文件中读取植物
     * @param filepath 文件路径
     * @return 植物
     */
    public static Plant getPlantFromFile(String filepath) throws IOException {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        Plant plant = new Plant("无", 1, 1, 1);
        plant.setName(jobj.getString("name"));
        plant.setAge(jobj.getIntValue("age"));
        plant.setHP(jobj.getIntValue("HP"));
        plant.setMaxHP(jobj.getIntValue("maxHP"));
        plant.setMaxAge(jobj.getIntValue("maxAge"));
        plant.setGoods((HashMap<String,Integer>)jobj.get("goods"));
        plant.setTimes(jobj.getIntValue("times"));
        plant.setUuid(UUID.randomUUID());
        return plant;
    }
    
    /**
     * 将植物保存成文件
     * @param plant 植物
     * @throws IOException 
     */
    public static void savePlantToFile(Plant plant) throws IOException {
        File playerData = new File(returnPath() + "/Main/Entity/Plants/" + plant.getName() + ".json");
        if(!playerData.exists()){
            playerData.createNewFile();
        }
        GsonUtil.saveStringToJsonFile(getStringFromObject(plant), playerData);
    }
}
