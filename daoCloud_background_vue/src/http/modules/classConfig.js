import http from "../httpRequest"

export function getClassInfo(token) {
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

export function delClass(classCode, token) {
    return http({
        url: '/classes', params:{classCode},
        method: 'delete',
        headers: {
            Authorization: token
        },
    })
}

export function getClassByCode(classCode, token) {
    return http({
        url: '/codeClasses',params:{classCode},
        method: 'get',
        headers: {
            Authorization: token
        },
        // data: classCode
    })
}


export function detailTeacherClass(token, userphone) {
    return http({
        url: '/classesdto',params:{userphone},
        method: 'get',
        headers: {
            Authorization: token
        },
    })
}