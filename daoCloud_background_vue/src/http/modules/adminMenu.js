import http from '../httpRequest'

export function getMenus() {
    return http({
        url: '/adminmenus',
        // url: '/menu/getMenus',
        method: 'get'
    })
}
