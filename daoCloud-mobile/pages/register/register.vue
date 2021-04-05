<template>
	<view class="start">
		<image class="img-b" src="@/static/3.png" ></image>
<!-- 		<image src="@/static/school.png" ></image> -->
		<view class="input-box">
			<view class="input-item">
			    <view class="input-label">手机号</view>
				<view class="input-body">
					<input v-model="phone" maxlength="11" type="number" style="margin-right: 160upx;" placeholder="请输入手机号" class="input" >
					<view v-if="showText" class="btn-code" @tap="getCode()">发送验证码</view>
					<view v-else class="btn-code" >重新发送({{ second }})</view>
				</view>
			</view>
			 <view class="input-item">
			 	<view class="input-label">验证码</view>
			 	<view class="input-body">
			 		<input placeholder="请输入验证码" type="number" v-model="code" maxlength="6" class="input">
			 	</view>
			 </view>
			<view class="input-item">
				<view class="input-label">密码</view>
				<view class="input-body">
					<input v-model="password" type="text" :password="isHidePassword?true:false" style="margin-right: 50upx;" placeholder="请输入密码" maxlength="20"  class="input" />
					<image class="eye"  v-if="isHidePassword" src="../../static/attention.png"  @click="isHidePasswordClick()"></image>
					<image class="eye"  v-if="!isHidePassword" src="../../static/attention_forbid.png" @click="isHidePasswordClick()"></image>
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">确认密码</view>
				<view class="input-body">
					<input v-model="password" type="text" :password="isHidePassword?true:false" style="margin-right: 50upx;" placeholder="请再次输入密码" maxlength="20"  class="input" />
					<image class="eye"  v-if="isHidePassword" src="../../static/attention.png"  @click="isHidePasswordClick()"></image>
					<image class="eye"  v-if="!isHidePassword" src="../../static/attention_forbid.png" @click="isHidePasswordClick()"></image>
				</view>
			</view>
		</view>
		<button class="button" @click="submit">注 册</button>
	</view>
</template>

<script>
	import {checkPhone,checkPwd,checkCode} from "@/common/common.js"
	export default {
		data() {
			return {
				second: 60, //默认60秒
				showText: true, //判断短信是否发送
				phone:"",//手机号
				password:"",//密码
				confirmPassword:"",//确认密码
				code:"",//验证码
				isHidePassword:true
			}
		},
		methods: {
			isHidePasswordClick(){
				this.isHidePassword=!this.isHidePassword
			},
			//获取短信验证码
			getCode() { 
				if (checkPhone(this.phone)) {
				    return
				}
				var interval = setInterval(() => {
					this.showText = false;
					var times = this.second - 1;
					//that.second = times<10?'0'+times:times ;//小于10秒补 0
					this.second = times;
					console.log(times);
				}, 1000);
				setTimeout(() => {
					clearInterval(interval);
					this.second = 60;
					this.showText = true;
				}, 60000);
				//这里请求后台获取短信验证码
				uni.showToast({
					duration:2000
				})
			},
			submit() {
				if(!checkPhone(this.phone)){
					return;
				};
				if(!checkPwd(this.password)){
					return;
				};
				if(!checkCode(this.code)){
					return;
				};
				/*
				* 注册逻辑
				*/
				this.$store.commit('update',['isLogin',true]);
				uni.reLaunch({
					url:'/pages/index/index'
				})
			}
		}
	}
</script>

<style lang="scss">
.start{
	width: 700rpx;
	margin: 0 auto;
	.button{
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		margin-top: 200rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
		}
}


</style>
