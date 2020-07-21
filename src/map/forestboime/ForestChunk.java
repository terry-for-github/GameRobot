package map.forestboime;

import map.Chunk;
import map.Location;
import utils.Initialization;

/**
 * 森林区块
 * @author Administrator
 */
public class ForestChunk extends Chunk {
    /**
     * 
     * @param location 位置
     */
    public ForestChunk(Location location) {
        super(location);
        this.setBoime("forest");
    }
    
    /**
     * 设置实体
     */
    @Override
    public void setEntitys(){
        Initialization.random.nextInt(12);
    }
}
