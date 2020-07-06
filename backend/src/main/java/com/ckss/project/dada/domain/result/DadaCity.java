package com.ckss.project.dada.domain.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cks
 */
@Data
public class DadaCity {

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市编码
     */
    private String cityCode;

    public static List<DadaCity> fromJson(String json) {
        JSONArray jsonArray = JSON.parseArray(json);
//        JSONObject jsonObject = JSON.parseObject(json);
//        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<DadaCity> dadaCities = new ArrayList<DadaCity>();
        for (Object object : jsonArray) {
            DadaCity dadaCity = new DadaCity();
            dadaCity.setCityName(((JSONObject)object).getString("cityName"));
            dadaCity.setCityCode(((JSONObject)object).getString("cityCode"));
            dadaCities.add(dadaCity);
        }
        return dadaCities;
    }

}
