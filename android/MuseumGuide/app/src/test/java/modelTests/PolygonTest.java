package modelTests;
import org.junit.Test;

import database.models.Polygon;
import exceptions.InvalidPolygonException;
import utilities.Point;

import static org.junit.Assert.*;

public class PolygonTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testPolygonCreationValid() throws InvalidPolygonException {
        Polygon p = new Polygon(new Point[]{new Point(3.56,2.43),
                new Point(2.34,8.9432),new Point(2.34,8.942),new Point(3.56,2.43)});
    }

    @Test(expected = InvalidPolygonException.class)
    public void testPolygonCreationInvalid() throws InvalidPolygonException {
        Polygon p = new Polygon(new Point[]{new Point(3.56, 2.43),
                new Point(2.34, 8.9432), new Point(3.56, 2.432)});
        p = new Polygon(new Point[]{new Point(3.56, 2.43),
                new Point(2.34, 8.9432), new Point(2.34, 8.9432), new Point(3.56, 2.432)});
    }

    @Test
    public void testVerifyPolygonValid(){
        assertTrue(Polygon.verifyPolygon(new Point[]{new Point(3.56,2.43),
                new Point(2.34,8.9432),new Point(2.34,8.943),new Point(3.56,2.43)}));
    }

    @Test
    public void testVerifyPolygonInvalid(){
        assertFalse(Polygon.verifyPolygon(new Point[]{new Point(3.56,2.43),
                new Point(2.34,8.9432),new Point(3.56,2.432)}));
    }
}