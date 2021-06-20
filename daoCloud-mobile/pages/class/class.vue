<template>
	<view class="page_edu">
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
				<template >
					<view v-if="classType=='0'" class="item" >
						<view class="img_view" :style="{background: menus[0].bg}">
							<image :src="menus[0].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="signin"></image>
						</view>
						<text class="txt">{{menus[0].txt}}</text>
					</view>
					<view v-else class="item" >
						<view class="img_view" :style="{background: menus[0].bg}">
							<image :src="menus[0].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="initiateSignin"></image>
						</view>
						<text class="txt">发起签到</text>
					</view>
				</template>
				<template v-if="classType=='1'">
					<view class="item" >
						<view class="img_view" :style="{background: menus[0].bg}">
							<image :src="menus[0].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="endSignin"></image>
						</view>
						<text class="txt">结束签到</text>
					</view>
				</template>
				<template>
					<view class="item" >
						<view class="img_view" :style="{background: menus[1].bg}">
							<image :src="menus[1].icon" class="image" style="height: 80rpx; width: 80rpx;"@click="signindetail"></image>
						</view>
						<text class="txt">{{menus[1].txt}}</text>
					</view>
				</template>
				<template>
					<view v-if="classType=='0'" class="item" >
						<view class="img_view" :style="{background: menus[2].bg}">
							<image :src="menus[2].icon" class="image" style="height: 80rpx; width: 80rpx;"></image>
						</view>
						<text class="txt">{{menus[2].txt}}</text>
					</view>
					<view v-else class="item">
						<view class="img_view" :style="{background: menus[2].bg}">
							<image :src="menus[2].icon" class="image" style="height: 80rpx; width: 80rpx;" @click="test"></image>
						</view>
						<text class="txt">结束班课</text>
					</view>
				</template>
			</view>
		</view>
		
		<view v-if="classType=='1'" class="cu-form-group margin-top">
			<view class="title">是否允许加入</view>
			<switch @change="SwitchA" :class="switchA?'checked':''" :checked="switchA?true:false"></switch>
		</view>
		
		<view style="display: flex;justify-content: space-between;font-size: 115%;margin-top: 20rpx;" >
				<view style="color: black;margin-left: 30rpx; font-size: 35rpx;">成员</view>
				<view style="color: gray;margin-right: 20rpx; ">93 人</view>
		</view>	
			 
		<view class="cu-list menu-avatar" style="margin-top: 15rpx;" >			
			<view class="cu-item" v-for="item in Students">
				<view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10001.jpg)"></view>
				<view class="content">
					<view class="text-black">{{item.name}}</view>
					<view class="text-gray text-sm flex">
						<view class="text-cut">
							{{item.no}}
						</view> 
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
				user:null,//当前用户信息
				class:'',//课程信息
				classType:'',//管理班课|查看班课
				className:'',//班课名
				classCode:'',
				longitude:'',//地理位置经度（教师发起签到）
				latitude:'',//地理位置维度（教师发起签到）
				startTime:'',//开始时间
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
				Students:[{name:'张三',no:'2000'},{name:'李四',no:'2001'}],//加入的班课
			}
		},
		onLoad() {
			var that=this;
			that.user=uni.getStorageSync('data')
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
		onShow() {
			var that=this;
			var allClass=[];
			var classIndex=0;
			that.classType=uni.getStorageSync('classType')
			classIndex=uni.getStorageSync('classIndex')
			if(that.classType=='0'){
				allClass=uni.getStorageSync('join_class')
			}else if(that.classType=='1'){
				allClass=uni.getStorageSync('bulid_class')
			}
			that.class=allClass[classIndex]
			that.className=that.class.className
			that.classCode=that.class.classCode
			that.switchA=true?that.class.classIsallowed=='1':false
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
					console.log("连接失败")
				}
			})
		},
		methods: {
			signdetail(){
				uni.navigateTo({
					url:'../signin/signin-detail'
				})
			},
			//教师发起签到
			initiateSignin(){
				var that=this;			
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
							uni.showToast({ title: '发起签到成功，期限为5分钟', icon: 'none' });
						}
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})
				
				// var time=formateDate(new Date(), 'Y-M-D h:min:s')
				// uni.setStorageSync('signinTime',time)
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
						uni.showToast({ title: '成功结束签到', icon: 'none' });
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})
			},
			test(){
				var that=this
				uni.getLocation({
					type: 'wgs84',
					success: function (res) {
						console.log('当前位置的经度：' + res.longitude);
						console.log('当前位置的纬度：' + res.latitude);
						that.longitude=res.longitude,
						that.latitude=res.latitude
					},
					
				});	
			},
			SwitchA(e) {
				var that=this
				that.switchA = e.detail.value
				console.log(e.detail.value)
				var type='0'
				type='1'?that.switchA==true:'0'
				uni.request({
					url:'http://112.74.55.61:8081/classallowed',
					header: {Authorization:uni.getStorageSync('token')},
					method:'GET',
					data:{
						classCode:that.classCode,
						isclose:type	
					}, 
					success: (res) => {
						console.log(res.data)
						console.log(res.data.data)				
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
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
