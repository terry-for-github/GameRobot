package utils;

import entity.animal.Animal;
import entity.animal.AnimalCreater;
import entity.mobs.Mob;
import entity.mobs.MobCreater;
import entity.plant.Plant;
import entity.plant.PlantCreater;
import entity.player.Player;
import entity.player.PlayerCreater;
import entity.player.PlayerManager;
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
import static gamerobot.GameRobot.gameevents;
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

/**
 *
 * @author Administrator
 */
public class Initization {
    
    public static Random random=new Random();//随机器 用来取随机数
    static Timer timer = new Timer();
    static Timer time = new Timer();
    static PermissionManager permissionmanager = new PermissionManager();

    public static void Initization() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, IllegalArgumentException, Exception {

        File Main = new File(ReturnPath() + "/data/Main");
        File Plugins = new File(ReturnPath() + "/data/Plugins");
        File MobsFile = new File(ReturnPath() + "/data/Main/Entity/Mobs");
        File PlantsFile = new File(ReturnPath() + "/data/Main/Entity/Plants");
        File AnimalsFile = new File(ReturnPath() + "/data/Main/Entity/Animals");
        File Pets = new File(ReturnPath() + "/data/Main/Entity/Pets");
        File Boss = new File(ReturnPath() + "/data/Main/Entity/Boss");
        File GoodsFile = new File(ReturnPath() + "/data/Main/Goods");
        File PlayersFile = new File(ReturnPath() + "/data/Saves/Players");
        File Permission = new File(ReturnPath() + "/data/Main/Permission");

        File Admingroup = new File(ReturnPath() + "/data/Main/Permission/所有者.json");
        File Usergroup = new File(ReturnPath() + "/data/Main/Permission/用户.json");
        File OPgroup = new File(ReturnPath() + "/data/Main/Permission/管理员.json");
        File BlackListgroup = new File(ReturnPath() + "/data/Main/Permission/黑名单.json");

        //
        //创建所有文件夹
        if (!Plugins.exists()) {
            Plugins.mkdirs();
        }
        if (!Permission.exists()) {
            Permission.mkdirs();
        }
        if (!Main.exists()) {
            Main.mkdirs();
        }
        if (!PlayersFile.exists()) {
            PlayersFile.mkdirs();
        }
        if (!Boss.exists()) {
            Boss.mkdirs();
        }
        if (!MobsFile.exists()) {
            MobsFile.mkdirs();
        }
        if (!PlantsFile.exists()) {
            PlantsFile.mkdirs();
        }
        if (!AnimalsFile.exists()) {
            AnimalsFile.mkdirs();
        }
        if (!Pets.exists()) {
            Pets.mkdirs();
        }
        if (!GoodsFile.exists()) {
            GoodsFile.mkdirs();
        }

        if (!Admingroup.exists()) {
            Admingroup.createNewFile();
            GameRobot.groups.put("所有者", new AdminGroup());

        } else {
            GameRobot.groups.put("所有者", new AdminGroup());
            GameRobot.groups.get("所有者").setEvents(permissionmanager.GetEvents(Admingroup));
            GameRobot.groups.get("所有者").setMembers(permissionmanager.GetMembers(Admingroup));
//            System.out.println("所有者是");
//            for (Map.Entry<Long, String> entry : GameRobot.groups.get("所有者").getMembers().entrySet()) {
//                System.out.println(entry.getKey());
//            }

        }
        if (!Usergroup.exists()) {
            Usergroup.createNewFile();
            GameRobot.groups.put("用户", new UserGroup());
        } else {
            GameRobot.groups.put("用户", new UserGroup());
            GameRobot.groups.get("用户").setEvents(permissionmanager.GetEvents(Usergroup));
            GameRobot.groups.get("用户").setMembers(permissionmanager.GetMembers(Usergroup));
        }
        if (!OPgroup.exists()) {
            OPgroup.createNewFile();
            GameRobot.groups.put("管理员", new OpGroup());
        } else {
            GameRobot.groups.put("管理员", new OpGroup());
            GameRobot.groups.get("管理员").setEvents(permissionmanager.GetEvents(OPgroup));
            GameRobot.groups.get("管理员").setMembers(permissionmanager.GetMembers(OPgroup));
        }
        if (!BlackListgroup.exists()) {
            BlackListgroup.createNewFile();
            GameRobot.groups.put("黑名单", new BlackListGroup());
        } else {
            GameRobot.groups.put("黑名单", new BlackListGroup());
            GameRobot.groups.get("黑名单").setEvents(permissionmanager.GetEvents(BlackListgroup));
            GameRobot.groups.get("黑名单").setMembers(permissionmanager.GetMembers(BlackListgroup));
        }

        //游戏时刻读取并且开始轮转
        File config = new File(ReturnPath() + "/data/config.json");
        if (!config.exists()) {
            try {
                config.createNewFile();
                Writer write;
                JSONObject blacklist = new JSONObject();
                blacklist.put("Time", 0);
                String blackliststring = formatJson(blacklist.toString());
                write = new OutputStreamWriter(new FileOutputStream(config), "UTF-8");
                write.write(blackliststring);
                write.flush();
                write.close();
            } catch (IOException ex) {
                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        Good good;
        System.out.print("======================正在初始化物品数据======================\n");
        File[] goodfs = GoodsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (goodfs.length != 0) {
            for (File s : goodfs) {
                //遍历File[]数组
                if (!s.isDirectory()) {
                    System.out.println("       " + "正在初始化物品  " + s.getName());
                    good = GoodCreater.GetGoodFromFile(s.getPath());
                    GameRobot.goods.put(good.getName(), good);
                }
            }
        }
        System.out.print("======================初始化物品数据完毕======================\n");

        Player player;
        System.out.print("======================正在初始化玩家数据======================\n");
        File[] playerfs = PlayersFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (playerfs.length != 0) {
            for (File s : playerfs) {
                //遍历File[]数组
                if (s.isDirectory()) {
                    System.out.println("       " + "正在初始化玩家  " + s.getName());
                    player = PlayerCreater.GetPlayerFromFile(s.getPath() + "/" + s.getName() + ".json");
                    GameRobot.players.put(player.getName(), player);
                }
            }
        }

        System.out.print("======================初始化玩家数据完毕======================\n");
        Mob mob;
        System.out.print("======================正在初始化怪物数据======================\n");
        File[] mobsfs = MobsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (mobsfs.length != 0) {
            for (File s : mobsfs) {
                //遍历File[]数组
                if (!s.isDirectory()) {
                    System.out.println("       " + "正在初始化怪物  " + s.getName());
                    mob = MobCreater.GetMobFromFile(s.getPath());
                    GameRobot.mobs.put(mob.getName(), mob);
                }
            }
        }

        System.out.print("======================初始化怪物数据完毕======================\n");

        Plant plant;
        System.out.print("======================正在初始化植物数据======================\n");
        File[] palntfs = PlantsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (palntfs.length != 0) {
            for (File s : palntfs) {
                //遍历File[]数组
                if (!s.isDirectory()) {
                    System.out.println("       " + "正在初始化植物  " + s.getName());
                    plant = PlantCreater.GetPlantFromFile(s.getPath());
                    GameRobot.plants.put(plant.getName(), plant);
                }
            }
        }

        System.out.print("======================初始化植物数据完毕======================\n");

        Animal animal;
        System.out.print("======================正在初始化动物数据======================\n");
        File[] animalfs = AnimalsFile.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (animalfs.length != 0) {
            for (File s : animalfs) {
                //遍历File[]数组
                if (!s.isDirectory()) {
                    System.out.println("       " + "正在初始化动物  " + s.getName());
                    animal = AnimalCreater.GetAnimalFromFile(s.getPath());
                    GameRobot.animals.put(animal.getName(), animal);
                }
            }
        }

        System.out.print("======================初始化动物数据完毕======================\n");

        System.out.print("======================正在加载插件======================\n");
        File[] pluginsfs = Plugins.listFiles();	//遍历path下的文件和目录，放在File数组中
        if (pluginsfs.length != 0) {
            for (File s : pluginsfs) {
                //遍历File[]数组
                if (!s.isDirectory()) {
                    System.out.println("       " + "正在加载插件  " + s.getName());
                    String packagename = s.getName().replace(".jar", "");
                    loadJar(s.getPath());
                    Class<?> aClass = Class.forName(packagename.toLowerCase() + "." + packagename);
                    Object instance = aClass.newInstance();
                    aClass.getDeclaredMethod("main", String[].class).invoke(instance, (Object) new String[]{});
                }
            }
        }

        System.out.print("======================加载插件完毕======================\n");

        System.out.print("======================正在加载触发======================\n");

//        BuildEvent buildevent = new BuildEvent();
        QueryImage queryimage = new QueryImage();
        Reload reload = new Reload();
        System.out.print("======================加载触发完毕======================\n");

        System.out.print("======================正在加载权限======================\n");

        for (Map.Entry<String, GameEvent> entry : gameevents.entrySet()) {
            File thepermission = new File(Permission.getPath() + "/" + entry.getValue().getMessage() + ".json");
            System.out.print("正在加载  " + entry.getValue().getMessage() + "  权限\n");
            if (new File(Permission.getPath() + "/" + entry.getValue().getMessage()).exists()) {
                entry.getValue().setIsopen(permissionmanager.GetIsOpen(thepermission));
                entry.getValue().setPermissions(permissionmanager.GetPermission(thepermission));
                entry.getValue().setOpen(permissionmanager.GetOpen(thepermission));
            } else {
                entry.getValue().setIsopen(true);
                entry.getValue().setOpen(false);
                entry.getValue().setPermissions(new HashMap<>());
                permissionmanager.SavePerMission(entry.getValue(), thepermission);
            }
        }
        System.out.print("======================加载权限完毕======================\n");

        SaveAll();
    }

    public static String ReturnPath() {
        String cf = "";
        File f = new File("");
        try {
            cf = f.getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cf;
    }

    public static void SaveAll() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.print("=======================================正在自动保存=======================================\n");
                System.out.print("===========正在保存玩家数据=========\n");
                HashMap<String, Player> theplayers = new HashMap();
                theplayers.putAll(GameRobot.players);
                for (Map.Entry<String, Player> entry : theplayers.entrySet()) {
                    Player player;
                    try {
                        player = ((Player) entry.getValue()).clone();
                        PlayerManager.SavePlayerToFile(Long.valueOf(player.getName()), player);
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                System.out.print("===========正在保存权限与权限组=========\n");
                File Permission = new File(ReturnPath() + "/data/Main/Permission");
                for (Map.Entry<String, GameEvent> entry : gameevents.entrySet()) {
                    File thepermission = new File(Permission.getPath() + "/" + entry.getValue().getMessage() + ".json");
                    System.out.print("正在保存" + entry.getValue().getMessage() + "  权限\n");
                    if (new File(Permission.getPath() + "/" + entry.getValue().getMessage() + ".json").exists()) {
                        permissionmanager.SavePerMission(entry.getValue(), thepermission);
                    }
                }
                for (Map.Entry<String, PermissionGroup> entry : GameRobot.groups.entrySet()) {
                    File thegroup = new File(ReturnPath() + "/data/Main/Permission/" + entry.getKey() + ".json");
                    permissionmanager.SaveGroup(entry.getValue(), thegroup);
                }

                System.out.print("===========保存权限与权限组完毕=========\n");

                System.out.print("===========����������=========\n");
                System.out.print("===========����������=========\n");

                System.out.print("===========����������=========\n");

                System.out.print("===========��������   =========\n");

                File Plugins = new File(ReturnPath() + "/data/Plugins");
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
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InstantiationException ex) {
                                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NoSuchMethodException ex) {
                                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SecurityException ex) {
                                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(Initization.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }

                System.out.print("=======================================\n自动保存完成\n=======================================\n");

            }

        }, 10000L, 600000L);
    }

    public static void Reload() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, Exception {
        time.cancel();
        timer.cancel();
        SaveAll();
        Initization();
    }

    public static void loadJar(String jarPath) {
        File jarFile = new File(jarPath);
        // 从URLClassLoader类中获取类所在文件夹的方法，jar也可以认为是一个文件夹
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        // 获取方法的访问权限以便写回
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            URL url = jarFile.toURI().toURL();
            method.invoke(classLoader, url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.setAccessible(accessible);
        }
    }

}
