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
									<view class="records" v-for="(item,index) in signinList" :key="index">							
										<view>				
											<view class="date">{{dates[index]+'  '+weeks[index]}}</view>
											<view class="time" style="font-size: 30rpx;">
												<view v-if="item.checkinType=='2'">限时签到</view>
												<view v-else>一键签到</view>
											</view>								
										</view>
										<view style="margin-right: 30rpx;" >
											<text class="cuIcon-right text-gray" @click="onSigninDetail(index)" ></text>
										</view>
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
				signinList:[],//签到信息清单
				dates:[],//签到时间
				weeks:[]//星期
			}
		},
		onShow() {
			var that=this;
			that.signinList=uni.getStorageSync('signinList')
			for(var i=0;i<that.signinList.length;i++){
				var date=resolvingDate(that.signinList[i].checkinDate)
				var week=getWeek(that.signinList[i].checkinDate)
				that.dates[i]=date
				that.weeks[i]=week	
			}
		},
		methods: {
			onSigninDetail(index){
				uni.setStorageSync('signinIndex',index)
				uni.navigateTo({
					url:"signinDetail"
				})
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
