package com.ckss.project.system.controller;

import com.ckss.common.enums.ResultCodeEnum;
import com.ckss.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @className: LogonController
 * @description: 登陆验证
 * @author: cksspk
 * @date: 2020/3/23
 **/
@RestController
@RequestMapping("sys")
public class LoginController {


//    @Autowired
//    private LoginService loginService;


    /**
     * ********** 模拟登录
     */
    @GetMapping("info")
    public AjaxResult info(){
            System.out.println("模拟登录");

            AjaxResult ajax = AjaxResult.success();
            ajax.put("roles","[admin]");
            ajax.put("name","admin");
            ajax.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return ajax;
    }

    @PostMapping("login")
    public AjaxResult login(String username, String password, String verifyCode){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("token","admin");
        return ajax;
    }





    /**
     * 登出接口
     * 使用springSecurity则不使用此接口
     * @return
     */
    @PostMapping("logout")
    public AjaxResult logout(){
        System.out.println("登出操作");
//        Result ajax = Result.ok();

        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }



}
