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
		<view class="cu-form-group " >			
			<view class="title">手机号</view>
			<input placeholder="请输入注册的手机号" v-model="userphone"></input>
		</view>
		<view class="cu-form-group">
			<view class="title">验证码</view>
			<input v-model="code"></input>
			<button v-if="showText" @tap="getCode()" class='cu-btn bg-blue'>发送验证码</button>
			<button v-else  class='cu-btn bg-green shadow' style="background-color: #A7A7A7;">重新发送({{ second }})</button>
		</view>
		<!--	设置密码/确认密码   	-->
		<view class="cu-form-group" >
			<view class="title">设置密码</view>
			<input v-model="password" password="true"></input>
			<text class='cuIcon-roundclose'></text>
		</view>
		<view class="cu-form-group">
			<view class="title">确认密码</view>
			<input v-model="confirmPassword" password="true"></input>
			<text class='cuIcon-roundclose'></text>
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
		<view class="cu-form-group">
			<view class="title">角色</view>
			<picker @change="PickerChange" :value="index" :range="picker">
				<view class="picker">
					{{index>-1?picker[index]:'请选择'}}
				</view>
			</picker>
		</view>
	</view>
	<button class="button bg-blue" @click="register">确认注册</button>
</view>
</template>  

<script>
	import {checkPhone,checkPwd,checkCode} from "@/common/common.js"
	
	export default {
		data() {
			return {
				username:'',//用户名
				userphone:'',//手机号
				password:'',//密码
				confirmPassword:'',//确认密码
				userschoool:'',//用户学校
				userdepartment:'',//用户院系
				usersno:'',//用户学号
				userole:'',//用户角色
				index: -1,
				picker: ['学生', '教师'],//角色选择
				code:'',//验证码
				second: 60, //默认60秒
				showText: true //判断短信是否发送
			}
		},
		methods: {
			//用户选择角色
			PickerChange(e) {
				this.index = e.detail.value
				this.userole=this.index==0?'student':'teacher'
			},
			//获取短信验证码
			getCode() { 			
				var that=this
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
				//这里请求后台获取短信验证码
				uni.request({
					url:'http://112.74.55.61:8081/verifiedcodes',
					method:'POST',
					data:{
						userphone:that.userphone,
						type:'S1'
					},
					success(res) {
						uni.showToast({
							title:"验证码已发送",
							icon:"none",
							duration:2000					
						})
						console.log(res)
					},
					fail() {
						console.log('链接失败')
					}					
				})
			},
			//注册账号
			register(){
				var that=this
				//检查手机号密码验证码格式
				if(checkPhone(this.userphone)){
					return
				};
				if(checkCode(this.code)){
					return
				};
				if(checkPwd(this.password)){
					return
				};
				//检查两次密码格式
				if(this.confirmPassword!==this.password){
					uni.showToast({
						title:'两次输入密码不一致',
						icon:'none'
					})
					this.confirmPassword=''
					return;
				};
				//验证验证码是否正确
				uni.request({
					url:'http://112.74.55.61:8081/verifiedcodesfromuser',
					method:'POST',
					data:{
						userphone:that.userphone,
						codefromuser:that.code,
						type:'S1'
					},
					success(res){
						console.log(res.data)
						that.temp=res.data.msg
						//验证码输入错误
						if(res.data.data==="codenotexist"){
							uni.showToast({
								title:"验证码不存在，已过期",
								icon:"none",
								duration:2000					
							}),
							that.code=''
							return
						}	
						//验证码过期
						else if(res.data.data==="failed"){
							uni.showToast({
								title:"验证码错误",
								icon:"none",
								duration:2000					
							}),
							that.code=''
							return
						}
						
						if(res.data.data==="success"){
							uni.request({
								url:'http://112.74.55.61:8081/signup',
								method:'POST',
								data:{
									username:that.username,
									userphone:that.userphone,
									password:that.password,
									userschoool:that.userschoool,
									userdepartment:that.userdepartment,
									usersno:that.usersno,
									userole:that.userole,
									mobiledevice:'MOBILEDEVICE'
								},
								success(res) {
									if(res.data.code==200 && res.data.msg=="添加成功"){
										console.log("注册成功")
										console.log(res)
										uni.navigateTo({
											url:"login"
										})
									}
									else{
										uni.showToast({ title: res.data.msg, icon: 'none' });
										console.log(res)
									}
								},
								fail() {
									console.log('sss')
								}
							})
							
						}
					},
					fail(){
						console.log('链接失败')
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
