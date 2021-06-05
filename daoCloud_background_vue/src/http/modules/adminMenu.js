import http from '../httpRequest'

export function getMenus() {
    return http({
        url: '/menu/getMenus',
        method: 'get'
    })
}
