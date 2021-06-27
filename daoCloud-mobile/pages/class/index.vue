<template>
	<view>
		<view v-if="isClose=='1'">
			<image src="../../static/classClose.png" style="margin-left: 60rpx;margin-top: 30rpx;"></image>
			<view style="text-align: center;font-size: 30rpx;">
				课程已关闭!
			</view>
		</view>
		<view v-else class="page_edu">
			<view class="page_edu_header">
				<view class="header_content">
					<view class="left">
						<text class="title">{{className}}</text>
						<text class="sub_title" style="font-size: 50rpx;">{{classCode}}</text>
					</view>
					<view>
						<image src="/static/right.png" style="width: 131px;height: 122px;"></image>
					</view>
				</view>
			</view>
			
			<view class="page_content">
				<view class="menu">
					<!-- 教师发起签到、学生参加签到 -->
					<template>
						<view v-if="classType=='0'" class="item" >
							<view class="img_view" :style="{background: menus[0].bg}">
								<image :src="menus[0].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="signin"></image>
							</view>
							<text class="txt">{{menus[0].txt}}</text>
						</view>
						<view v-else class="item" >
							<!-- 当前没有签到，教师发起签到 -->
							<view v-if="startTime==''">
								<view class="img_view" :style="{background: menus[0].bg}">
									<image :src="menus[0].icon" class="image" style="height: 80rpx; width: 80rpx;" @tap="showModal" data-target="bottomModal"></image>
								</view>
								<text class="txt">发起签到</text>
								<!-- 签到方式模态框 -->
								<view class="cu-modal bottom-modal" :class="modalName=='bottomModal'?'show':''">
									<view class="cu-dialog">
										<view class="text-blue" style="padding: 15upx 0upx;" @click="oneclickSignin">
											一键签到
										</view> 
										<view class="text-blue" style="padding: 15upx 0upx;" @click="timeSignin">
											限时签到							
										</view>
										<view class="text-blue" @tap="hideModal" style="padding: 15upx 0upx;">
											取消
										</view>
									</view>
								</view>
							</view>
							<!-- 教师结束签到 -->
							<view v-else>
								<view class="item">
									<view class="img_view" :style="{background: 'linear-gradient(0deg,rgba(205,92,92),rgba(255,0,0))'}">
										<image :src="menus[0].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="endSignin"></image>
									</view>
									<text class="txt">结束签到</text>
								</view>
							</view>
						</view>
					</template>
					<!-- 签到记录 -->
					<template>
						<view class="item" >
							<view class="img_view" :style="{background:'linear-gradient(0deg,rgba(65,105,225),rgba(100,149,237))'}">
								<image :src="menus[0].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="signDetail"></image>
							</view>
							<text class="txt">签到记录</text>
						</view>
					</template>
					<!-- 分享班课 -->
					<template>
						<view class="item" >
							<view class="img_view" :style="{background: menus[1].bg}">
								<image :src="menus[1].icon" class="image" style="height: 80rpx; width: 80rpx;"@click="shareClass"></image>
							</view>
							<text class="txt">{{menus[1].txt}}</text>
						</view>
					</template>
					<!-- 教师结束班课、学生退出班课 -->
					<template>
						<view v-if="classType=='0'" class="item" >
							<view class="img_view" :style="{background: menus[2].bg}">
								<image :src="menus[2].icon" class="image" style="height: 80rpx; width: 80rpx;"></image>
							</view>
							<text class="txt">{{menus[2].txt}}</text>
						</view>
						<view v-else class="item">
							<view class="img_view" :style="{background: menus[2].bg}">
								<image :src="menus[2].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="overClass"></image>
							</view>
							<text class="txt">结束班课</text>
						</view>
					</template>
				</view>
			</view>
			<!-- 教师端设置按钮 -->
			<view v-if="classType=='1'" class="cu-form-group margin-top">
				<view class="title">是否允许加入</view>
				<switch @change="SwitchA" :class="switchA?'checked':''" :checked="switchA?true:false"></switch>
			</view>
			<!-- 班课成员信息 -->
			<view style="display: flex;justify-content: space-between;font-size: 115%;margin-top: 20rpx;" >
					<view style="color: black;margin-left: 30rpx; font-size: 35rpx;">成员</view>
					<view style="color: gray;margin-right: 20rpx; ">{{Students.length}} 人</view>
			</view>	
			<view class="cu-list menu-avatar" style="margin-top: 15rpx;" >			
				<view class="cu-item" v-for="(item,index) in Students" :key="index">
					<view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10001.jpg)"></view>
					<view class="content">
						<view class="text-black">{{item.userName}}</view>
						<view class="text-gray text-sm flex">
							<view class="text-cut" style="font-size: 30rpx;color: black;">
								{{item.userPhone}}
							</view> 
						</view>
		<!-- 				<view class="text-cut" style="font-size: 30rpx;color: black;">
							{{exps[index]}}
						</view> -->
					</view>
				</view>	
			</view>						
		</view>
	</view>
