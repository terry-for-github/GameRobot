package utils;

import entity.animal.Animal;
import entity.animal.AnimalCreater;
import entity.mobs.Mob;
import entity.mobs.MobCreater;
import entity.plant.Plant;
import entity.plant.PlantCreater;
import entity.player.Player;
import entity.player.PlayerCreater;
import gameevent.GameEvent;
import gameevent.QueryImage;
import gameevent.Reload;
import goods.Good;
import goods.GoodCreater;
import permission.AdminGroup;
import permission.BlackListGroup;
import permission.OpGroup;
import permission.PermissionGroup;
import permission.PermissionManager;
import permission.UserGroup;
import static utils.GsonUtil.formatJson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import gamerobot.GameRobot;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import static gamerobot.GameRobot.gameEvents;
import java.net.MalformedURLException;

/**
 * 初始化
 * @author Administrator
 */
public class Initialization {
    private Initialization() {}
    
    public static Random random = new Random();//随机器 用来取随机数
    static Timer timer = new Timer();
    static Timer time = new Timer();

    /**
     * 初始化
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     * @throws Exception 
     */
    public static void Initialization() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, IllegalArgumentException, Exception {
        File main = new File(returnPath() + "/Main");
        if (!main.exists()) {
            main.mkdirs();
        }
        initConfig();
        initPermission();
        initGood();
        initPlayer();
        initMob();
        initPlant();
        initAnimal();
        initPlugin();
        initTrigger();
        saveAll();
    }
    
