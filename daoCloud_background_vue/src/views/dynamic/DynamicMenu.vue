<template>
    <el-submenu :index="menu.name">
        <template slot="title">
            <i :class="menu.meta.icon"></i>
            <span>{{menu.meta.nameZh}}</span>
        </template>
        <el-menu-item v-for="subMenu in menu.children" :key="subMenu.meta.menuId" :index="subMenu.name" @click="$router.push({ name: subMenu.name })">
            <i :class="subMenu.meta.icon"></i>
            <span slot="title">{{subMenu.meta.nameZh}}</span>
        </el-menu-item>
    </el-submenu>
</template>

<script>
    export default {
        name: 'DynamicMenu',
        props: {
            menu: {
                type: Object,
                required: true
            }
        }
    }
</script>

<style>
</style>