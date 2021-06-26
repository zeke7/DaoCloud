<template>
	<view class="t-login">
		<!-- 页面装饰图片 -->
		<!-- <image class="img-a" src="@/static/2.png"></image> -->
		<image class="img-b" src="@/static/3.png"></image>
		<!-- 标题 -->
		
		<image src="@/static/Class.png" ></image>
		<view class="login_header_title">
			<text :class="[cur ? 'choose1':'choose2']" @click="onChoose()"> 短信登录</text>
			<text  :class="[!cur ? 'choose3':'choose4']"  @click="onChoose()">密码登录</text>			
		</view>
		<view >
			<!-- 输入手机号获取验证码登录 -->
			<view class="t-a" v-if="!cur">
				<image src="@/static/phone.png" style="width: 60upx;"></image>
				<input type="number" name="phone" placeholder="请输入手机号" maxlength="11" v-model="userphone" />
			</view>
			<view class="t-a" v-if="!cur">
				<image src="@/static/code.png"  style="width: 60upx;"></image>
				<input type="number" name="code" maxlength="6" placeholder="请输入验证码" v-model="code" />
				<view v-if="showText" class="t-c" @tap="getCode()">发送验证码</view>
				<view v-else class="t-c" style="background-color: #A7A7A7;">重新发送({{ second }})</view>
			</view>
			<!-- 输入用户名及密码登录 -->
			<view class="t-a" v-if="cur">
				<image src="@/static/user.png"  style="width: 60upx;"></image>
				<input type="number" name="username" placeholder="请输入用户名" maxlength="11" v-model="username" />
			</view>
			<view class="t-a" v-if="cur">
				<image src="@/static/password.png"  style="width: 60upx;"></image>
				<input  name="password"  placeholder="请输入密码" v-model="password" :password="true"/>
			</view>
			<!-- 注册账号及忘记密码 -->
			<view class="t-bottom">
				<text class="t-l" @click="go_register">注册账号</text>
				<text class="t-r" @click="go_forgetpassword">忘记密码</text>
			</view>
			<!-- 两种验证方式 -->
			<button @tap="login_code()" v-if="!cur">登录/快速注册</button>
			<button @tap="login_password()" v-if="cur">登 录</button>
		</view>
		
	</view>
</template>

<script>
	import {checkPhone,checkPwd,checkCode} from "@/common/common.js"
	export default {
		data() {
			return {
				cur:false,  //当前登录方式
				second: 60, //默认60秒
				showText: true, //判断短信是否发送
				userphone: '', //手机号码
				code: '', //验证码			
				username:'', //用户名
				password:''//登录密码
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
					url:"register"
				})
			},
			//忘记密码
			go_forgetpassword(){
				uni.navigateTo({
					url:"forget-password"
				})
			},
			test(){
				console.log(this.username,this.password)
			},
			//获取短信验证码
			getCode() { 			
				var that=this
				let type="";
				console.log(that.userphone)
				if (checkPhone(this.userphone)) {
				    return
				}
				var interval = setInterval(() => {
					this.showText = false;
					var times = this.second - 1;
					//that.second = times<10?'0'+times:times ;//小于10秒补 0
					this.second = times;
				}, 1000);
				setTimeout(() => {
					clearInterval(interval);
					this.second = 60;
					this.showText = true;
				}, 60000);
				//判断用户是否存在
				uni.request({
					url:'http://112.74.55.61:8081/accountexist'+'?username='+that.userphone,
					method:'POST',
					success(res) { 
						//账号存在=>登录 type‘L0’
						//账号不存在=>快速注册 type‘S1’
						console.log(res.data)
						if(res.data.data==null){
							type='S1'
						}else{
							type='L0'					
						}
						uni.request({
							url:'http://112.74.55.61:8081/verifiedcodes',
							method:'POST',
							data:{
								userphone:that.userphone,
								type:type
							},
							success(res) {
								uni.showToast({
									title:"验证码已发送",
									icon:"none",
									duration:2000					
								})
								//console.log(res)
							},
							fail() {
								console.log('链接失败')
							}					
						})
						console.log(type)
					},
					fail() {
						console.log('链接失败')
					}					
				})
			},
			//短信验证码登录(快速注册)
			login_code() {
				var that=this
				uni.request({
					url:'http://112.74.55.61:8081/loginbysms',
					method:'POST',
					data:{
						userphone:that.userphone,
						username:that.userphone,
						codefromuser:that.code,
						mobiledevice:'MOBILEDEVICE'
					},
					success(res){
						console.log(res.data)
						// //验证码输入错误
						if(res.data.data!=='failed'){
							console.log("登录成功")
							console.log(res.data)
							uni.setStorageSync('token',res.header.Authorization)
							uni.switchTab({
								url:"../home/home"
							})
						}
						else{
							uni.showToast({ title: '验证码错误', icon: 'none' });
						}
					},
					fail(){
						console.log('链接失败')
					}
				})			
				
			},
			//用户名及登录
			login_password() {
				var that=this
				if (checkPhone(this.username)) {
				    return
				}				
				uni.request({ 
					url:'http://112.74.55.61:8081/login',
					method:'POST',
					data:{
						username:that.username,
						password:that.password
					},				
					success:(res)=>{
						console.log(res)
						console.log(res.data);
						if(res.data.msg==='登录成功(Login Success.)'){
							uni.setStorageSync('token',res.header.Authorization)
							uni.setStorageSync('data',res.data.data)//保存用户信息
							uni.switchTab({
								url:"../home/home"
							})
						}
						else{
							uni.showToast({ title: '用户名或密码错误！', icon: 'none' });
						}
					},
					fail: (res) => {
						console.log("连接失败")
					}
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
