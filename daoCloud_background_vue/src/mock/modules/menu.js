// import Mock from 'mockjs'

// 登录
export function getMenus() {
    return {
        // isOpen: false,
        url: 'menu/getMenus',
        type: 'get',
        data: {
            'code': 200,
            'msg': 'success',
            'data': menuList
        }
    }
}

/*
    menuId       表示当前菜单项 ID
    parentId     表示父菜单项 ID
    name_zh      表示菜单名（中文）
    name_en      表示菜单名（英语）
    url          表示路由跳转路径（自身模块 或者 外部 url）
    type         表示当前菜单项的级别（0: 目录，1: 菜单项，2: 按钮）
    icon         表示当前菜单项的图标
    orderNum     表示当前菜单项的顺序
    subMenuList  表示当前菜单项的子菜单
*/
// var menuList = [{
//     'menuId': 1,
//     'parentId': 0,
//     'name_zh': '系统管理',
//     'name_en': 'System Control',
//     'url': '',
//     'type': 0,
//     'icon': 'el-icon-setting',
//     'orderNum': 0,
//     'subMenuList': [
//         {
//             'menuId': 4,
//             'parentId': 1,
//             'name_zh': '用户管理',
//             'url': 'sys/UserList',
//             'type': 1,
//             'icon': 'el-icon-menu',
//             'orderNum': 3,
//             'subMenuList': []
//         },
//         {
//             'menuId': 5,
//             'parentId': 1,
//             'name_zh': '签到管理',
//             'name_en': 'Menu Control',
//             'url': '',
//             'type': 1,
//             'icon': 'el-icon-menu',
//             'orderNum': 4,
//             'subMenuList': []
//         },
//         {
//             'menuId': 6,
//             'parentId': 1,
//             'name_zh': '班课管理',
//             'name_en': 'Class Control',
//             'url': 'sys/classList',
//             'type': 1,
//             'icon': 'el-icon-menu',
//             'orderNum': 5,
//             'subMenuList': []
//         },
//         {
//             'menuId': 7,
//             'parentId': 1,
//             'name_zh': '参数管理',
//             'url': 'sys/config',
//             'type': 1,
//             'icon': 'el-icon-menu',
//             'orderNum': 6,
//             'subMenuList': []
//         },
//         {
//             'menuId': 8,
//             'parentId': 1,
//             'name_zh': '数据字典管理',
//             'url': 'dictionary/dictionarylist',
//             'type': 1,
//             'icon': 'el-icon-menu',
//             'orderNum': 7,
//             'subMenuList': []
//         },
//     ]
// },
//     {
//         'menuId': 5,
//         'parentId': 0,
//         'name_zh': '帮助',
//         'name_en': 'Help',
//         'url': '',
//         'type': 0,
//         'icon': 'el-icon-info',
//         'orderNum': 1,
//         'subMenuList': []
//     }
// ]
var menuList = [{
    'menuId': 1,
    'nameZh': '系统管理',
    'orderNum': 0,
    'url': '',
    'subMenusList': [
        {
            'menuId': 4,
            'parentId': 1,
            'nameZh': '用户管理',
            'url': 'user/userInfo',
            'orderNum': 3,
        },
        {
            'menuId': 5,
            'parentId': 1,
            'nameZh': '签到管理',
            'orderNum': 4,
        },
        {
            'menuId': 6,
            'parentId': 1,
            'nameZh': '班课管理',
            'url': 'sys/classList',
            'orderNum': 5,
        },
        {
            'menuId': 7,
            'parentId': 1,
            'nameZh': '参数管理',
            'url': 'sys/config',
            'orderNum': 6,
        },
        {
            'menuId': 8,
            'parentId': 1,
            'nameZh': '数据字典管理',
            'url': 'dictionary/dictionarylist',
            'orderNum': 7,
        },
    ]
},
    {
        'menuId': 5,
        'nameZh': '帮助',
        'orderNum': 1,
        'subMenusList': []
    }
]