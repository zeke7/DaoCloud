<template>
	<view>
		<view class="cu-form-group" style="margin-top: 375rpx;">
			<input placeholder="请输入班课号" name="input" v-model="classNo" type="number"></input>
			<text class="cuIcon-camera" @click="scan"></text>
		</view>
		<view style="color: gray; margin: 10upx 10upx;">可扫描二维码自动填写班课号</view>
		<button class="button bg-blue"  @click="go_home()">加入班课</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				classNo:"",
				Class:"工程实践",
				joinClass:[],
				mes:null
			}
		},
		onLoad() {
			var that=this
			that.mes=uni.getStorageSync('data')
		},
		methods: {
			scan() { //扫码验证
				var that = this;
				uni.scanCode({
			　　　　　onlyFromCamera: false, //为true只允许相机扫码，不加允许相册扫码
					success: function(res) {
						uni.showToast({
							title: '扫码成功'
						})
						that.classNo=res.result
						if(that.classNo=='111111'){
							that.classNo=''
							uni.showToast({
							title: '班课不存在',
							icon:'none',
							duration: 2000
							});
							
							return
							
						}
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
												username:that.mes.userName,
												userphone:that.mes.userPhone
											},
											success: (res) => {
												console.log(res.data)
												if(res.data.msg=="该用户已在该班课中"){
													uni.showToast({
													title: '已加入该班课',
													icon:'none',
													duration: 2000
													});
													that.classNo=""
												}else if(res.data.msg=="班课不允许加入"){
													uni.showToast({
													title: '班课不允许加入',
													icon:'none',
													duration: 2000
													});
													that.classNo=""
												}else if(res.data.msg=="班课加入失败，参数没有通过有效性验证。"){
													uni.showToast({
													title: '班课已经结束',
													icon:'none',
													duration: 2000
													});
													that.classNo=""
												}else{															
													uni.showToast({
													title: '加入成功',
													icon:'none',
													duration: 2000
													});
													uni.switchTab({
														url:'../home/home'
													})	
												}
												
											},
											fail: (res) => {
												console.log(res)
												console.log("连接失败")
											}
										})
									} else if (res.cancel) {
										console.log('用户点击取消');
									}
								}
						});
					},
					fail: function(err) {
						console.log('扫码失败', err)
						if(that.classNo=='111111'){
							that.classNo=''
							uni.showToast({
							title: '班课不存在',
							icon:'none',
							duration: 2000
							});
							
							return
							
						}
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
												username:that.mes.userName,
												userphone:that.mes.userPhone
											},
											success: (res) => {
												console.log(res.data)
												if(res.data.msg=="该用户已在该班课中"){
													uni.showToast({
													title: '已加入该班课',
													icon:'none',
													duration: 2000
													});
													that.classNo=""
												}else if(res.data.msg=="班课不允许加入"){
													uni.showToast({
													title: '班课不允许加入',
													icon:'none',
													duration: 2000
													});
													that.classNo=""
												}else if(res.data.msg=="班课加入失败，参数没有通过有效性验证。"){
													uni.showToast({
													title: '班课已经结束',
													icon:'none',
													duration: 2000
													});
													that.classNo=""
												}else{															
													uni.showToast({
													title: '加入成功',
													icon:'none',
													duration: 2000
													});
													uni.switchTab({
														url:'../home/home'
													})	
												}
												
											},
											fail: (res) => {
												console.log(res)
												console.log("连接失败")
											}
										})
									} else if (res.cancel) {
										console.log('用户点击取消');
									}
								}
						});
					}
				})
			},
			//根据输入的班课号获取信息
			async getClassById(){
				var that=this
				await uni.request({
					url:'http://112.74.55.61:8081//codeClasses'+'?classCode='+that.classNo,
					header: {Authorization:uni.getStorageSync('token')},
					method:'GET',
					success: (res) => {
						console.log(res.data)
						that.Class=res.data.data.className
						console.log(that.Class+"前")
					},
					fail: (res) => {
						console.log(res)
	
					}
				})
			},
			// getAllClass: () => {
			//     return new Promise((resolve, reject) => {
			//        var that=this
			//        uni.request({
			//        	url:'http://112.74.55.61:8081//codeClasses'+'?classCode='+that.classNo,
			//        	header: {Authorization:uni.getStorageSync('token')},
			//        	method:'GET',
			//        	success: (res) => {
			//        		console.log(res.data)
			//        		that.Class=res.data.data.className;
			//        		console.log(that.Class+"前")
			//        	},
			//        	fail: (res) => {
			//        		console.log(res)
			//        	}
			//        })
			//     })
			// },
			//根据输入的班课号加入班课
			go_home(){
				var that=this;
				if(that.classNo=='111111'){
					that.classNo=''
					uni.showToast({
					title: '班课不存在',
					icon:'none',
					duration: 2000
					});
					
					return
					
				}
				that.getClassById();
				
				console.log(that.Class+"后")
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
										username:that.mes.userName,
										userphone:that.mes.userPhone
									},
									success: (res) => {
										console.log(res.data)
										if(res.data.msg=="该用户已在该班课中"){
											uni.showToast({
											title: '已加入该班课',
											icon:'none',
											duration: 2000
											});
											that.classNo=""
										}else if(res.data.msg=="班课不允许加入"){
											uni.showToast({
											title: '班课不允许加入',
											icon:'none',
											duration: 2000
											});
											that.classNo=""
										}else if(res.data.msg=="班课加入失败，参数没有通过有效性验证。"){
											uni.showToast({
											title: '班课不存在',
											icon:'none',
											duration: 2000
											});
											that.classNo=""
										}else{															
											uni.showToast({
											title: '加入成功',
											icon:'none',
											duration: 2000
											});
											uni.switchTab({
												url:'../home/home'
											})	
										}
										
									},
									fail: (res) => {
										console.log(res)
										console.log("连接失败")
									}
								})
							} else if (res.cancel) {
								console.log('用户点击取消');
							}
						}
				});
			}			
		}
	}
</script>

<style>
	
</style>

