package com.example.sari.museumguide;

import com.example.sari.museumguide.models.indoormapping.Building;
import com.example.sari.museumguide.models.indoormapping.Floor;
import com.example.sari.museumguide.models.indoormapping.Room;
import com.example.sari.museumguide.tools.Point;
import com.example.sari.museumguide.tools.Rectangle;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

/* Test for basic building

   0___________10
   |     |     |
   |  1  |  2  |
   |     |     |
   |_____|_____|Floor 0
   10

   0___________10
   |  3  |     |
   |_____|  4  |
   |  5  |     |
   |_____|_____|Floor 1
   10

   0___________10
   |  6  |  7  |
   |_____|_____|
   |  8  |  9  |
   |_____|_____|Floor 2
   10

 */
public class ModelTest {
   static Room r1, r2, r3, r4, r5, r6, r7, r8, r9;
   static Floor f0, f1, f2;
   static Building testBuilding;

    @BeforeClass
    public static void setUp() {
        r1 = new Room(new Rectangle(new Point(0,0),5,10));
        r2 = new Room(new Rectangle(new Point(5,0),5,10));

        r3 = new Room(new Rectangle(new Point(0,0),5,5));
        r4 = new Room(new Rectangle(new Point(5,0),5,10));
        r5 = new Room(new Rectangle(new Point(0,5),5,5));

        r6 = new Room(new Rectangle(new Point(0,0),5,5));
        r7 = new Room(new Rectangle(new Point(5,0),5,5));
        r8= new Room(new Rectangle(new Point(0,5),5,5));
        r9 = new Room(new Rectangle(new Point(5,5),5,5));
        Room[] f0Array = {r1,r2};
        Room[] f1Array = {r3,r4,r5};
        Room[] f2Array = {r6,r7,r8,r9};

        f0 = new Floor(f0Array);
        f1 = new Floor(f1Array);
        f2 = new Floor(f2Array);

        Floor[] floors = {f0,f1,f2};

        testBuilding = new Building(floors);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void buildingTest(){
        assertEquals(testBuilding.getFloors().length, 3);
        System.out.print(testBuilding.getFloors()[1].getNeighbours(r8).length);

    }
}