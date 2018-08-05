package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;

public class PointsTests {
    @Test
    public void testArea(){
        Point point = new Point();
        Assert.assertEquals(point.distance(1, 2, 4, 9), 7.615773105863909);
        Assert.assertEquals(point.distance(12, 25, 43, 75), 58.83026432033091);
        Assert.assertEquals(point.distance(5, 20, 36, 89), 75.64390259630977);
        Assert.assertEquals(point.distance(1.5, 2.7, 4.8, 9.1), 7.200694410957876);

    }

}
