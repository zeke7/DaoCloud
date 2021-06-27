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
									<!-- 学生页面：某个学生的签到情况 -->
									<view v-if="classType=='0'" class="records" v-for="(item,index) in mainArray" :key="index">							
										<view>				
											<view class="date">{{dates[index]}}</view>
											<view class="time" style="font-size: 30rpx;">{{weeks[index]}}</view>								
										</view>
										<view class="status" v-if="item.isCheckin=='1'" style="color: #0081FF;">已签到</view>
										<view class="status" v-else style="color: #DD514C;">未签到</view>
									</view>
									<!-- 教师页面：某个签到下所有学生的签到情况 -->
									<view v-if="classType=='1'" class="records" v-for="(item,index) in studentList" :key="index">
										<view>	
											<view class="date">{{item.studentName}}</view>
											<view class="time" style="font-size: 30rpx;">{{item.studentPhone}}</view>
										</view>
										<view class="status" v-if="item.isCheckin=='1'"style="color: #0081FF;">已签到</view>
										<view class="status" v-else style="color: #DD514C;" @click="makeUp(index)">未签到(点击补签)</view>
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
				classType:'',
				//学生：
				mainArray:[],//签到信息
				classCode:'',//班课号
				dates:[],//签到时间
				weeks:[],//星期	
				//教师：
				signinList:[],
				signinIndex:0,
				studentList:[],//学生签到情况
				chechinDate:''
			}
		},
		onShow() {
			var that=this;
			that.user=uni.getStorageSync('data')
			that.classCode=uni.getStorageSync('classCode')
			that.classType=uni.getStorageSync('classType')	
			//教师：获取这次签到所有学生签到情况
			if(that.classType=='1'){
				that.signinIndex=uni.getStorageSync('signinIndex')
				that.signinList=uni.getStorageSync('signinList')
				that.studentList=that.signinList[that.signinIndex].studentList
				that.checkinDate=uni.getStorageSync('checkinDate')
			}
			//学生：获取该门课程自身所有签到情况
			else if(that.classType=='0'){
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
			//教师为学生补签
			makeUp(index){
				var that=this;
				uni.showModal({
						title: that.Class,
						content: '是否确定为该学生补签',
						success: function (res) {
							if (res.confirm) {
								uni.request({
									url:'http://112.74.55.61:8081/makeupscheckninrecords ',
									header: {Authorization:uni.getStorageSync('token')},
									method:'POST',
									data:{
										classcode:that.classCode,
										studentphone:that.studentList[index].studentPhone,
										checkindate:that.checkinDate
									}, 
									success: (res) => {
										console.log(res.data)
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
												that.signinList=uni.getStorageSync('signinList')
												that.studentList=that.signinList[that.signinIndex].studentList
											},
											fail: (res) => {
												console.log(res)
											}
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
