package com.example.sari.museumguide.positioning;

import com.example.sari.museumguide.models.positioning.Position;
import com.example.sari.museumguide.tools.Point;

/**
 * Created by sari on 18/02/16.
 */
public class PositionEngine {

    /**
     * This method will return the coordinates of a specific reading (position). The returned value
     * might changed based on the algorithm used by this method.
     * @param p
     * @return
     */
    public static Point getRoom(Position p){
        return new Point(0,0);
    }
}
