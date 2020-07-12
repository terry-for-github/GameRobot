package gamerobot;

import entity.animal.Animal;
import entity.mobs.Mob;
import entity.plant.Plant;
import entity.player.Player;
import entity.player.PlayerCreater;
import entity.player.PlayerManager;
import gameevent.GameEvent;
import goods.Good;
import map.Chunk;
import map.Location;
import utils.Initization;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import permission.PermissionGroup;
import static utils.ImageUtils.CreateLine;
import static utils.Initization.ReturnPath;
import java.io.FileNotFoundException;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.japt.Events;
import net.mamoe.mirai.message.FriendMessage;
import net.mamoe.mirai.message.GroupMessage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;

/**
 *
 * @author Administrator
 */
public class GameRobot {

    /**
     * @param args the command line arguments
     */
    //不需要高并发的存取
    public static boolean cando = true;
    public static long time;

    //高取不存（需要被复制）
    public static Map<String, Mob> mobs = new ConcurrentHashMap<>();//所有的怪物

    public static Map<String, Plant> plants = new ConcurrentHashMap<>();//所有的植物

    public static Map<String, Good> goods = new ConcurrentHashMap<>();//所有的物品

    public static Map<String, Animal> animals = new ConcurrentHashMap<>();//所有的动物

    public static Map<String, GameEvent> gameevents = new ConcurrentHashMap<>();//所有的触发

    public static Map<String, PermissionGroup> groups = new ConcurrentHashMap<>();//所有的组

    //需要高并发的存取
    public static Map<String, Player> players = new ConcurrentHashMap<>();//所有玩家

    public static Map<Location, Chunk> chuns = new ConcurrentHashMap<>();

    //线程池
    public static ExecutorService pool = Executors.newFixedThreadPool(200);
    public static ExecutorService executorService = Executors.newCachedThreadPool();
    public static Bot bot;

    public static void main(String[] args) throws InterruptedException, IOException, IllegalAccessException, IllegalArgumentException, IllegalArgumentException, InvocationTargetException, InvocationTargetException, NoSuchMethodException, NoSuchMethodException, InstantiationException, MalformedURLException, ClassNotFoundException, ClassNotFoundException, Exception {
//

//        // 测试图片的叠加
//        overlyingImageTest();
//        // 测试图片的垂直合并
//        imageMargeTest();
        //用于作机器人的qq账户
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入账户");
//        long account = scanner.nextLong();
//        System.out.println("请输入密码");
//        String password = scanner.next();
        //机器人初始化
        bot = BotFactoryJvm.newBot(501864196, "fuck:19980504", new BotConfiguration() {
            {
                setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File(ReturnPath() + "/data/deviceInfo.json"), context));
            }
        });

        //初始化所有信息
        Initization.Initization();

        //        机器人登录
        bot.login();

        Events.subscribeAlways(FriendMessage.class,
                (FriendMessage event)
                -> {

        });

        //私聊触发
        Events.subscribeAlways(FriendMessage.class,
                (FriendMessage event) -> {

                    if (cando) {
                        if (gameevents.containsKey(event.getMessage().contentToString().split(" ")[0])) {
                            pool.submit(new Thread() {
                                @Override
                                public void run() {
                                    if (!PlayerManager.ExistThisPlayer(event.getSender())) {
                                        try {
                                            PlayerCreater.CreatePlayer(event.getSender().getId());
                                        } catch (IOException ex) {
                                            Logger.getLogger(GameRobot.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    GameEvent groupevent = gameevents.get(event.getMessage().contentToString().split(" ")[0]);
                                    Player player = players.get(String.valueOf(event.getSender().getId()));

                                    if (groupevent.isIsopen()) {
                                        groupevent.Do(player);
                                        groupevent.Do(player, event.getMessage().contentToString());
                                    }
                                }
                            });

                        }

                    }
                });

        //群组消息触发
        Events.subscribeAlways(GroupMessage.class, (GroupMessage event) -> {
            if (event.getMessage().contentToString().equals("Test")) {
                Image image;
                try {
                    System.out.println("Test");
                    image = event.getGroup().uploadImage(CreateLine());
                    event.getGroup().sendMessage(image);
                } catch (FileNotFoundException ex) {

                }

            }
            if (gameevents.containsKey(event.getMessage().contentToString().split(" ")[0])) {

                pool.submit(new Thread() {
                    @Override
                    public void run() {

                        if (!PlayerManager.ExistThisPlayer(event.getSender())) {
                            try {
                                PlayerCreater.CreatePlayer(event.getSender().getId());
                            } catch (IOException ex) {
                                Logger.getLogger(GameRobot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        Player player = players.get(String.valueOf(event.getSender().getId()));
                        GameEvent groupevent = gameevents.get(event.getMessage().contentToString().split(" ")[0]);
                        //是否开启

                        if (groupevent.isIsopen()) {
                            //是否所有人都可以用

                            if (groupevent.isOpen() || groups.get("所有者").getMembers().containsValue(player.getName())) {
                                groupevent.Do(event.getGroup());
                                groupevent.Do(player);
                                groupevent.Do(event.getGroup(), event.getMessage().contentToString());
                                groupevent.Do(event.getGroup(), player);
                            } else {
                                //是否拥有权限

                                if (groupevent.getPermissions().containsKey(event.getSender().getId())) {
                                    groupevent.Do(event.getGroup());
                                    groupevent.Do(player);
                                    groupevent.Do(event.getGroup(), event.getMessage().contentToString());
                                    groupevent.Do(event.getGroup(), player);
                                } else {
                                    event.getGroup().sendMessage("你没有权限");
                                }
                            }

                        } else {
                            event.getGroup().sendMessage("管理员未开放此功能");
                        }

                    }
                });

            }

        }
        );

        bot.join(); // 阻塞当前线程直到 bot 离线
    }

}
