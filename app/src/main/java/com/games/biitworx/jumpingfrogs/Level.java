package com.games.biitworx.jumpingfrogs;

import java.util.ArrayList;

/**
 * Created by WEIS on 31.08.2015.
 */
public abstract class Level {

   public ArrayList<Integer> Roses;

    public Level()
    {
        Roses=new ArrayList<>();
        initRoses();
    }
    public abstract void initRoses();
}
