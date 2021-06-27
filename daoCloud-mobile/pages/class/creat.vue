<template>
	<view>
		<!--	班课封面  	-->
		<view class="cu-form-group" style="display: flex;justify-content: center;height: 180rpx;">				
			<!--	封面为空、添加封面  	-->
			<view @tap="ChooseImage" v-if="imgList.length<1">
				<image src="../../static/c.png" style="height: 100rpx;width: 110rpx;"></image>
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
				<view class="title">班级名称</view>
				<input v-model="courseName"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">班课名称</view>
				<picker @change="Picker_className" :value="index_className" :range="picker_className">
					<view class="picker">
						{{index_className>-1?picker_className[index_className]:'去选择'}}
					</view>
				</picker>
			</view>
			<view class="cu-form-group">
				<view class="title">班课学生人数</view>
				<input v-model="classmember" type="number"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">学校院系</view>
				<view v-if="!cur" @click="chooseSchool">去选择 ></view>
				<view v-else>{{schoolCollege}}</view>
			</view>
			<view class="cu-form-group">
				<view class="title">课程学期</view>
				<picker @change="PickerChange" :value="index" :range="picker2">
					<view class="picker">
						{{index>-1?picker2[index]:'去选择'}}
					</view>
				</picker>
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
		
		<!--	创建班课完成   	-->
		<button class="button bg-blue"  @click="createClass()">创建班课</button>
	</view>
</template>

<script>
	import request from '../../common/request.js'
	export default {
		data() {
			return {
				imgList: [],//班课封面				
				className:null,//班课名称
				picker_className:['工程实践','工程训练','专业英语','智能技术','软件体系结构'],//选择班课名称
				index_className:-1,//默认下标为-1,
				courseName:null,//班级名称
				classmember:null,//班课学生人数
				semester:null,//班课学期
				schoolCollege:null,
				cur:false,
				date: new Date().toISOString().slice(0, 10),
				picker2:['2020-2021-1','2020-2021-2','2021-2022-1','2021-2022-2'],//选择班课学期
				index:1,//默认下标为-1,
				classCode:null,//班课号
				user:null,//用户信息
				userPhone:null //创建者手机号
			}
		},
		onShow() {
			var that=this;
			that.schoolCollege=uni.getStorageSync('school')+uni.getStorageSync('college')
			that.cur=uni.getStorageSync('cur')
			console.log(this.schoolCollege)
			console.log(this.cur)			
		},
		onLoad() {
			var that=this;
			uni.removeStorageSync('school')
			uni.removeStorageSync('college')
			uni.removeStorageSync('cur')			
			that.user=uni.getStorageSync('data')
			that.userPhone=that.user.userPhone
			if(that.date>'2021-07-31'){
				that.index=2
			}	
			that.semester=that.picker2[that.index]
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
			chooseSchool(){
				uni.navigateTo({
					url:'school'
				})
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
				that.semester=that.picker2[that.index]
			},
			//选择课程名
			Picker_className(e){
				var that=this;
				that.index_className = e.detail.value,
				that.className=that.picker_className[that.index_className]
			},
			//创建班课
			createClass(){
				var that=this
				if (!/^[0-9]*$/.test(that.classmember)) {
					uni.showToast({ title: '班课必须是数字形式', icon: 'none' });				
				}else if(that.className==null||that.courseName==null){
					uni.showToast({ title: '班级名或班课名不能为空', icon: 'none' });	
				}else{
					uni.request({
						url:'http://112.74.55.61:8081/classes',
						header: {Authorization:uni.getStorageSync('token')},
						method:'POST',
						data:{
							classname:that.className,
							userphone:that.userPhone,
							classmembers:that.classmember,
							classsemester:that.semester
						},
						success: (res) => {
							console.log(res.data)
							that.classCode=res.data.data
							uni.setStorageSync('classCode',that.classCode)
							uni.navigateTo({
								url:"QRCode"
							})
						},
						fail: (res) => {
							console.log(res)
							console.log("连接失败")
						}
					})	
				}		
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
