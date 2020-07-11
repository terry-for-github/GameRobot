/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Goods.Consumables;

import Goods.Good;

/**
 *
 * @author Administrator
 */
//消耗品
public class Consumables extends Good implements Cloneable {

    public Consumables(String name, String use) {
        super(name, "消耗品", use);
    }

    @Override
    public Good clone() {
        Consumables consumables = null;
        consumables = (Consumables) super.clone();
        return consumables;
    }

    public void Use() {

    }
}
