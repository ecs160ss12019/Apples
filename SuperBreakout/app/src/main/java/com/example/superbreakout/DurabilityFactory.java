package com.example.superbreakout;


/**
 * This class acts a base class and engine to determine which durability for each level.
 * Uses state-based refactoring
 */

import android.content.Context;

public class DurabilityFactory {

    public Obstacle getDurabilityObject(Context context, int row, int column, int widthObstacle,
                                      int heightObstacle, int horzPadding, int vertPadding,
                                      int durability){

        if(durability == DurabilityOne.DURABILITY_ONE){

            return new DurabilityOne(context, row, column,
                    widthObstacle, heightObstacle, horzPadding, vertPadding);

        }else if(durability == DurabilityTwo.DURABILITY_TWO){

            return new DurabilityTwo(context, row, column,
                    widthObstacle, heightObstacle, horzPadding, vertPadding);

        }else if(durability == DurabilityThree.DURABILITY_THREE){

            return new DurabilityThree(context, row, column,
                    widthObstacle, heightObstacle, horzPadding, vertPadding);

        }else if(durability == DurabilityZero.DURABILITY_ZERO){

            return new DurabilityZero(context, row, column,
                    widthObstacle, heightObstacle, horzPadding, vertPadding);

        }else if(durability == Explosive.DURABILITY_EXPLOSIVE){

            return new Explosive(context, row, column,
                    widthObstacle, heightObstacle, horzPadding, vertPadding);
        }
                else{
            return null;
        }
    }
}
