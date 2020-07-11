/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Map.ForestBoime;

import Map.Chunk;
import Map.Location;

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
