package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundingBoxTest {

    @DisplayName("WHEN we construct an empty bounding box, THEN it has a `null` upper left corner "
            + "and width, height, and area all 0.")
    @Test
    void testEmptyConstruction() {
        BoundingBox emptyBox = new BoundingBox();
        assertNull(emptyBox.upperLeftCorner());
        assertEquals(0, emptyBox.width(), "incorrect box width");
        assertEquals(0, emptyBox.height(), "incorrect box height");
        assertEquals(0, emptyBox.area(), "incorrect box area");
    }

    @DisplayName("WHEN we construct a non-empty bounding box AND a point in its interior, THEN the "
            + "box should contain that point.")
    @Test
    void testContainsInteriorPoint() {
        BoundingBox box = new BoundingBox(new Point(0, 2), 2, 2);
        Point interiorPoint = new Point(1, 1);
        assertTrue(box.containsPoint(interiorPoint));
    }

    @DisplayName("WHEN the intersection of two bounding box is another 2D bounding box, THEN "
            + "this intersection should have the correct location and dimensions.")
    @Test
    void test2DIntersection() {
        // this is the test case from the handout figure
        BoundingBox redBox = new BoundingBox(new Point(2, 5), 3, 3);
        BoundingBox blueBox = new BoundingBox(new Point(4, 4), 3, 3);

        BoundingBox purpleBox = redBox.intersectWith(blueBox);
        assertEquals(new Point(4, 4), purpleBox.upperLeftCorner(),
                "incorrect upper left corner of intersection");
        assertEquals(1, purpleBox.width(), "incorrect intersection width");
        assertEquals(2, purpleBox.height(), "incorrect intersection height");

        // check that the other intersection order also works
        purpleBox = blueBox.intersectWith(redBox);
        assertEquals(new Point(4, 4), purpleBox.upperLeftCorner(),
                "incorrect upper left corner of intersection");
        assertEquals(1, purpleBox.width(), "incorrect intersection width");
        assertEquals(2, purpleBox.height(), "incorrect intersection height");
    }




    // TODO: Add more tests to fully cover the `BoundingBox` specifications.
    @DisplayName("WHEN a width and height are given, THEN " +
            "the area should be the product of the width and height")
    @Test
    void testArea(){
        BoundingBox box1 = new BoundingBox(new Point(2,5),3,4);
        assertEquals(12, box1.area());
    }




    @DisplayName("When another bounding box is given and the coordinates of the other bounding box fits inside this box, THEN " +
    "the box should contain that other boudning box.")
    @Test
    void testContainsBox(){
        BoundingBox box1 = new BoundingBox(new Point(2,5),1,1);
        BoundingBox box2 = new BoundingBox(new Point(1,6),6,7);
        assertTrue(box2.containsBox(box1));
    }



    @DisplayName("WHEN two boxes have the same dimensions, THEN "+
    "they should be equal")
    @Test
    void testEquals(){
        BoundingBox box1 = new BoundingBox(new Point(2,5),1,1);
        BoundingBox box2 = new BoundingBox(new Point(2,5),1,1);
        assertTrue(box2.equals(box1));
    }
    @DisplayName("WHEN a box has no points inside it, THEN "+
    "the box is empty")
    @Test
    void testIsEmpty(){
        BoundingBox box1 = new BoundingBox(new Point(2,5),1,1);
        assertFalse(box1.isEmpty());
        BoundingBox box2 = new BoundingBox(new Point(2, 5),0,0);
        assertFalse(box2.isEmpty());

        BoundingBox box3 = new BoundingBox();
        assertTrue(box3.isEmpty());

    }

    @DisplayName("WHEN a box is created, THEN "+
    "it should have an upper left corner.")
    @Test
    void testULCorner(){
        BoundingBox box1 = new BoundingBox(new Point(2,5),1,1);
        assertEquals(new Point(2,5), box1.upperLeftCorner());
    }

    @DisplayName("WHEN a box is created, THEN "+
            "it should have a width.")
    @Test
    void testWidth(){
        BoundingBox box1 = new BoundingBox(new Point(2,5),1,1);
        assertEquals(1, box1.width());
    }

    @DisplayName("WHEN a box is created, THEN "+
            "it should have a height.")
    @Test
    void testHeight(){
        BoundingBox box1 = new BoundingBox(new Point(2,5),1,3);
        assertEquals(3, box1.height());
    }



}
