package gamerobot;

import entity.animal.Animal;
import entity.mobs.Mob;
import entity.plant.Plant;
import entity.player.Player;
import entity.player.PlayerCreater;
import entity.player.PlayerManager;
import gameevent.GameEvent;
import goods.Good;
import java.awt.Color;
import map.Chunk;
import map.Location;
import utils.Initialization;
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
import java.io.FileNotFoundException;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.japt.Events;
import net.mamoe.mirai.message.FriendMessage;
import net.mamoe.mirai.message.GroupMessage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import static utils.HttpUtil.saveImage;
import static utils.ImageUtils.createLine;
import static utils.Initialization.returnPath;

/**
 *
 * @author Administrator
 */
public class GameRobot {

    /**
     * 主类
     */
    public static boolean canRecieveFriendMessage = true;
    public static long time = 0;

    //高取不存（需要被复制）
    public static Map<String, Mob> mobs = new ConcurrentHashMap<>();//所有的怪物

    public static Map<String, Plant> plants = new ConcurrentHashMap<>();//所有的植物

    public static Map<String, Good> goods = new ConcurrentHashMap<>();//所有的物品

    public static Map<String, Animal> animals = new ConcurrentHashMap<>();//所有的动物

    public static Map<String, GameEvent> gameEvents = new ConcurrentHashMap<>();//所有的触发

    public static Map<String, PermissionGroup> groups = new ConcurrentHashMap<>();//所有的组

    //需要高并发的存取
    public static Map<String, Player> players = new ConcurrentHashMap<>();//所有玩家

    public static Map<Location, Chunk> chuns = new ConcurrentHashMap<>();

    //线程池
    public static ExecutorService pool = Executors.newFixedThreadPool(200);
    public static ExecutorService executorService = Executors.newCachedThreadPool();
    public static Bot bot;

    /**
     *
     * @param args
     * @throws InterruptedException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws MalformedURLException
     * @throws ClassNotFoundException
     * @throws Exception
     */
    public static void main(String[] args) throws InterruptedException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, InstantiationException, MalformedURLException, ClassNotFoundException, Exception {
       
//        // 测试图片的叠加
//        overlyingImageTest();
//        // 测试图片的垂直合并
//        imageMargeTest();
//        // 用于作机器人的qq账户
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入账户");
//        long account = scanner.nextLong();
//        System.out.println("请输入密码");
//        String password = scanner.next();

        // 机器人初始化
        bot = BotFactoryJvm.newBot(501864196, "fuck:19980504", new BotConfiguration() {
            {
                setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File(returnPath() + "/deviceInfo.json"), context));
            }
        });

        // 初始化所有信息
        Initialization.Initialization();

        // 机器人登录
        bot.login();

        // 私聊触发
        Events.subscribeAlways(FriendMessage.class, (FriendMessage event) -> {
            String eventContent = event.getMessage().contentToString();
            String eventKey = eventContent.split(" ")[0];
            long playerId = event.getSender().getId();
            String playerKey = String.valueOf(playerId);

            if (canRecieveFriendMessage && gameEvents.containsKey(eventKey)) {
                pool.submit(new Thread() {
                    @Override
                    public void run() {
                        if (!PlayerManager.existThisPlayer(event.getSender())) {
                            try {
                                PlayerCreater.createPlayer(playerId);
                            } catch (IOException ex) {
                                Logger.getLogger(GameRobot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        GameEvent groupEvent = gameEvents.get(eventKey);
                        Player player = players.get(String.valueOf(playerId));

                        if (groupEvent.isIsopen()) {
                            groupEvent.Do(player);
                            groupEvent.Do(player, eventContent);
                        }
                    }
                });
            }
        });

        // 群组消息触发
        Events.subscribeAlways(GroupMessage.class, (GroupMessage event) -> {
            String eventContent = event.getMessage().contentToString();
            String eventKey = eventContent.split(" ")[0];
            long playerId = event.getSender().getId();
            String playerKey = String.valueOf(playerId);

            if (eventContent.equals("Test")) {
                Image image;
                try {
                    System.out.println("Test");
                    image = event.getGroup().uploadImage(createLine(100, 100, Color.WHITE,Color.BLACK, 2000, 2000));
                    event.getGroup().sendMessage(image);
                } catch (FileNotFoundException ex) {

                }
            }

            if (gameEvents.containsKey(eventKey)) {
                pool.submit(new Thread() {
                    @Override
                    public void run() {
                        if (!PlayerManager.existThisPlayer(event.getSender())) {
                            try {
                                PlayerCreater.createPlayer(playerId);
                            } catch (IOException ex) {
                                Logger.getLogger(GameRobot.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        Player player = players.get(playerKey);
                        GameEvent groupEvent = gameEvents.get(eventKey);
                        // 是否开启
                        if (groupEvent.isIsopen()) {
                            // 是否所有人都可以用
                            if (groupEvent.isOpen() || groups.get("所有者").getMembers().containsValue(player.getName())) {
                                groupEvent.Do(event.getGroup());
                                groupEvent.Do(player);
                                groupEvent.Do(event.getGroup(), eventContent);
                                groupEvent.Do(event.getGroup(), player);
                            } else {
                                // 是否拥有权限
                                if (groupEvent.getPermissions().containsKey(event.getSender().getId())) {
                                    groupEvent.Do(event.getGroup());
                                    groupEvent.Do(player);
                                    groupEvent.Do(event.getGroup(), eventContent);
                                    groupEvent.Do(event.getGroup(), player);
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
        });

        // 阻塞当前线程直到 bot 离线
        bot.join();
    }
}
