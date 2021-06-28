<template>
<view>
	<!--	账户信息：用户名、手机号、密码   	-->
	<view>
		<view style="color: gray; margin: 10upx 30upx;">账户信息</view>
		<view class="cu-form-group" style="margin-top: 10upx;">
				<view class="title">用户名</view>
				<input placeholder="请输入用户名" v-model="username"></input>
				<text class='cuIcon-roundclose' ></text>
		</view>
	</view>
		<!--	个人信息：学校名称、院系名称、学号、角色   	-->
	<view>
		<view style="color: gray; margin: 10upx 30upx;">个人信息</view>
		<view class="cu-form-group " style="margin-top: 10upx;">			
			<view class="title">学校名称</view>
			<input v-model="userschoool" type="text"></input>
			<text class='cuIcon-roundclose'></text>
		</view>
		<view class="cu-form-group">
			<view class="title">院系名称</view>
			<input v-model="userdepartment"></input>
			<text class='cuIcon-roundclose'></text>
		</view>
		<view class="cu-form-group">
			<view class="title">学号/工号</view>
			<input v-model="usersno" type="digit"></input>
			<text class='cuIcon-roundclose' ></text>
		</view>
	</view>
	<button class="button bg-blue" @click="sure">确认</button>
</view>
</template>  

<script>
	import {checkPhone,checkPwd,checkCode} from "@/common/common.js"
	
	export default {
		data() {
			return {
				user:null,
				username:'',//用户名
				userschoool:'',//用户学校
				userdepartment:'',//用户院系
				usersno:'',//用户学号
				userPhone:''
			}
		},
		onShow() {
			var that=this;
			that.user=uni.getStorageSync('data')
		},
		methods: {
			
			sure(){
				var that=this;
				uni.request({
					url:'http://112.74.55.61:8081/userinfo',
					header: {Authorization:uni.getStorageSync('token')},
					method:'PUT',
					data:{
						userName:that.username,
						userSchool:that.userschoool,
						userDept:that.userdepartment,
						userSno:that.usersno,
						userPhone:that.user.userPhone
					}, 
					success: (res) => {
						console.log(res.data)
						console.log(res.data.data)
						uni.switchTab({
							url:"home"
						})
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})
			}
		
		}
	}
</script>

<style lang="scss">
.cu-form-group .title {
		min-width: calc(4em + 15px);
	}

.button {
	font-size: 28rpx;		
	color: #fff;
	height: 90rpx;
	line-height: 90rpx;
	margin: 80rpx 20rpx 0 20rpx;
	border-radius: 50rpx;

}
</style>

