package com.ckss.project.dada.mp;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author cks
 */
public class MyTest {

    @Test
    public void timeConvert(){
        Integer updateTime = 1593576173;
        Instant instant = Instant.ofEpochMilli(updateTime);
        LocalDateTime time = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//        LocalDateTime time = LocalDateTime.parse(updateTime);

        Long updateTime2 = 1593576173000l;
        Instant instant2 = Instant.ofEpochMilli(updateTime2);
        LocalDateTime time2 = LocalDateTime.ofInstant(instant2, ZoneId.systemDefault());
        System.out.println("time1:"+time);
        System.out.println("time2:"+time2);
    }

    @Test
    public void splitTest(){

        String str = "a";
        String[] split = StringUtils.split(str, ",");
        List list = Arrays.asList(split);
        System.out.println(Arrays.toString(split));

        list.forEach(s-> System.out.println(s));
    }
}
