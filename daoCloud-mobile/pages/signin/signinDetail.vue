<template>
	<view class="container">
		<!-- 顶部面板 -->
		<view>
			<image src="../../static/signin-detail.png" style="height: 325rpx;"></image>
		</view>
		<!-- 滚动区域 -->
		<view class="scroll-panel" id="scroll-panel">
			<view class="list-box">
				<view class="main">
					<swiper class="swiper" :style="{ 'height':scrollHeight }" 
						   vertical="true" duration="100">
						<swiper-item>
							<scroll-view  scroll-y="true" :style="{ 'height':scrollHeight }">
								<view class="item">
									<view class="title">
										<view @click="test">历史签到记录</view>
									</view>  
									<!-- 教师页面：某个签到下学生的签到情况 -->
									<view v-if="userRole=='3'" class="records" v-for="(item,index) in mainArray" :key="index">							
										<view>				
											<view class="date">{{dates[index]}}</view>
											<view class="time" style="font-size: 30rpx;">{{weeks[index]}}</view>								
										</view>
										<view class="status" v-if="item.isCheckin=='1'" style="color: #0081FF;">已签到</view>
										<view class="status" v-else style="color: #DD514C;">未签到</view>
									</view>
									<!-- 教师页面：某个签到下学生的签到情况 -->
									<view v-if="userRole=='2'" class="records" v-for="(item,index) in studentList" :key="index">
										<view>	
											<view class="date">{{item.studentName}}</view>
											<view class="time" style="font-size: 30rpx;">{{item.studentPhone}}</view>
										</view>
										<view class="status" v-if="item.isCheckin=='1'"style="color: #0081FF;">已签到</view>
										<view class="status" v-else style="color: #DD514C;">未签到(点击补签)</view>
									</view>								
								</view>
							</scroll-view>
						</swiper-item>
					</swiper>
				</view> 
			</view>
		</view>
	</view>
</template>

<script>
	import {resolvingDate,getWeek} from "@/common/util.js" 
	export default {
		data() {
			return {
				scrollHeight:'400px',
				user:null,//当前用户信息
				userRole:'',
				//学生：
				mainArray:[],//签到信息
				classCode:'',//班课号
				dates:[],//签到时间
				weeks:[],//星期	
				//教师：
				studentList:[]//学生签到情况
			}
		},
		onShow() {
			var that=this;
			that.user=uni.getStorageSync('data')
			that.classCode=uni.getStorageSync('classCode')
			that.userRole=that.user.roleId			
			if(that.userRole=='2'){
				var signinIndex=0;
				var signinList=[];
				signinIndex=uni.getStorageSync('signinIndex')
				signinList=uni.getStorageSync('signinList')
				that.studentList=signinList[signinIndex].studentList
			}else if(that.userRole=='3'){
				uni.request({
					url:'http://112.74.55.61:8081/scheckninrecords',
					header: {Authorization:uni.getStorageSync('token')},
					method:'POST',
					data:{
						classcode:that.classCode,
						studentphone:that.user.userPhone,
					}, 
					success: (res) => {
						console.log(res.data.data)	
						that.mainArray=res.data.data;
						for (var i=0;i<that.mainArray.length;i++)
						{ 
						    var date=resolvingDate(that.mainArray[i].checkinDate)
							var week=getWeek(that.mainArray[i].checkinDate)
							that.dates[i]=date
							that.weeks[i]=week				
						}
					},
					fail: (res) => {
						console.log(res)
					}
				})
			}		
		},

		methods: {
			test(){
				
			}
		}
	}
</script>

<style lang="scss">
page,.container{
	height: 100%;
}
/* 容器 */
.container{
	display: flex;
	flex-direction: column;
	flex-wrap: nowrap;
	justify-content: flex-start;
	align-items: flex-start;
	align-content: flex-start;
	
	&>view{
		width: 100%;
	}
	
	.scroll-panel{
		flex-grow: 1;
		height: 0;
		overflow: hidden;
	}
}

.list-box{
	display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: flex-start;
    align-items: flex-start;
    align-content: flex-start;
	font-size: 28rpx;
	
	.left{
		width: 200rpx;
		background-color: #f6f6f6;
		line-height: 80rpx;
		box-sizing: border-box;
		font-size: 32rpx;
		
		.item{
			padding-left: 20rpx;
			position: relative;
			&:not(:first-child) {
				margin-top: 1px;
			
				&::after {
					content: '';
					display: block;
					height: 0;
					border-top: #d6d6d6 solid 1px;
					width: 620upx;
					position: absolute;
					top: -1px;
					right: 0;
					transform:scaleY(0.5);	/* 1px像素 */
				}
			}
			
			&.active,&:active{
				color: #42b983;
				background-color: #fff;
			}
		}
	}
	.main{
		background-color: #fff;
		padding-left: 30rpx;
		width: 0;
		flex-grow: 1;
		box-sizing: border-box;
		
		.swiper{
			height: 500px;
		}

		.title{
			line-height: 64rpx;
			font-size: 35rpx;
			font-weight: bold;
			color: #666;
			background-color: #fff;
			position: sticky;
			top: 0;
			z-index: 999;
		}
		
		.item{
			padding-bottom: 10rpx;
		}
		
		.records{
			display: flex;
			flex-direction: row;
			flex-wrap: nowrap;
			justify-content: space-between;
			align-items: center;
			align-content: center;
			padding-top: 20rpx;
			padding-bottom: 20rpx;
			.date{
				font-size: 35rpx;				
			}		
			.time{
				font-size: 24rpx;
				color: #999999;
			}	
			.status{
				color: #999999;	
				margin-right: 30rpx;
			}
		}
	}
}
</style>
