package com.ckss.project.dada.domain.req;


import com.ckss.project.dada.utils.JSONUtil;

import java.io.Serializable;

/**
 * DATE: 18/9/3
 *
 * @author: wan
 */
public class BaseModel {

    public String toJson() {
        return JSONUtil.toJson(this);
    }
}
