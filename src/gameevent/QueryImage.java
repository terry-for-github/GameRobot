package gameevent;

import entity.player.Player;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;


/**
 *
 * @author Administrator
 */
public class QueryImage extends GameEvent {

    public QueryImage() {
        super("动漫");
        

    }

    @Override
    public void Do(Group group, Player player) {
        try {
           
            Image image = group.uploadImage(new URL("https://api.dongmanxingkong.com/suijitupian/acg/1080p/index.php"));
            group.sendMessage(image);
        } catch (MalformedURLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

}
