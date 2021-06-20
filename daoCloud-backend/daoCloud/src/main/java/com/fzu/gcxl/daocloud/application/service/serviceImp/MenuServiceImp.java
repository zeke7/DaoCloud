package com.fzu.gcxl.daocloud.application.service.serviceImp;

import com.alibaba.fastjson.JSONObject;
import com.fzu.gcxl.daocloud.application.service.MenuService;
import com.fzu.gcxl.daocloud.domain.entity.Menu;
import com.fzu.gcxl.daocloud.domain.entity.Submenu;
import com.fzu.gcxl.daocloud.domain.entity.response.BaseResponse;
import com.fzu.gcxl.daocloud.domain.exception.UserFriendException;
import com.fzu.gcxl.daocloud.domain.repository.MenuRepository;
import com.fzu.gcxl.daocloud.domain.repository.SubmenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImp implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    SubmenuRepository submenuRepository;

    @Override
    public BaseResponse createNewMenu(JSONObject menu) {
        // 创建菜单
        String menuname_en = menu.getString("menuname_en");
        String menuname_zh = menu.getString("menuname_zh");
        Integer ordernum = menu.getInteger("order_num");

        try{
            Menu newMenu = new Menu();
            newMenu.setMenunameEn(menuname_en);
            newMenu.setMenunameZh(menuname_zh);
            newMenu.setOrderNum(ordernum);

            if (menuRepository.selectByEnName(menuname_en) != null || menuRepository.selectByZhName(menuname_zh) != null) {
                return new BaseResponse(500, "添加菜单失败，已经存在","");
            }
            int res = menuRepository.insert(newMenu);
            if(res==-1)
                return new BaseResponse(500, "添加菜单失败","");
            return new BaseResponse(200, "添加菜单成功","");
        }catch (Exception e){
            throw new UserFriendException("添加菜单失败");
        }
    }

    @Override
    public BaseResponse deleteMenuById(Integer mid) {
        try{
            if (submenuRepository.selectByParentId(mid).size() != 0){
                return new BaseResponse(500, "删除菜单失败，存在子菜单","");
            }else {
                if (menuRepository.selectByPrimaryKey(mid) != null){
                    int res = menuRepository.deleteByPrimaryKey(mid);
                    if (res == -1)
                        return new BaseResponse(500, "删除菜单失败", "");
                    return new BaseResponse(200, "删除菜单成功", "");
                }else{
                    return new BaseResponse(500, "删除菜单失败，菜单不存在。", "");
                }
            }
        }catch (Exception e){
            throw new UserFriendException("删除菜单失败");
        }
    }

    @Override
    public BaseResponse updateMenu(JSONObject menu) {

        Integer mid = menu.getInteger("menuId");
        String menuname_en = menu.getString("menuname_en");
        String menuname_zh = menu.getString("menuname_zh");
        Integer ordernum = menu.getInteger("order_num");
        try{
            Menu newMenu = new Menu();
            newMenu.setMenuId(mid);
            newMenu.setMenunameEn(menuname_en);
            newMenu.setMenunameZh(menuname_zh);
            newMenu.setOrderNum(ordernum);

            if (menuRepository.selectByPrimaryKey(mid) == null) {
                return new BaseResponse(500, "修改菜单失败，不存在","");
            }else {
                int res = menuRepository.updateByPrimaryKeySelective(newMenu);
                if(res==-1)
                    return new BaseResponse(500, "修改菜单失败","");
                return new BaseResponse(200, "修改菜单成功","");
            }

        }catch (Exception e){
            throw new UserFriendException("修改菜单失败");
        }
    }

    @Override
    public BaseResponse createNewSubMenu(JSONObject menu) {
        // 创建菜单
        String subname_zh = menu.getString("subname_zh");
        Integer parent_id = menu.getInteger("parent_id");
        Integer ordernum = menu.getInteger("order_num");

        System.out.println(menu);

        try{
            Submenu newSubMenu = new Submenu();
            newSubMenu.setParentId(parent_id);
            newSubMenu.setNameZh(subname_zh);
            newSubMenu.setOrderNum(ordernum);

            List<Submenu> submenus = submenuRepository.selectByParentId(parent_id);

            if (submenus != null) {
                for (Submenu sub: submenus) {
                    if (sub.getNameZh().equals(subname_zh))
                        return new BaseResponse(500, "添加子菜单失败，已经存在","");
                }
            }
            System.out.println(newSubMenu);
            int res = submenuRepository.insert(newSubMenu);
            if(res==-1)
                return new BaseResponse(500, "添加子菜单失败","");
            return new BaseResponse(200, "添加子菜单成功","");
        }catch (Exception e){
            System.out.println(e);
            throw new UserFriendException("添加子菜单失败");
        }
    }

    @Override
    public BaseResponse deleteSubMenuById(Integer mid) {
        try{
            if (submenuRepository.selectByPrimaryKey(mid) != null){
                int res = submenuRepository.deleteByPrimaryKey(mid);
                if (res == -1)
                    return new BaseResponse(500, "删除子菜单失败", "");
                return new BaseResponse(200, "删除子菜单成功", "");
            }else{
                return new BaseResponse(500, "删除子菜单失败，菜单不存在。", "");
            }
        }catch (Exception e){
            throw new UserFriendException("删除子菜单失败");
        }
    }

    @Override
    public BaseResponse updateSubMenu(JSONObject menu) {
        String subname_zh = menu.getString("subname_zh");
        Integer parent_id = menu.getInteger("parent_id");
        Integer ordernum = menu.getInteger("order_num");

        try{
            Submenu newSubMenu = new Submenu();
            newSubMenu.setParentId(parent_id);
            newSubMenu.setNameZh(subname_zh);
            newSubMenu.setOrderNum(ordernum);

            if (submenuRepository.selectByPrimaryKey(parent_id) != null) {
                return new BaseResponse(500, "修改菜单失败，不存在","");
            }else {
                int res = submenuRepository.updateByPrimaryKeySelective(newSubMenu);
                if(res==-1)
                    return new BaseResponse(500, "修改子菜单失败","");
                return new BaseResponse(200, "修改子菜单成功","");
            }

        }catch (Exception e){
            throw new UserFriendException("修改子菜单失败");
        }
    }


}
