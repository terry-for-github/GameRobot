package map.forestboime;

import map.Chunk;
import map.Location;
import utils.Initization;

/**
 *
 * @author Administrator
 */
//森林区块
public class ForestChunk extends Chunk {
    
    public ForestChunk(Location location) {
        super(location);
        this.setBoime("forest");
    }
    
    @Override
    public void setEntitys()
    {
        Initization.random.nextInt(12);
    }
}
