import http from "../httpRequest";

export function getUserInfo(method, token, data) {
    return http({
        url :'/userinfo',
        headers: {
            Authorization: token
        },
        method: method,
        data: data
    })
}

export function getStudentInfo(token, classCode) {
    return http({
        url: '/studentinfo', params: {classCode},
        headers: {
            Authorization: token
        },
        method: 'get',
    })
}

export function userUpdate(token, data) {
    return http({
        url: '/user',
        headers: {
            Authorization: token
        },
        method: 'put',
        data: data
    })
}

export function getSingleUser(token, userphone) {
    return http({
        url: '/user', params: {userphone},
        headers: {
            Authorization: token
        },
        method: 'get',
    })
}

export function delUser(token, userphone) {
    return http({
        url: '/user', params: {userphone},
        headers: {
            Authorization: token
        },
        method: 'delete',
    })
}

