package com.ckss.project.dada.domain.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ckss.project.dada.utils.JSONUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: DataTransporterResult
 * @date: 2020/6/13
 **/
@Data
public class DataTransporterResult {

    private Integer id;
    private String name;
    private String phone;
    private Integer cityId;

    public static List<DataTransporterResult> fromJson(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        List<DataTransporterResult> dadaCities = new ArrayList<DataTransporterResult>();
        for (Object object : jsonArray) {
            DataTransporterResult data = new DataTransporterResult();
            data.setId(((JSONObject)object).getInteger("id"));
            data.setName(((JSONObject)object).getString("name"));
            data.setPhone(((JSONObject)object).getString("phone"));
            data.setCityId(((JSONObject)object).getInteger("city_id"));
            dadaCities.add(data);
        }
        return dadaCities;
    }
}
