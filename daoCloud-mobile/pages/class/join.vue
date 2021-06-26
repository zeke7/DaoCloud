<template>
	<view>
		<view class="cu-form-group" style="margin-top: 375rpx;">
			<input placeholder="请输入班课号" name="input" v-model="classNo" type="number"></input>
			<text class="cuIcon-camera" @click="scan"></text>
		</view>
		<view style="color: gray; margin: 10upx 10upx;">可扫描二维码自动填写班课号</view>
		<button class="button bg-blue"  @click="joinClass()">加入班课</button>
	</view>
</template>

<script>
	import {addInformation} from "@/common/joinTips.js" 
	export default {
		data() {
			return {
				classNo:"",
				Class:"工程实践",
				user:null
			}
		},
		onShow() {
			var that=this
			that.user=uni.getStorageSync('data')
		},
		methods: {
			//根据班课号获取信息
			getClassById(){
				var that=this
				uni.request({
					url:'http://112.74.55.61:8081//codeClasses'+'?classCode='+that.classNo,
					header: {Authorization:uni.getStorageSync('token')},
					method:'GET',
					success: (res) => {
						console.log(res.data)
						that.Class=res.data.data.className
				
					},
					fail: (res) => {
						console.log(res)
				
					}
				})
			},
			//根据输入的班课号加入班课
			joinClass(){
				var that=this;
				if(that.classNo==''){
					that.classNo=''
					uni.showToast({
					title: '请输入班课号',
					icon:'none',
					duration: 2000
					});				
					return
				}
				uni.request({
					url:'http://112.74.55.61:8081//codeClasses'+'?classCode='+that.classNo,
					header: {Authorization:uni.getStorageSync('token')},
					method:'GET',
					success: (res) => {
						console.log(res.data)
						if(res.data.data==null){
							that.classNo=''
							uni.showToast({
							title: '班课不存在',
							icon:'none',
							duration: 2000
							});				
						}else{
							that.Class=res.data.data.className
							uni.showModal({
									title: that.Class,
									content: '是否确定加入该班课',
									success: function (res) {
										if (res.confirm) {
											uni.request({
												url:'http://112.74.55.61:8081/inclasses',
												header: {Authorization:uni.getStorageSync('token')},
												method:'POST',
												data:{
													classcode:that.classNo,
													username:that.user.userName,
													userphone:that.user.userPhone
												},
												success: (res) => {
													console.log(res.data)
													addInformation(res.data.msg)
													that.classNo=''
												},
												fail: (res) => {
													console.log(res)
												}
											})
										} else if (res.cancel) {
											console.log('用户点击取消');
										}
									}
							});
						}	
					},
					fail: (res) => {
						console.log(res)					
					}
				})
			},	
			scan() { //扫码验证
				var that = this;
				uni.scanCode({
			　　　　 onlyFromCamera: false, //为true只允许相机扫码，不加允许相册扫码
					success: function(res) {
						uni.showToast({
							title: '扫码成功'
						})
						that.classNo=res.result
						that.joinClass()
					},
					fail: function(err) {
						console.log('扫码失败', err)
					}
				})
			}
		
		}
	}
</script>

<style>
	
</style>

