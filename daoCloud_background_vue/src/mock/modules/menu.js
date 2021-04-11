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
var menuList = [{
    'menuId': 1,
    'parentId': 0,
    'name_zh': '系统管理',
    'name_en': 'System Control',
    'url': '',
    'type': 0,
    'icon': 'el-icon-setting',
    'orderNum': 0,
    'subMenuList': [{
        'menuId': 2,
        'parentId': 1,
        'name_zh': '菜单管理',
        'name_en': 'Administrator List',
        'url': 'sys/UserList',
        'type': 1,
        'icon': 'el-icon-user',
        'orderNum': 1,
        'subMenuList': []
    },
        {
            'menuId': 3,
            'parentId': 1,
            'name_zh': '角色管理',
            'name_en': 'Role Control',
            'type': 1,
            'icon': 'el-icon-price-tag',
            'orderNum': 2,
            'subMenuList': []
        },
        {
            'menuId': 4,
            'parentId': 1,
            'name_zh': '用户管理',
            'url': 'sys/MenuControl',
            'type': 1,
            'icon': 'el-icon-menu',
            'orderNum': 3,
            'subMenuList': []
        },
        {
            'menuId': 5,
            'parentId': 1,
            'name_zh': '定时任务',
            'name_en': 'Menu Control',
            'url': '',
            'type': 1,
            'icon': 'el-icon-menu',
            'orderNum': 4,
            'subMenuList': []
        },
        {
            'menuId': 6,
            'parentId': 1,
            'name_zh': '班课管理',
            'name_en': 'Class Control',
            'url': 'sys/classList',
            'type': 1,
            'icon': 'el-icon-menu',
            'orderNum': 5,
            'subMenuList': []
        },
        {
            'menuId': 7,
            'parentId': 1,
            'name_zh': '参数管理',
            'url': 'sys/config',
            'type': 1,
            'icon': 'el-icon-menu',
            'orderNum': 6,
            'subMenuList': []
        },
        {
            'menuId': 8,
            'parentId': 1,
            'name_zh': '数据字典管理',
            'url': 'dictionary/dictionarylist',
            'type': 1,
            'icon': 'el-icon-menu',
            'orderNum': 7,
            'subMenuList': []
        },
    ]
},
    {
        'menuId': 5,
        'parentId': 0,
        'name_zh': '帮助',
        'name_en': 'Help',
        'url': '',
        'type': 0,
        'icon': 'el-icon-info',
        'orderNum': 1,
        'subMenuList': []
    }
]
// var menuList = [{
//         'menuId': 1,
//         'parentId': 0,
//         'name_zh': '系统管理',
//         'name_en': 'System Control',
//         'url': '',
//         'type': 0,
//         'icon': 'el-icon-setting',
//         'orderNum': 0,
//         'subMenuList': [{
//                 'menuId': 2,
//                 'parentId': 1,
//                 'name_zh': '菜单管理',
//                 'name_en': 'Administrator List',
//                 'url': '',
//                 'type': 1,
//                 'icon': 'el-icon-user',
//                 'orderNum': 1,
//                 'subMenuList': []
//             },
//             {
//                 'menuId': 3,
//                 'parentId': 1,
//                 'name_zh': '角色管理',
//                 'name_en': 'Role Control',
//                 'url': '',
//                 'type': 1,
//                 'icon': 'el-icon-price-tag',
//                 'orderNum': 2,
//                 'subMenuList': []
//             },
//             {
//                 'menuId': 4,
//                 'parentId': 1,
//                 'name_zh': '用户管理',
//                 'name_en': 'Menu Control',
//                 'url': '',
//                 'type': 1,
//                 'icon': 'el-icon-menu',
//                 'orderNum': 3,
//                 'subMenuList': [{
//                     'menuId': 5,
//                     'parentId': 4,
//                     'name_zh': '用户管理',
//                     'name_en': 'Menu Control',
//                     'type': 1,
//                     'icon': 'el-icon-menu',
//                     'orderNum': 3,
//                     'subMenuList': []
//                 }]
//             },
//             {
//                 'menuId': 5,
//                 'parentId': 1,
//                 'name_zh': '定时任务',
//                 'name_en': 'Menu Control',
//                 'url': '',
//                 'type': 1,
//                 'icon': 'el-icon-menu',
//                 'orderNum': 4,
//                 'subMenuList': []
//             },
//             {
//                 'menuId': 6,
//                 'parentId': 1,
//                 'name_zh': '班课管理',
//                 'name_en': 'Class Control',
//                 'url': '@/views/student/classList',
//                 'type': 1,
//                 'icon': 'el-icon-menu',
//                 'orderNum': 5,
//                 'subMenuList': []
//             },
//         ]
//     },
// ]
