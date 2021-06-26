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

export function register(userphone, username, password, userschool, userdepartment, usersno, userole, codefromuser, mobiledevice, type) {
    return http({
        url: '/signup',
        method: 'post',
        data: {
            userphone,
            username,
            password,
            userschool,
            userdepartment,
            usersno,
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
        url: '/resetpassword',
        method: 'post',
        header: token,
        data: {
            username,
            password,
            newpassword
        }
    })
}

export function forgetPassword(userphone, newpassword, codefromuser, type) {
    return http({
        url: '/backpassword',
        method: 'post',
        data: {
            userphone,
            newpassword,
            codefromuser,
            type
        }
    })
}






