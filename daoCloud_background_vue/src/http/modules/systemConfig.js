// import http from '@/http/httpRequest.js'
import http from '../httpRequest'

export function getParamters(token) {
    return http({
        url: '/allsystemparamters',
        method: 'get',
        headers: {
            Authorization: token
        }
    })
}

export function systemCurd(method, data, token) {
    return http({
        url: '/systemparamters',
        method: method,
        headers: {
            Authorization: token
        },
        data: data
    })
}