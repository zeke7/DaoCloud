import http from '@/http/httpRequest.js'

export function getToken(data) {
    return http({
        url: '/login',
        // url: '/auth/token',
        method: 'post',
        data
    })
}

export function register(data) {
    return http({
        url: '/signup',
        method: 'post',
        data
    })
}

export function modifyPassword(data) {
    return http({
        url: 'modifyPassword',
        method: 'post',
        data
    })
}

export function forgetPassword(data) {
    return http({
        url: 'forgetPassword',
        method: 'post',
        data
    })
}
