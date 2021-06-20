package com.fzu.gcxl.daocloud.application.service.assembler;

import com.fzu.gcxl.daocloud.domain.entity.Menu;
import com.fzu.gcxl.daocloud.domain.entity.Submenu;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.repository.MenuRepository;
import com.fzu.gcxl.daocloud.domain.repository.SubmenuRepository;
import com.fzu.gcxl.daocloud.frontinterface.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuDtoAssembler {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    SubmenuRepository submenuRepository;

    public BaseResponse adminMenuAssembler(){
        List<MenuDto> resultList = new ArrayList<>();
        List<Menu> topMenus = menuRepository.selectAllMenu();

        for (Menu tmenu: topMenus) {
            MenuDto mdto = new MenuDto();
            if (tmenu.getMenuId() != null){
                List<Submenu> submenus = submenuRepository.selectByParentId(tmenu.getMenuId());
                mdto.setSubMenusList(submenus);
            }
            String nameEn = tmenu.getMenunameEn() != null? tmenu.getMenunameEn() : "暂未设置";
            String nameZh = tmenu.getMenunameZh() != null? tmenu.getMenunameZh() : "暂未设置";
            String murl = tmenu.getUrl() != null? tmenu.getUrl() : "暂未设置";
            String micon = tmenu.getIcon() != null? tmenu.getIcon() : "暂未设置";
            mdto.setMenuId(tmenu.getMenuId());
            mdto.setNameZh(nameZh);
            mdto.setName_en(nameEn);
            mdto.setUrl(murl);
            mdto.setIcon(micon);

            resultList.add(mdto);
        }

        return new BaseResponse(200, "获取全部列表", resultList);
    }

    public BaseResponse teacherMenuAssembler(){
        List<MenuDto> resultList = new ArrayList<>();
        List<Submenu> subList = new ArrayList<>();

        MenuDto resultdto = new MenuDto();

        Menu topMenus = menuRepository.selectByPrimaryKey(1);
        List<Submenu> submenus = submenuRepository.selectByParentId(1);

        String nameEn = topMenus.getMenunameEn() != null? topMenus.getMenunameEn() : "暂未设置";
        String nameZh = topMenus.getMenunameZh() != null? topMenus.getMenunameZh() : "暂未设置";
        String murl = topMenus.getUrl() != null? topMenus.getUrl() : "暂未设置";
        String micon = topMenus.getIcon() != null? topMenus.getIcon() : "暂未设置";
        resultdto.setMenuId(1);
        resultdto.setUrl(murl);
        resultdto.setIcon(micon);
        resultdto.setNameZh(nameZh);
        resultdto.setName_en(nameEn);
        for (Submenu sub: submenus) {
//            if(sub.getNameZh().equals("用户管理")){
//                sub.setNameZh("学生管理");
//                subList.add(sub);
//            }
            if(sub.getNameZh().equals("学生班课管理"))
                subList.add(sub);
        }
        resultdto.setSubMenusList(subList);

        resultList.add(resultdto);

        return new BaseResponse(200, "获取部分列表", resultList);
    }
}
