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

export function loginByCode(userphone, codefromuser, type) {
    return http({
        url: '/loginbysms',
        method: 'post',
        data: {
            userphone,
            codefromuser,
            type
        }
    })
}

export function register(userphone, username, password, userschool, userdepartment, userSno, userole, codefromuser, mobiledevice, type) {
    return http({
        url: '/signup',
        method: 'post',
        data: {
            userphone,
            username,
            password,
            userschool,
            userdepartment,
            userSno,
            userole,
            codefromuser,
            mobiledevice,
            type
        }
    })
}

export function getCaptcha(userphone, type) {
    return http({
        url: '/verifiedcodes',
        method: 'post',
        data:{
            userphone,
            type
        }
    })
}

export function modifyPassword(username, password, newpassword, token) {
    return http({
        url: '/reset-password',
        method: 'post',
        headers: {
			Authorization: token
		},
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

export function getClassInfo(userphone, token) {
    return http({
        url: '/classesdto', params: {userphone},
        method: 'get',
        headers: {
            Authorization: token
        }
        // data: {
        //     userphone
        // }
    })
}

export function getDictionary(token) {
    return http({
        url: '/alldictionarydetails',
        method: 'get',
        headers: {
            Authorization: token
        }
    })
}
export function getParamters(token) {
    return http({
        url: '/allsystemparamters',
        method: 'get',
        headers: {
            Authorization: token
        }
    })
}