</template>

<script>
	import {formateDate} from "@/common/util.js" 
	export default {
		data() {
			return {
				isClose:'',//班课是否关闭
				user:null,//当前用户信息
				class:'',//课程信息			
				classType:'',//管理班课|查看班课
				className:'',//班课名
				classCode:'',//班课号
				modalName: null,//模态框
				longitude:'',//地理位置经度（教师发起签到）
				latitude:'',//地理位置维度（教师发起签到）
				startTime:'',//签到开始时间
				menus: [{
						bg: 'linear-gradient(0deg,rgba(9,216,162,1),rgba(90,242,217,1))',
						icon: '/static/signin.png',
						txt: '参与签到'
					},
					{
						bg: 'linear-gradient(0deg,rgb(238, 130, 238),rgb(238, 130, 238))',
						icon: '/static/Share.png',
						txt: '分享班课'
					},
					{
						bg: 'linear-gradient(0deg,rgba(255,126,34,1),rgba(240,184,27,1))',
						icon: '/static/team.png',
						txt: '小组方案'
					}
				],
				switchA: true,
				Students:[],//该班课的学生
				//exps:[0]
			}
		},
		onLoad() {
			var that=this;	
			uni.getLocation({
				type: 'wgs84',
				success: function (res) {
					console.log('当前位置的经度：' + res.longitude);
					console.log('当前位置的纬度：' + res.latitude);
					that.longitude=res.longitude,
					that.latitude=res.latitude
				}
			});	
		},
		async onShow() {
			var that=this;
			var classIsallowed='1';
			that.isClose=uni.getStorageSync('classIsclose')
			that.user=uni.getStorageSync('data')
			that.classType=uni.getStorageSync('classType')
			that.className=uni.getStorageSync('className')
			that.classCode=uni.getStorageSync('classCode')
			classIsallowed=uni.getStorageSync('classIsallowed')
			that.switchA=true?classIsallowed=='1':false
			//查看当时是否有签到
			await uni.request({
				url:'http://112.74.55.61:8081/checkinfo',
				header: {Authorization:uni.getStorageSync('token')},
				method:'GET',
				data:{
					classcode:that.classCode,
				}, 
				success: (res) => {
					console.log(res.data.data)
					if(res.data.data!=''){
						that.startTime=res.data.data.startTime
					}			
				},
				fail: (res) => {
					console.log(res)
				}
			})
			//获取班级成员
			await uni.request({
				url:'http://112.74.55.61:8081/studentsfromclass',
				header: {Authorization:uni.getStorageSync('token')},
				method:'GET',
				data:{
					classCode:that.classCode
				}, 
				success: (res) => {
					console.log(res.data)
					that.Students=res.data.data
					//查看班课成员经验值
					// for(var i=0;i<that.Students.length;i++){
					// 	console.log(that.classCode) 
					// 	console.log(that.Students[i].userPhone)
					// 	uni.request({
					// 		url:'http://112.74.55.61:8081/stuexp',
					// 		header: {Authorization:uni.getStorageSync('token')},
					// 		method:'POST',
					// 		data:{
					// 			classcode:that.classCode,
					// 			studentphone:that.Students[i].userPhone
					// 		}, 
					// 		success: (res) => {
					// 			console.log(res.data)
					// 			that.exps[i]=res.data.data
					// 			console.log(that.exps[i])
					// 		},
					// 		fail: (res) => {
					// 			console.log(res)
					// 		}
					// 	})
					// }
					
				},
				fail: (res) => {
					console.log(res)
				}
			})
		},
		onHide() {
			var that=this
			that.modalName = null
		},
		methods: {
			//签到记录
			signDetail(){
				var that=this
				if(that.user.roleId=='2'){
					uni.request({
						url:'http://112.74.55.61:8081/tcheckninrecords',
						header: {Authorization:uni.getStorageSync('token')},
						method:'POST',
						data:{
							classcode:that.classCode
						}, 
						success: (res) => {
							console.log(res.data.data)	
							uni.setStorageSync('signinList',res.data.data)
							uni.navigateTo({
								url:"../signin/signinList"
							})
						},
						fail: (res) => {
							console.log(res)
						}
					})
				}else{
					uni.navigateTo({
						url:'../signin/signinDetail'
					})
				}	
			},
			//模态框对应操作 显示/隐藏
			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(e) {
				this.modalName = null
			},
			//教师发起一键签到
			oneclickSignin(){
				var that=this;	
				console.log(that.longitude)
				uni.request({
					url:'http://112.74.55.61:8081/checkinteachers',
					header: {Authorization:uni.getStorageSync('token')},
					method:'POST',
					data:{
						classcode:that.classCode,
						teacherphone:that.user.userPhone,
						location_x:that.longitude,
						location_y:that.latitude
					}, 
					success: (res) => {
						console.log(res.data)
						console.log(res.data.data)	
						if(res.data.msg=="发起签到失败，已存在"){
							uni.showToast({ title: '请先结束签到再发起新的签到', icon: 'none' });
						}else if(res.data.msg=="发起签到成功"){
							uni.request({
								url:'http://112.74.55.61:8081/checkinfo',
								header: {Authorization:uni.getStorageSync('token')},
								method:'GET',
								data:{
									classcode:that.classCode,
								}, 
								success: (res) => {
									console.log(res.data.data)
									that.startTime=res.data.data.startTime		
								},
								fail: (res) => {
									console.log(res)
								}
							})
							uni.showToast({ title: '发起签到成功，无时间限制', icon: 'none' });
						}
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})
			},
			//教师发起限时签到
			timeSignin(){
				var that=this;		
				uni.request({
					url:'http://112.74.55.61:8081/checkinteachers',
					header: {Authorization:uni.getStorageSync('token')},
					method:'POST',
					data:{
						classcode:that.classCode,
						teacherphone:that.user.userPhone,
						location_x:that.longitude,
						location_y:that.latitude,
						checkintype:"limited"
					}, 
					success: (res) => {
						console.log(res.data)
						console.log(res.data.data)	
						if(res.data.msg=="发起签到失败，已存在"){
							uni.showToast({ title: '请先结束签到再发起新的签到', icon: 'none' });
						}else if(res.data.msg=="发起签到成功"){
							uni.request({
								url:'http://112.74.55.61:8081/checkinfo',
								header: {Authorization:uni.getStorageSync('token')},
								method:'GET',
								data:{
									classcode:that.classCode,
								}, 
								success: (res) => {
									console.log(res.data.data)
									that.startTime=res.data.data.startTime		
								},
								fail: (res) => {
									console.log(res)
								}
							})
							uni.showToast({ title: '发起签到成功，期限为5分钟', icon: 'none' });
						}
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})
			},
			//学生参与签到
			signin(){
				var that=this
				if(that.startTime==''){
					uni.showToast({ title: '老师还没有开始签到或签到已结束', icon: 'none' });
				}else{
					uni.navigateTo({
						url:"../signin/signin"
					})
				}
			},
			//结束签到
			endSignin(){
				var that=this;
				uni.request({
					url:'http://112.74.55.61:8081/checkinover',
					header: {Authorization:uni.getStorageSync('token')},
					method:'PUT',
					data:{
						classcode:that.classCode,
						teacherphone:that.user.userPhone,
						checkindate:that.startTime
					}, 
					success: (res) => {
						console.log(res.data)
						console.log(res.data.data)
						that.startTime=''
						that.modalName=null
						uni.showToast({ title: '成功结束签到', icon: 'none' });
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})
			},
			shareClass(){
				uni.navigateTo({
					url:"QRCode"
				})
			},
			//将班课设置为已结束
			overClass(){
				var that=this;
				uni.showModal({
						content: '是否结束该班课,该操作不可撤回',
						success: function (res) {
							if (res.confirm) {
								uni.request({
									url:'http://112.74.55.61:8081/classclousres',
									header: {Authorization:uni.getStorageSync('token')},
									method:'PUT',
									data:{
										classcode:that.classCode,
										isclose:'1'	
									}, 
									success: (res) => {
										console.log(res.data)
										uni.switchTab({
											url:"../home/home"
										})
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
			},
			//设置班课是否允许加入
			SwitchA(e) {
				var that=this
				that.switchA = e.detail.value
				var type='0'
				type=that.switchA==true?'1':'0'
				console.log(type)
				uni.request({
					url:'http://112.74.55.61:8081/classallowed',
					header: {Authorization:uni.getStorageSync('token')},
					method:'PUT',
					data:{
						classcode:that.classCode,
						isallowed:type	
					}, 
					success: (res) => {
						console.log(res.data)				
					},
					fail: (res) => {
						console.log(res)
					}
				})
			},
		}
	}
</script>

<style>
	page {
		width: 100%;
		background-color: #ebebeb;
	}
</style>
<style lang="scss" scoped>
	@function realSize($args) {
		@return $args / 1.5;
	}
	
	.page_edu {
		width: 100%;
	}
	.closeImg{
		width: 100%;
		height: 100%;
	}
	.page_edu_header {
		padding-top: var(--status-bar-height);
		background-color: #0bc99d;
		width: 100%;
		height: realSize(315px);
	
		.header {
			display: flex;
			flex-direction: row;
			align-items: center;
			padding: realSize(20px);
	
			.btn {
				width: realSize(36px);
				height: realSize(30px);
			}
	
			.input {
				height: realSize(59px);
				width: 100%;
				margin-left: realSize(20px);
				margin-right: realSize(20px);
				background: rgba(255, 255, 255, 1);
				border-radius: realSize(30px);
				display: flex;
				flex-direction: row;
				align-items: center;
	
				.search {
					width: realSize(30px);
					height: realSize(30px);
					margin-left: realSize(20px);
					margin-right: realSize(20px);
				}
			}
		}
	
		.header_content {
			display: flex;
			flex-direction: row;
	
			.left {
				display: flex;
				flex-direction: column;
				width: 57%;
				margin-top: 10px;
				margin-left: 15px;
				margin-right: 15px;
	
				.title {
					width: realSize(419px);
					height: realSize(59px);
					font-size: realSize(47px);
					font-weight: bold;
					color: rgba(255, 255, 255, 1);
				}
	
				.sub_title {
					margin-top: 3px;
					font-size: realSize(21px);
					font-weight: 400;
					color: rgba(255, 255, 255, 1);
					background: linear-gradient(0deg, rgba(120, 255, 224, 1) 0%, rgba(255, 255, 255, 1) 100%);
					-webkit-background-clip: text;
					-webkit-text-fill-color: transparent;
				}
	
				.btn {
					margin-top: 3px;
					width: realSize(198px);
					height: realSize(60px);
					background: linear-gradient(-30deg, rgba(252, 135, 29, 1), rgba(246, 185, 9, 1));
					box-shadow: 0px 4px 10px 0px rgba(255, 121, 0, 0.5);
					border-radius: realSize(30px);
					color: rgba(255, 255, 255, 1);
					display: flex;
					align-items: center;
					justify-content: center;
				}
			}
		}
	}
	.page_content {
		width: 100%;
		margin-top: -74px;
	
		.menu {
			margin-left: 10px;
			margin-right: 10px;
			padding-left: 10px;
			padding-right: 10px;
			height: realSize(176px);
			background: rgba(255, 255, 255, 1);
			box-shadow: 0px 10px 10px 0px rgba(0, 161, 124, 0.1);
			border-radius: 10px;
			display: flex;
			flex-direction: row;
			align-items: stretch;
			justify-content: space-between;
	
			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
	
				.img_view {
					width: 60px;
					height: 60px;
					border-radius: 30px;
					display: flex;
					align-items: center;
					justify-content: center;
	
					.image {
						width: 50px;
						height: 50px;
					}
				}
	
				.txt {
					margin-top: 5px;
					font-size: 14px;
					color: rgba(51, 51, 51, 1);
				}
			}
		}
	
		
	}
</style>
