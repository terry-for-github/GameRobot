/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import Entities.Entity;

/**
 *
 * @author Administrator
 */
public abstract class Chunk {

    private Location location;
    private String boime;//群系
    private Space[][] spaces;

    public Chunk(Location location) {
        //区块位置
        this.location = location;
        //区块的每个空间
        this.spaces = new Space[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                spaces[i][j] = new Space(new Location(i, j));
            }
        }
    }

    public Space getSpace(int x, int y) {
        return spaces[x][y];
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Space[][] getSpaces() {
        return spaces;
    }

    public void setSpaces(Space[][] spaces) {
        this.spaces = spaces;
    }

    public abstract void setEntitys();

    public String getBoime() {
        return boime;
    }

    public void setBoime(String boime) {
        this.boime = boime;
    }

}
