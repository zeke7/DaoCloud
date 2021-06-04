import http from "../httpRequest";

export function getMenus1() {
    return http({
        url: '/menu/menus1',
        method: 'get'
    })
}