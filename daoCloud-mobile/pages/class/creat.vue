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
				<view class="title">班级</view>
				<input ></input>
			</view>
			<view class="cu-form-group">
				<view class="title">课程</view>
				<input v-model="className"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">学期</view>
				<view class="text-gray">2020-1</view>
			</view>
		</view>
		<!--	类型   	-->
		<view>
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
		</view>
		<!--	展开设置学校、院系详情   	-->
		<view>
			<view style="color: gray; margin: 20upx 10upx;">展开设置学校、院系详情</view>
			<view>
				<text style="font-size: 30upx;margin: 20upx 10upx;">设置班课详情</text>
				<text class="cuIcon-refresharrow" ></text>
			</view>			
		</view>
		<!--	创建班课完成   	-->
		<button class="button bg-blue"  @click="go_home()">创建班课</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				imgList: [],//班课封面
				switchA: false,//是否为学校课表班课
				index: -1,//云教材初始index
				picker: ['C++', 'java', 'python'],//云教材选择
				className:null,
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
			//选择云教材
			PickerChange(e) {
				this.index = e.detail.value
			},
			go_home(){
				uni.switchTab({
					url:'../home/home',
				})				
			},
			handleQuery(){
				this.$router.push({
					path:'../home/home',
					query:{
						className:11,
					}
				});
			}
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
