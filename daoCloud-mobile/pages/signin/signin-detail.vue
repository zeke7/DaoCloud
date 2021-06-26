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
									<view class="records" v-for="(item,index) in mainArray" :key="index">							
										<view>				
											<view class="date">{{item.checkinDate}}</view>
											<view class="time" style="font-size: 30rpx;">{{weeks[index]}}</view>								
										</view>
										<view class="status" v-if="item.isCheckin=='1'">已签到</view>
										<view class="status" v-else>未签到</view>
									</view>
								</view>
							</scroll-view>
						</swiper-item>
					</swiper>
				</view> 
			</view>
		</view>
	<!-- 	底部面板
		<view class="bottom-panel">
			<view style="background-color: #ffaa00;text-align: center;font-size: 28rpx;padding: 10px 0;color: #fff;">
				
			</view>
		</view> -->
	</view>
</template>

<script>
	import {resolvingDate,getWeek} from "@/common/util.js" 
	export default {
		data() {
			return {
				scrollHeight:'400px',
				mainArray:[],//当前用户签到信息
				user:null,//当前用户信息
				classCode:'',//班课号
				class:'',//课程信息
				classType:'',//管理班课|查看班课
				className:'',//班课名
				weeks:[]
			}
		},
		onShow() {
			var that=this;
			var allClass=[];
			var classIndex=0;
			that.user=uni.getStorageSync('data')
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
			
		},
		mounted(){
			this.$nextTick(()=>{
				setTimeout(()=>{
					this.initScrollView().then(()=>{
						this.getListData();
					})
				},200);
			})
		},
		methods: {
			/* 初始化滚动区域 */
			initScrollView(){
				return new Promise((resolve, reject)=>{
					let view = uni.createSelectorQuery().select('#scroll-panel');
					view.boundingClientRect(res => {
						this.scrollHeight = `${res.height}px`;
						this.$nextTick(()=>{
							resolve();
						})
					}).exec();
				});
			},
			/* 获取列表数据 */
			getListData(){
				var that=this
				// Promise 为 ES6 新增的API ，有疑问的请自行学习该方法的使用。
				new Promise((resolve,reject)=>{
					uni.showLoading(); 
					setTimeout(()=>{
							 						 	 
						// 将请求接口返回的数据传递给 Promise 对象的 then 函数。
						uni.request({
							url:'http://112.74.55.61:8081/scheckninrecords',
							header: {Authorization:uni.getStorageSync('token')},
							method:'POST',
							data:{
								classcode:that.classCode,
								studentphone:that.user.userPhone,
							}, 
							success: (res) => {
								console.log(res.data)
								console.log(res.data.data)	
								//let main=[{name:'2021-04-27 星期二',no:'12:00'},{name:'2021-04-27 星期二',no:'11:21'}];
								let main=res.data.data
								resolve({main});
							},
							fail: (res) => {
								console.log(res)
								console.log("连接失败")
							}
						})					
					},500); 
				}).then((res)=>{
					console.log('-----------请求接口返回数据示例-------------');
					console.log(res);	
					uni.hideLoading();
					this.mainArray=res.main;
					for (var i=0;i<this.mainArray.length;i++)
					{ 
					    var time=resolvingDate(this.mainArray[i].checkinDate)
						var week=getWeek(this.mainArray[i].checkinDate)
						this.mainArray[i].checkinDate=time
						this.weeks[i]=week				
					}
				});
			},
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
