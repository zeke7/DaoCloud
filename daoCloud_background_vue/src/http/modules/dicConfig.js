import http from '../httpRequest'

export function getDictionary(token) {
    return http({
        url: '/alldictionaries',
        method: 'get',
        headers: {
            Authorization: token
        }
    })
}

export function dicCurd(method, data, token) {
    return http({
        url: '/dictionaries',
        method: method,
        headers: {
            Authorization: token
        },
        data: data
    })
}