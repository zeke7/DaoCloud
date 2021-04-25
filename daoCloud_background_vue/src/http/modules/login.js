import http from '@/http/httpRequest.js'

export function getToken(data) {
    return http({
        url: '/login',
        // url: '/auth/token',
        method: 'post',
        // data: {
        //     userName,
        //     password
        // }
        data
    })
}

export function loginByCode(userphone, codefromuser) {
    return http({
        url: '/verifiedcodes', params:{userphone},
        method: 'post',
        data: {
            userphone,
            codefromuser
        }
    })
}

export function register(data) {
    return http({
        url: '/signup',
        method: 'post',
        data
    })
}

export function getCaptcha(userPhone) {
    return http({
        url: '/verifiedcodes', params:{userPhone},
        method: 'get',
        data: {
            userPhone
        }
    })
}


export function modifyPassword(username, password, newpassword) {
    return http({
        url: '/reset-password',
        method: 'post',
        data: {
            username,
            password,
            newpassword
        }
    })
}

export function forgetPassword(data) {
    return http({
        url: '/backpassword',
        method: 'post',
        data
    })
}

export function getClassInfo(userphone) {
    return http({
        url: '/classesdto', params: {userphone},
        method: 'get',
        data: {
            userphone
        }
    })
}

export function getDictionary() {
    return http({
        url: '/alldictionarydetails',
        method: 'get',
        data: {

        }
    })
}
export function getParamters() {
    return http({
        url: '/allsystemparamters',
        method: 'get',
        data: {

        }
    })
}
