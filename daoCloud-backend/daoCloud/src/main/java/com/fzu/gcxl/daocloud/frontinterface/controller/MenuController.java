package com.fzu.gcxl.daocloud.frontinterface.controller;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.MenuService;
import com.fzu.gcxl.daocloud.application.service.assembler.MenuDtoAssembler;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    MenuDtoAssembler menuDtoAssembler;

    @CrossOrigin
    @GetMapping("/tmenus")
    public BaseResponse getMenuteahcer(){
        return menuDtoAssembler.teacherMenuAssembler();
    }

    @CrossOrigin
    @GetMapping("/adminmenus")
    public BaseResponse getMenuadmin(){
        return menuDtoAssembler.adminMenuAssembler();
    }

    @CrossOrigin
    @PostMapping("/menus")
    public BaseResponse createMenu(@RequestBody JSONObject menu){
        return menuService.createNewMenu(menu);
    }

    @CrossOrigin
    @PutMapping("/menus")
    public BaseResponse modifyMenu(@RequestBody JSONObject menu){
        return menuService.updateMenu(menu);
    }

    @CrossOrigin
    @DeleteMapping("/menus")
    public BaseResponse deleteMenu(Integer mid){
        return menuService.deleteMenuById(mid);
    }

    @CrossOrigin
    @PostMapping("/submenus")
    public BaseResponse createSubMenu(@RequestBody JSONObject menu){
        return menuService.createNewSubMenu(menu);
    }

    @CrossOrigin
    @PutMapping("/submenus")
    public BaseResponse modifySubMenu(@RequestBody JSONObject menu){
        return menuService.updateSubMenu(menu);
    }

    @CrossOrigin
    @DeleteMapping("/submenus")
    public BaseResponse deleteSubMenu(Integer subid){
        return menuService.deleteSubMenuById(subid);
    }


}
