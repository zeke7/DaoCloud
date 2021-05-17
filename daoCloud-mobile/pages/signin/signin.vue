<template>
	<view>
		<view style="font-size: 40rpx;padding-left: 80rpx;padding-top: 30rpx;">
			{{date}}
		</view>
		<!-- 签到按钮 -->
		<view style="margin-top: 100rpx;">
			<view v-if="!isclick" class="module CBlue" @click="clickSign">
				<view class="text">签到</view>
				<view>{{time}}</view>
			</view>
			<view v-else class="module CGray">
				<view style="font-size: 20px;padding-top: 80rpx;">已签到</view>
			</view>
		</view>
		<!-- 地理位置 -->
		<view style="margin: 200rpx 50rpx 0 50rpx;border-radius: 20px; background-color:#F5F5F5;height: 50rpx;">
			<view >
				{{address}}
			</view>
		</view>
	</view>
</template>

<script>
	import {formateDate} from "@/common/util.js" 
	export default {
		data() {
			return {
				longitude:'',//地理位置经度
				latitude:'',//地理位置维度
				address:'签到以获取地理位置',//地理位置信息
				time: formateDate(new Date(), 'h:min:s'), //当前时分秒
				date: (new Date().getMonth()+1).toString()+'.'+new Date().getDate().toString(),//当前日期
				isclick:false
			}
		},
		methods: {
			clickSign(){
				var that=this
				uni.getLocation({
					type: 'wgs84',
					success: function (res) {
						console.log('当前位置的经度：' + res.longitude);
						console.log('当前位置的纬度：' + res.latitude);
						that.longitude=res.longitude,
						that.latitude=res.latitude,
						that.getAdd()
					}
				});	
				that.isclick=true
			},
			// 腾讯位置服务
			getAdd() {
				var that =this
				var url =
					`https://apis.map.qq.com/ws/geocoder/v1/?location=${that.latitude},${that.longitude}&key=3DABZ-JP5EF-I6EJE-JALWN-IOBP6-A5FDF`;
			
				uni.request({
					url,
					success(res) {
						var data = res.data;
						if (data.status != 0) {
							uni.showToast({
								title: data.message,
								icon: "none"
							})
							return;
						}
			
						that.address = res.data.result.address + res.data.result.formatted_addresses.recommend				
					}
				})
			},
		}, 
		onLoad() {
			var that=this
			var timer=setInterval(()=>{
				that.time=formateDate(new Date(), 'h:min:s')
			},1000)
		}
	}
</script>

<style>
	.CBlue {
		background-color: #007aff;
		box-shadow: 0 5px 5px #007aff;
	}
	.CGray {
		background-color: #DCDCDC;
		box-shadow: 0 5px 5px #DCDCDC;
	}
	.module {
		overflow: hidden;
		margin: 20upx auto;
		width: 220upx;
		height: 220upx;
		border-radius: 50%;
		color: #fff;
		text-align: center;
	}
	.module .text {
		font-size: 20px;
		margin: 50upx auto 10upx;
	}
	
</style>
