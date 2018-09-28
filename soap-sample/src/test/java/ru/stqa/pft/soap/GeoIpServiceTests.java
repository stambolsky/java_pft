package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GeoIpServiceTests {

    @Test
    public void testMyIp() {
        String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("37.215.178.146");
        String geo = geoIP;
        System.out.println(geoIP);
        //Assert.assertEquals(geoIP.getCountryCode(), "BY");
    }

    @Test
    public void testInvalidIp() {
        String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("37.215.178.xxx");
        System.out.println(geoIP);
        //Assert.assertEquals(geoIP.getCountryCode(), "BY");
    }
}
