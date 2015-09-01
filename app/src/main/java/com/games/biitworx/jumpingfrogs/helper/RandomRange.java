package com.games.biitworx.jumpingfrogs.helper;

import java.util.Random;

/**
 * Created by WEIS on 01.09.2015.
 */
public final class RandomRange {
    public static int getRandom(int aStart, int aEnd){
        if (aStart > aEnd) {
            int aSave=aStart;
            aStart = aEnd;
            aEnd=aSave;
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * new Random().nextDouble());
        return  (int)(fraction + aStart);

    }


    public static float getFloat(float min,float max)
    {
        float result = getRandom((int)min,(int)max)+ new Random().nextFloat();
        while(result<min || result > max)
            result= getRandom((int)min,(int)max)+new Random().nextFloat();
        return result;
    }
}