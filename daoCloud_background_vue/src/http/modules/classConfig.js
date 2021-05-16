import http from "../httpRequest"

export function getClassInfo(userphone, token) {
    return http({
        url: '/allclassesdto',
        method: 'get',
        headers: {
            Authorization: token
        }
    })
}

export function classCurd(method, data, token) {
    return http({
        url: '/classes',
        method: method,
        headers: {
            Authorization: token
        },
        data: data
    })
}