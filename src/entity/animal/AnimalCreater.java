/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Animal;

import Utils.GsonUtil;
import static Utils.GsonUtil.GetStringFromObject;
import static Utils.Initization.ReturnPath;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Administrator
 */
public class AnimalCreater {
    //从文件获取动物
    public static Animal GetAnimalFromFile(String filepath) {
        String message = GsonUtil.readJsonFile(filepath);
        JSONObject jobj = JSON.parseObject(message);
        Animal animal = new Animal("无", 1, 1, 1);
        animal.setName(jobj.getString("name"));
        animal.setAge(jobj.getIntValue("age"));
        animal.setHP(jobj.getIntValue("HP"));
        animal.setMAXHP(jobj.getIntValue("MAXHP"));
        animal.setMaxage(jobj.getIntValue("maxage"));
        animal.setGoods((HashMap<String, Integer>) jobj.get("goods"));
        animal.setForage((Map<String, Integer>) jobj.get("forage"));

        animal.setTimes(jobj.getIntValue("times"));
        animal.setUuid(UUID.randomUUID());
        return animal;
    }

    public static void SaveAnimalToFile(Animal animal) throws IOException {
        File Animals = new File(ReturnPath() + "/data/Main/Entity/Animals");
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
