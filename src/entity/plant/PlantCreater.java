package entity.plant;

import utils.GsonUtil;
import static utils.GsonUtil.GetStringFromObject;
import static utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Administrator
 */
public class PlantCreater {

    public static Plant GetPlantFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        Plant plant = new Plant("无", 1, 1, 1);
        plant.setName(jobj.getString("name"));
        plant.setAge(jobj.getIntValue("age"));
        plant.setHP(jobj.getIntValue("HP"));
        plant.setMAXHP(jobj.getIntValue("MAXHP"));
        plant.setMaxAge(jobj.getIntValue("maxAge"));
        plant.setGoods((HashMap<String,Integer>)jobj.get("goods"));
        plant.setTimes(jobj.getIntValue("times"));
        plant.setUuid(UUID.randomUUID());
        return plant;
    }

    public static void SavePlantToFile(Plant plant) throws IOException {
        File Plants = new File(ReturnPath() + "/Main/Entity/Plants");
        File playerdata = new File(Plants + "/" + plant.getName() + ".json");
        if (playerdata.exists()) {
            String string = GetStringFromObject(plant);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        } else {
            playerdata.createNewFile();
            String string = GetStringFromObject(plant);
            GsonUtil.SaveStringToJsonFile(string, playerdata);
        }
    }
}