    private static void initConfig() throws IOException{
        //游戏时刻读取并且开始轮转
        File config = new File(returnPath() + "/config.json");
        if (!config.exists()) {
            config.createNewFile();
            JSONObject blackList = new JSONObject();
            blackList.put("Time", 0);
            String blackListString = formatJson(blackList.toString());
            Writer write = new OutputStreamWriter(new FileOutputStream(config), "UTF-8");
            write.write(blackListString);
            write.flush();
            write.close();
        } else {
            String message = GsonUtil.readJsonFile(config.getPath());
            JSONObject jobj = JSON.parseObject(message);
            GameRobot.time = jobj.getLongValue("Time");
        }
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                GameRobot.time = GameRobot.time + 100;
            }
        }, 0, 100);
    }
    
    private static void initPermission() throws IOException{
        File permission = new File(returnPath() + "/Main/Permission");
        if (!permission.exists()) {
            permission.mkdirs();
        }
        
        File adminGroup = new File(returnPath() + "/Main/Permission/所有者.json");
        if (!adminGroup.exists()) {
            adminGroup.createNewFile();
            GameRobot.groups.put("所有者", new AdminGroup());

        } else {
            GameRobot.groups.put("所有者", new AdminGroup());
            GameRobot.groups.get("所有者").setEvents(PermissionManager.getEvents(adminGroup));
            GameRobot.groups.get("所有者").setMembers(PermissionManager.getMembers(adminGroup));
        }
        
        File userGroup = new File(returnPath() + "/Main/Permission/用户.json");
        if (!userGroup.exists()) {
            userGroup.createNewFile();
            GameRobot.groups.put("用户", new UserGroup());
        } else {
            GameRobot.groups.put("用户", new UserGroup());
            GameRobot.groups.get("用户").setEvents(PermissionManager.getEvents(userGroup));
            GameRobot.groups.get("用户").setMembers(PermissionManager.getMembers(userGroup));
        }
        
        File opGroup = new File(returnPath() + "/Main/Permission/管理员.json");
        if (!opGroup.exists()) {
            opGroup.createNewFile();
            GameRobot.groups.put("管理员", new OpGroup());
        } else {
            GameRobot.groups.put("管理员", new OpGroup());
            GameRobot.groups.get("管理员").setEvents(PermissionManager.getEvents(opGroup));
            GameRobot.groups.get("管理员").setMembers(PermissionManager.getMembers(opGroup));
        }
        
        File blackListGroup = new File(returnPath() + "/Main/Permission/黑名单.json");
        if (!blackListGroup.exists()) {
            blackListGroup.createNewFile();
            GameRobot.groups.put("黑名单", new BlackListGroup());
        } else {
            GameRobot.groups.put("黑名单", new BlackListGroup());
            GameRobot.groups.get("黑名单").setEvents(PermissionManager.getEvents(blackListGroup));
            GameRobot.groups.get("黑名单").setMembers(PermissionManager.getMembers(blackListGroup));
        }
        
        System.out.println("======================正在加载权限======================");
        for (Map.Entry<String, GameEvent> entry : gameEvents.entrySet()) {
            File thePermission = new File(permission.getPath() + "/" + entry.getValue().getMessage() + ".json");
            System.out.println("正在加载  " + entry.getValue().getMessage() + "  权限");
            if (new File(permission.getPath() + "/" + entry.getValue().getMessage()).exists()) {
                entry.getValue().setIsopen(PermissionManager.getIsOpen(thePermission));
                entry.getValue().setPermissions(PermissionManager.getPermission(thePermission));
                entry.getValue().setOpen(PermissionManager.getOpen(thePermission));
            } else {
                entry.getValue().setIsopen(true);
                entry.getValue().setOpen(false);
                entry.getValue().setPermissions(new HashMap<>());
                PermissionManager.savePerMission(entry.getValue(), thePermission);
            }
        }
        System.out.println("======================加载权限完毕======================");
    }
    
    private static void initGood() throws IOException{
        File goodsFile = new File(returnPath() + "/Main/Goods");
        if (!goodsFile.exists()) {
            goodsFile.mkdirs();
        }
        Good good;
        System.out.println("======================正在初始化物品数据======================");
        File[] goodFileList = goodsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (goodFileList.length != 0) {
            for (File goodFile : goodFileList) {
                //遍历File[]数组
                if (!goodFile.isDirectory()) {
                    System.out.println("       " + "正在初始化物品  " + goodFile.getName());
                    good = GoodCreater.getGoodFromFile(goodFile.getPath());
                    GameRobot.goods.put(good.getName(), good);
                }
            }
        }
        System.out.println("======================初始化物品数据完毕======================");
    }
    
    private static void initPlayer() throws IOException{
        File playersFile = new File(returnPath() + "/Saves/Players");
        if (!playersFile.exists()) {
            playersFile.mkdirs();
        }
        Player player;
        System.out.print("======================正在初始化玩家数据======================\n");
        File[] playerFileList = playersFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (playerFileList.length != 0) {
            for (File playerFile : playerFileList) {
                //遍历File[]数组
                if (playerFile.isDirectory()) {
                    System.out.println("       " + "正在初始化玩家  " + playerFile.getName());
                    player = PlayerCreater.getPlayerFromFile(playerFile.getPath() + "/" + playerFile.getName() + ".json");
                    GameRobot.players.put(player.getName(), player);
                }
            }
        }
        System.out.print("======================初始化玩家数据完毕======================\n");
    }
    
    private static void initMob() throws IOException{
        File mobsFile = new File(returnPath() + "/Main/Entity/Mobs");
        if (!mobsFile.exists()) {
            mobsFile.mkdirs();
        }
        Mob mob;
        System.out.print("======================正在初始化怪物数据======================\n");
        File[] mobFileList = mobsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (mobFileList.length != 0) {
            for (File mobFile : mobFileList) {
                //遍历File[]数组
                if (!mobFile.isDirectory()) {
                    System.out.println("       " + "正在初始化怪物  " + mobFile.getName());
                    mob = MobCreater.getMobFromFile(mobFile.getPath());
                    GameRobot.mobs.put(mob.getName(), mob);
                }
            }
        }
        System.out.print("======================初始化怪物数据完毕======================\n");
    }
    
    private static void initPlant() throws IOException{
        File plantsFile = new File(returnPath() + "/Main/Entity/Plants");
        if (!plantsFile.exists()) {
            plantsFile.mkdirs();
        }
        Plant plant;
        System.out.println("======================正在初始化植物数据======================");
        File[] plantFileList = plantsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (plantFileList.length != 0) {
            for (File plantFile : plantFileList) {
                //遍历File[]数组
                if (!plantFile.isDirectory()) {
                    System.out.println("       " + "正在初始化植物  " + plantFile.getName());
                    plant = PlantCreater.getPlantFromFile(plantFile.getPath());
                    GameRobot.plants.put(plant.getName(), plant);
                }
            }
        }
        System.out.println("======================初始化植物数据完毕======================");
    }
    
    private static void initAnimal() throws IOException{
        File animalsFile = new File(returnPath() + "/Main/Entity/Animals");
        if (!animalsFile.exists()) {
            animalsFile.mkdirs();
        }
        Animal animal;
        System.out.println("======================正在初始化动物数据======================");
        File[] animalFileList = animalsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (animalFileList.length != 0) {
            for (File animalFile : animalFileList) {
                //遍历File[]数组
                if (!animalFile.isDirectory()) {
                    System.out.println("       " + "正在初始化动物  " + animalFile.getName());
                    animal = AnimalCreater.getAnimalFromFile(animalFile.getPath());
                    GameRobot.animals.put(animal.getName(), animal);
                }
            }
        }
        System.out.println("======================初始化动物数据完毕======================");
    }
    
    private static void initPlugin() throws ClassNotFoundException, InstantiationException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException{
        File plugins = new File(returnPath() + "/Plugins");
        if (!plugins.exists()) {
            plugins.mkdirs();
        }
        System.out.println("======================正在加载插件======================");
        File[] pluginFileList = plugins.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (pluginFileList.length != 0) {
            for (File pluginFile : pluginFileList) {
                //遍历File[]数组
                if (!pluginFile.isDirectory()) {
                    System.out.println("       " + "正在加载插件  " + pluginFile.getName());
                    String packagename = pluginFile.getName().replace(".jar", "");
                    loadJar(pluginFile.getPath());
                    Class<?> aClass = Class.forName(packagename.toLowerCase() + "." + packagename);
                    Object instance = aClass.newInstance();
                    aClass.getDeclaredMethod("main", String[].class).invoke(instance, (Object) new String[]{});
                }
            }
        }
        System.out.println("======================加载插件完毕======================");
    }
    
    private static void initTrigger(){
        System.out.println("======================正在加载触发======================");
//        BuildEvent buildevent = new BuildEvent();
        QueryImage queryImage = new QueryImage();
        Reload reload = new Reload();
        System.out.println("======================加载触发完毕======================");
    }

    /**
     * 返回保存数据的母路径
     * @return 母路径
     */
    public static String returnPath() {
        // TODO 为什么记录日志后不再抛出一个错误而正常地返回一个空串
        try{
            return new File("").getCanonicalPath() + "/data";
        } catch (IOException ex){
            Logger.getLogger(Initialization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public static void saveAll() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.print("=======================================正在自动保存=======================================\n");
                System.out.print("===========正在保存玩家数据=========\n");
                HashMap<String, Player> thePlayers = new HashMap();
                thePlayers.putAll(GameRobot.players);
                for (Map.Entry<String, Player> entry : thePlayers.entrySet()) {
                    Player player;
                    try {
                        player = ((Player) entry.getValue()).clone();
                        PlayerCreater.savePlayerToFile(Long.valueOf(player.getName()), player);
                    } catch (IOException | CloneNotSupportedException ex) {
                        Logger.getLogger(Initialization.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                System.out.print("===========正在保存权限与权限组=========\n");
                File Permission = new File(returnPath() + "/Main/Permission");
                for (Map.Entry<String, GameEvent> entry : gameEvents.entrySet()) {
                    File thepermission = new File(Permission.getPath() + "/" + entry.getValue().getMessage() + ".json");
                    System.out.print("正在保存" + entry.getValue().getMessage() + "  权限\n");
                    if (new File(Permission.getPath() + "/" + entry.getValue().getMessage() + ".json").exists()) {
                        PermissionManager.savePerMission(entry.getValue(), thepermission);
                    }
                }
                for (Map.Entry<String, PermissionGroup> entry : GameRobot.groups.entrySet()) {
                    File thegroup = new File(returnPath() + "/Main/Permission/" + entry.getKey() + ".json");
                    PermissionManager.saveGroup(entry.getValue(), thegroup);
                }

                System.out.print("===========保存权限与权限组完毕=========\n");

                System.out.print("===========����������=========\n");
                System.out.print("===========����������=========\n");

                System.out.print("===========����������=========\n");

                System.out.print("===========��������    =========\n");

                File Plugins = new File(returnPath() + "/Plugins");
                File[] pluginsfs = Plugins.listFiles();	//遍历path下的文件和目录，放在File数组中
                if (pluginsfs.length != 0) {
                    for (File s : pluginsfs) {
                        //遍历File[]数组
                        if (!s.isDirectory()) {
                            try {
                                System.out.println("       " + "保存插件" + s.getName());
                                String packagename = s.getName().replace(".jar", "");
                                loadJar(s.getPath());
                                Class<?> aClass = Class.forName(packagename.toLowerCase() + "." + packagename);
                                Object instance = aClass.newInstance();
//                                aClass.getDeclaredMethod("Save", String[].class).invoke(instance, (Object) new String[]{});
                                aClass.getMethod("Save").invoke(instance);
                            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | MalformedURLException ex) {
                                Logger.getLogger(Initialization.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

                System.out.print("=======================================\n自动保存完成\n=======================================\n");

            }

        }, 10000L, 600000L);
    }

    public static void reload() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, Exception {
        time.cancel();
        timer.cancel();
        saveAll();
        Initialization();
    }

    public static void loadJar(String jarPath) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, NoSuchMethodException {
        File jarFile = new File(jarPath);
        // 从URLClassLoader类中获取类所在文件夹的方法，jar也可以认为是一个文件夹
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        // 获取方法的访问权限以便写回
        boolean accessible = method.isAccessible();
        method.setAccessible(true);
        // 获取系统类加载器
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        URL url = jarFile.toURI().toURL();
        method.invoke(classLoader, url);
        method.setAccessible(accessible);
    }
}
