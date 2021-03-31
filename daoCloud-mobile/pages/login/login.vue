<template>
	<view class="t-login">
		<!-- 页面装饰图片 -->
		<!-- <image class="img-a" src="@/static/2.png"></image> -->
		<image class="img-b" src="@/static/3.png"></image>
		<!-- 标题 -->
		
		<image  src="@/static/class.png"></image>
		<!-- <view class="t-b">{{ title }}</view> -->
		<view class="login_header_title">
			<text :class="[cur ? 'choose1':'choose2']" @click="onChoose()"> 短信登录</text>
			<text  :class="[!cur ? 'choose3':'choose4']"  @click="onChoose()">密码登录</text>			
		</view>
		<view >
			<view class="t-a" v-if="!cur">
				<image src="@/static/phone.png"></image>
				<input type="number" name="phone" placeholder="请输入手机号" maxlength="11" v-model="phone" />
			</view>
			<view class="t-a" v-if="!cur">
				<image src="@/static/code.png"></image>
				<input type="number" name="code" maxlength="6" placeholder="请输入验证码" v-model="code" />
				<view v-if="showText" class="t-c" @tap="getCode()">发送验证码</view>
				<view v-else class="t-c" style="background-color: #A7A7A7;">重新发送({{ second }})</view>
			</view>
			<view class="t-a" v-if="cur">
				<image src="@/static/user.png"></image>
				<input type="number" name="user" placeholder="请输入用户名" maxlength="11" v-model="user" />
			</view>
			<view class="t-a" v-if="cur">
				<image src="@/static/password.png"></image>
				<input type="number" name="password" maxlength="6" placeholder="请输入密码" v-model="password" :password="true"/>
			</view>
			<view class="t-bottom">
				<text class="t-l" @click="go_register">注册账号</text>
				<text class="t-r" @click="go_forgetpassword">忘记密码</text>
			</view>
			<button @tap="login_code1()" v-if="!cur">登 录</button>
			<button @tap="login_password()" v-if="cur">登 录</button>
		</view>
		
	</view>
</template>

<script>
	import {checkPhone,checkPwd,checkCode} from "@/common/common.js"
	export default {
		data() {
			return {
				title: '欢迎回来！', 
				second: 60, //默认60秒
				showText: true, //判断短信是否发送
				phone: '', //手机号码
				code: '', //验证码
				cur:false  //当前登录方式
			}
		},
		methods: {
			onChoose(){
				this.cur=!this.cur,
				console.log(this.cur)
			},
			//注册账号
			go_register(){
				uni.navigateTo({
					url:"../register/register"
				})
			},
			//忘记密码
			go_forgetpassword(){
				uni.navigateTo({
					url:"../forget-password/forget-password"
				})
			},
			//短信登录
			login_code1(){
				uni.navigateTo({
					url:"../home/home"
				})
			},
			login_code() {			
				if (checkPhone(this.phone)) {
				    return
				}
				if (!this.code) {
					uni.showToast({ title: '请输入验证码', icon: 'none' });
					return;
				}
				//....此处省略，这里需要调用后台验证一下验证码是否正确，根据您的需求来
				uni.showToast({ title: '登录成功！', icon: 'none' });
			},
			//密码登录
			login_password() {
				var that = this;
				if (checkPhone(this.phone)) {
				    return
				}
				if (!that.yzm) {
					uni.showToast({ title: '请输入验证码', icon: 'none' });
					return;
				}
				//....此处省略，这里需要调用后台验证一下验证码是否正确，根据您的需求来
				uni.showToast({ title: '登录成功！', icon: 'none' });
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
			}
		}
	}
</script>

<style lang="scss">
.img-a {
	position: absolute;
	width: 100%;
	top: -280rpx;
	right: -100rpx;
}
.t-login {
	width: 600rpx;
	margin: 0 auto;
	font-size: 28rpx;
	color: #000;
	position: relative;
	//border: 1px green solid;
	button {
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		margin-top: 160rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
	}
	input {
		padding: 0 20rpx 0 120rpx;
		height: 90rpx;
		line-height: 90rpx;
		margin-bottom: 20rpx;
		background: #f8f7fc;
		border: 1px solid #e9e9e9;
		font-size: 28rpx;
		border-radius: 50rpx;
	}
	.t-a {
	position: relative;
	image {
		width: 40rpx;
		height: 40rpx;
		position: absolute;
		left: 40rpx;
		top: 28rpx;
		border-right: 2rpx solid #dedede;
		padding-right: 20rpx;
	}
	.t-c {
		position: absolute;
		z-index: 3;
		right: 22rpx;
		top: 22rpx;
		background: #5677fc;
		color: #fff;
		font-size: 24rpx;
		border-radius: 50rpx;
		height: 50rpx;
		line-height: 50rpx;
		padding: 0 25rpx;
	}
}
	.t-bottom{
		.t-l{
			position: absolute;
			color: #5677fc;
			left: 20rpx;			
		}	
		.t-r{
			position: absolute;
			color: #5677fc;
			right: 20rpx;
		}
	}

}

.t-b {
	text-align: center;
	font-size: 46rpx;
	color: #000;
	padding: 300rpx 0 120rpx 0;
	font-weight: bold;
}

.login_header_title{
	position: relative;
	// border: 1px yellow solid;
	padding: 30rpx 0 30rpx 0;
	margin: 0 0 30rpx 0;
	.choose1{
		position: absolute;
		left: 150rpx;
	}
	.choose2{
		position: absolute;
		left: 150rpx;
		border-bottom: 1px #5677fc solid;
		color: #5677fc;
	}
	.choose3{
		position: absolute;
		left: 350rpx;
	}
	.choose4{
		position: absolute;
		left: 350rpx;
		border-bottom: 1px #5677fc solid;
		color: #5677fc;
	}
}
</style>
