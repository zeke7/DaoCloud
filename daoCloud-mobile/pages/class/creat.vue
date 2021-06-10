<template>
	<view>
		<!--	班课封面  	-->
		<view class="cu-form-group" style="display: flex;justify-content: center;height: 180rpx;">				
			<!--	封面为空、添加封面  	-->
			<view @tap="ChooseImage" v-if="imgList.length<1">
				<image src="../../static/照相机.png" style="height: 100rpx;width: 110rpx;"></image>
				<view>添加封面</view>
			</view>
			<!--	存在封面  	-->
			<view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage" :data-url="imgList[index]">
				<image :src="imgList[index]" mode="aspectFill" style="height: 100rpx;width: 110rpx;"></image>
				<view @tap.stop="DelImg" :data-index="index" style="width: 5rox;height: 5rpx;position: relative;">
					<text class='cuIcon-close' style="position: absolute;padding-left: 38rpx;"></text>
				</view>
			</view>		
		</view>
		<!--	班课信息   	-->
		<view>
			<view style="color: gray; margin: 10upx 30upx;">班课信息</view>
			<view class="cu-form-group" style="margin-top: 10upx;">
				<view class="title">班课名称</view>
				<input v-model="className"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">教师姓名</view>
				<input v-model="teacherName"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">班课学生人数</view>
				<input v-model="classmember"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">课程学期</view>
				<picker @change="PickerChange" :value="index" :range="picker">
					<view class="picker">
						{{index>-1?picker[index]:'去选择'}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">班课编号</view>
				<input v-model="classcode"></input>
			</view>
		</view>
		<!--	创建人信息  	-->
		<view>
			<view style="color: gray; margin: 10upx 30upx;">创建者信息</view>
			<view class="cu-form-group" style="margin-top: 10upx;">
				<view class="title">创建者手机号</view>
				<view style="color: #999999;">{{userPhone}}</view>
			</view>
		</view>
		
		<!--	类型   	-->
		<!-- <view>
			<view style="color: gray; margin: 20upx 30upx;">类别</view>
			<view class="cu-form-group" style="margin-top: 10upx;">
				<view class="title">学校课表班课</view>
				<switch @change="SwitchA" :class="switchA?'checked':''" :checked="switchA?true:false"></switch>
			</view>
			<view style="color: gray; margin: 10upx 10upx;">学校课表班课是学校安排课程表里的正式课程</view>
			<view class="cu-form-group" style="margin-top: 10upx;">
				<view class="title">云教材</view>
				<picker @change="PickerChange" :value="index" :range="picker">
					<view class="picker">
						{{index>-1?picker[index]:'去选择'}}
					</view>
				</picker>
			</view>
		</view> -->
		<!--	展开设置学校、院系详情   	-->
		<!-- <view>
			<view style="color: gray; margin: 20upx 10upx;">展开设置学校、院系详情</view>
			<view>
				<text style="font-size: 30upx;margin: 20upx 10upx;">设置班课详情</text>
				<text class="cuIcon-refresharrow" ></text>
			</view>			
		</view> -->
		
		<!--	创建班课完成   	-->
		<button class="button bg-blue"  @click="suc_cre()">创建班课</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imgList: [],//班课封面				
				className:null,//班课名称
				teacherName:null,
				classmember:null,//班课学生人数
				semester:null,//班课学期
				picker:['2020-1','2020-2','2021-1','2021-2'],//选择班课学期
				index:-1,//默认下标为-1,
				classcode:null,//班课编号
				userPhone:13055766787 //创建者手机号
			}
		},
		methods: {
			//选择照片
			ChooseImage() {
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album'], //从相册选择
					success: (res) => {
						if (this.imgList.length != 0) {
							this.imgList = this.imgList.concat(res.tempFilePaths)
						} else {
							this.imgList = res.tempFilePaths
						}
					}
				});
			},
			//放大查看照片
			ViewImage(e) {
				uni.previewImage({
					urls: this.imgList,
					current: e.currentTarget.dataset.url
				});
			},
			//删除照片
			DelImg(e) {
				uni.showModal({
					title: '',
					content: '确定要删除这张封面吗？',
					cancelText: '取消',
					confirmText: '确定',
					success: res => {
						if (res.confirm) {
							this.imgList.splice(e.currentTarget.dataset.index, 1)
						}
					}
				})
			},
			//判断类别
			SwitchA(e) {
				this.switchA = e.detail.value
			},
			//选择课程学期
			PickerChange(e) {
				var that=this;
				that.index = e.detail.value,
				that.semester=that.picker[that.index]
			},
			suc_cre(){
				var that=this
				uni.request({
					url:'http://112.74.55.61:8081/classes',
					method:'POST',
					data:{
						classname:that.className,
						userPhone:that.userPhone,
						classmember:that.classmember,
						classcode:that.classcode,
						semester:that.semester					
					},
					success: (res) => {
						console.log(res.data)
					},
					fail: (res) => {
						console.log(res)
						console.log("连接失败")
					}
				})				
				// uni.navigateTo({
				// 	url:'suc-creat'
				// })		
			},
			
		}
	}
</script>

<style>
.button {
	font-size: 28rpx;		
	color: #fff;
	height: 90rpx;
	line-height: 90rpx;
	margin: 80rpx 20rpx 0 20rpx;
	border-radius: 50rpx;
}
</style>
