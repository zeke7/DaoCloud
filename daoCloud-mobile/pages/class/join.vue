<template>
	<view>
		<view class="cu-form-group" style="margin-top: 375rpx;">
			<input placeholder="请输入班课号" name="input" v-model="classNo"></input>
			<text class="cuIcon-camera" @click="scan"></text>
		</view>
		<view style="color: gray; margin: 10upx 10upx;">可扫描二维码自动填写班课号</view>
		<button class="button bg-blue"  @click="go_home()">加入班课</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				classNo:"",
				Class:{name:'智能技术',teacher:'陈志华'},
				joinClass:[]
			}
		},
		onLoad() {
			var that=this
			uni.getStorage({
				key:'join_class',
				success: function (res) {
					console.log(res.data);
					that.joinClass=res.data
				}
			})
		},
		methods: {
			scan() { //扫码验证
				var _this = this;
				uni.scanCode({
			　　　　　onlyFromCamera: false, //为true只允许相机扫码，不加允许相册扫码
					success: function(res) {
						uni.showToast({
							title: '扫码成功'
						})
					},
					fail: function(err) {
						console.log('扫码失败', err)
					}
				})
			},
			go_home(){
				var that=this;
				//that.joinClass.pop(),
				that.joinClass.push(that.Class),
				uni.setStorage({
					key:'join_class',
					data:that.joinClass,
					success: function () {
						console.log("success");
					}
				}),
				uni.switchTab({
					url:'../home/home'
				})
			}
			
		}
	}
</script>

<style>
	
</style>

