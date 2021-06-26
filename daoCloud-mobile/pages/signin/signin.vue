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
				user:null,//当前用户信息
				classCode:'',//班课号
				tcLongitude:'',//教师位置经度
				tcLatitude:'',//教师位置纬度
				startTime:'',//签到开始时间
				longitude:'',//学生位置经度
				latitude:'',//学生位置纬度
				address:'签到以获取地理位置',//地理位置信息
				time: formateDate(new Date(), 'h:min:s'), //当前时分秒
				date: (new Date().getMonth()+1).toString()+'.'+new Date().getDate().toString(),//当前日期
				isclick:false
			}
		},
		onShow() {
			var that=this;
			that.user=uni.getStorageSync('data')
			that.classCode=uni.getStorageSync('classCode')
			uni.getLocation({
				type: 'wgs84',
				success: function (res) {
					console.log('当前位置的经度：' + res.longitude);
					console.log('当前位置的纬度：' + res.latitude);
					that.longitude=res.longitude,
					that.latitude=res.latitude
				}
			});	
			//学生是否已经完成签到
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
					let main=res.data.data
					if(main[main.length-1].isCheckin=='1'){
						that.isclick=true
						that.getAdd()
					}
				},
				fail: (res) => {
					console.log(res)
				}
			})
			// 获取签到发起的信息
			uni.request({
				url:'http://112.74.55.61:8081/checkinfo',
				header: {Authorization:uni.getStorageSync('token')},
				method:'GET',
				data:{
					classcode:that.classCode,
				}, 
				success: (res) => {
					console.log(res.data.data)
					that.tcLongitude=res.data.data.checkinLocx
					that.tcLatitude=res.data.data.checkinLocy
					that.startTime=res.data.data.startTime
					
					//计算两地距离
					let PI = 3.14159265358979323;//圆周率
					let R = 6371229;//地球半径
					
					var lon1 = parseFloat(that.longitude);
					var lat1 = parseFloat(that.latitude);  
					
					var lon2 = parseFloat(that.tcLongitude);
					var lat2 = parseFloat(that.tcLatitude);
					let x,y,distance;
					let lonres = lon1 > lon2?lon1-lon2:lon2-lon1;
					let latres = lat1 > lat2?lat1-lat2:lat2-lat1;
					x = (lonres) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
					y = (lat2 - lat1) * PI * R / 180;
					distance = Math.hypot(x, y);		
					console.log(distance)
					
				},
				fail: (res) => {
					console.log(res)
					console.log("连接失败")
				}
			})
		},
		methods: {
			clickSign(){
				var that=this
				uni.request({
					url:'http://112.74.55.61:8081/checkninstudents',
					header: {Authorization:uni.getStorageSync('token')},
					method:'POST',
					data:{
						classcode:that.classCode,
						studentphone:that.user.userPhone,
						location_x:that.longitude,
						location_y:that.latitude,
						checkindate:that.startTime
					}, 
					success: (res) => {
						console.log(res.data)
						if(res.data.msg==="学生签到超时"){
							uni.showToast({ title: '签到已结束', icon: 'none' });
						}if(res.data.msg==="学生签到失败"){
							uni.showToast({ title: '老师还没有开始签到', icon: 'none' });
						}else if(res.data.msg==="学生签到成功"){
							uni.showToast({ title: '签到成功！', icon: 'none' });
							that.getAdd()
							that.isclick=true
						}
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})
			
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
		},
		getDistance(){
			var that=this;
			let PI = 3.14159265358979323;//圆周率
			let R = 6371229;//地球半径
			
			var lon1 = that.longitude;
			var lat1 = that.latitude;
			
			var lon2 = that.tcLongitude;
			var lat2 = that.tcLatitude;
			let x,y,distance;
			let lonres = lon1 > lon2?lon1-lon2:lon2-lon1;
			let latres = lat1 > lat2?lat1-lat2:lat2-lat1;
			x = (lonres) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
			y = (lat2 - lat1) * PI * R / 180;
			distance = Math.hypot(x, y);		
			console.log(distance)
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
