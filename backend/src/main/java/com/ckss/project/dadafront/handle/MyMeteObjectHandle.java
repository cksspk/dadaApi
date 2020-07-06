package com.ckss.project.dadafront.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author cks
 */
@Component
@Slf4j
public class MyMeteObjectHandle implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);

        //设置逻辑删除默认字段为0
        this.setFieldValByName("deleted", 0, metaObject);
        log.info("mp自动添加时间 insertFill");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",LocalDateTime.now(),metaObject);
        log.info("mp自动更新时间 updateFill");

    }
}
