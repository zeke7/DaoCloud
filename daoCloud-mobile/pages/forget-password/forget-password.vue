<template>
	<view class="start">
		<image class="img-b" src="@/static/3.png"></image>
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
				<view class="input-label">新密码</view>
				<view class="input-body">
					<input v-model="newPassword" type="text"  style="margin-right: 50upx;" placeholder="请输入新密码" maxlength="20"  class="input" />
				</view>
			</view>
			<view class="input-item">
				<view class="input-label">确认密码</view>
				<view class="input-body">
					<input v-model="confirmPassword" type="text"  style="margin-right: 50upx;" placeholder="请确认新密码" maxlength="20"  class="input" />
				</view>
			</view>
		</view>
		<button class="button" @click="submit">修改密码</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				second: 60, //默认60秒
				showText: true, //判断短信是否发送
				phone:"",//手机号
				newPassword:"",//新密码
				confirmPassword:"",//确认密码
				code:"",//验证码
			}
		},
		methods: {
			
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
