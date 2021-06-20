import http from '../httpRequest'

export function getDictionary(token) {
    return http({
        url: '/dicdto',
        method: 'get',
        headers: {
            Authorization: token
        }
    })
}

export function dicDelCurd(method, data, token) {
    return http({
        url: '/dictionarydetails',
        method: method,
        headers: {
            Authorization: token
        },
        data: data
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


export function delDic(dicCode, token) {
    return http({
        url: '/dictionaries', params:{dicCode},
        method: 'delete',
        headers: {
            Authorization: token
        },
    })
}

export function selectDic(token, dicCode) {
    return http({
        url: '/singledicdto', params:{dicCode},
        method: 'get',
        headers: {
            Authorization: token
        }
    })
}

export function detailDicCurd(method, data, token) {
    return http({
        url: '/dictionarydetails',
        method: method,
        headers: {
            Authorization: token
        },
        data: data
    })
}

export function delDetailDic(detailcode, token) {
    return http({
        url: '/dictionarydetails', params:{detailcode},
        method: 'delete',
        headers: {
            Authorization: token
        },
    })
}
