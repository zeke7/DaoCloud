import http from "../httpRequest";

export function getMenus1() {
    return http({
        'url' :'/tmenus',
        // url: '/menu/menus1',
        method: 'get'
    })
}