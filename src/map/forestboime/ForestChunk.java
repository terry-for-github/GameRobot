package map.forestboime;

import map.Chunk;
import map.Location;

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
        
    }
}
