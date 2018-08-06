package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;

public class PointsTests {
    @Test
    public void testArea(){
        Point p1 = new Point();
        Point p2 = new Point(20, 40);


        Assert.assertEquals(p1.distance(p2), 44.721359549995796);
        Assert.assertEquals(p1.distance(60,100), 116.61903789690601);
        Assert.assertEquals(p1.distance(52, 110), 121.67168939404104);
        Assert.assertEquals(p1.distance(5.4, 8.9), 10.410091258005378);

    }

}
