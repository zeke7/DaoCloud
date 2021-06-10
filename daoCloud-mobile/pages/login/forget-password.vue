<template>
	<view>
		<!--	手机号/验证码   	-->
		<view>
			<image src="../../static/forgetpassword.png" style="width: 700rpx; height: 450rpx; margin-left: 25rpx;"></image>
			<view style="color: gray; margin: 10upx 30upx;">信息验证</view>
			<view class="cu-form-group " style="margin-top: 10upx;">			
				<view class="title" v-model="userphone">手机号</view>
				<input placeholder="请输入注册的手机号" v-model="userphone"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">验证码</view>
				<input v-model="code"></input>
				<button v-if="showText" @tap="getCode()" class='cu-btn bg-blue'>发送验证码</button>
				<button v-else  class='cu-btn bg-green shadow' style="background-color: #A7A7A7;">重新发送({{ second }})</button>
			</view>
		</view>
		<!--	密码修改   	-->
		<view>
			<view style="color: gray; margin: 10upx 30upx;">密码修改</view>
			<view class="cu-form-group " style="margin-top: 10upx;">			
				<view class="title">新密码</view>
				<input v-model="newPassword"></input>
				<text class='cuIcon-roundclose'></text>
			</view>
			<view class="cu-form-group">
				<view class="title">确认密码</view>
				<input v-model="confirmPassword"></input>
				<text class='cuIcon-roundclose'></text>
			</view>
		</view>
		<button class="button bg-blue"  @click="repassword">确认修改</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				second: 60, //默认60秒
				showText: true, //判断短信是否发送
				userphone:"",//手机号
				newPassword:"",//新密码
				confirmPassword:"",//确认密码
				code:"",//验证码
			}
		},
		methods: {
			repassword(){
				var that=this
				//检查手机号密码验证码格式
				if(checkPhone(this.userphone)){
					return
				};
				if(checkCode(this.code)){
					return
				};
				if(checkPwd(this.newpassword)){
					return
				};
				//检查两次密码格式
				if(this.confirmPassword!==this.newPassword){
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
						type:'R2'
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
								url:'http://112.74.55.61:8081/backpassword',
								method:'POST',
								data:{
									userphone:that.userphone,
									password:that.password,
									codefromuser:that.code
								},
								success(res) {
									console.log(res)
									uni.navigateTo({
										url:"login"
									})
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

<style>

.button {
	font-size: 28rpx;		
	color: #fff;
	height: 90rpx;
	line-height: 90rpx;
	margin: 80rpx 20rpx 0 20rpx;
	border-radius: 50rpx;
}
</style>
