<template>
	<view>
		<view style="font-size: 30rpx;text-align: center;">
			<view v-if="!cur">
				<view v-if="!cur" v-for="(item,index) in school" :key="index" style="margin-top: 30rpx;padding: 25rpx 0;background-color: white;"@click="chooseSchool(index)">
					{{school[index]}}
				</view>
			</view>
			<view v-else>
				<view v-for="(item,index) in college" :key="index" style="margin-top: 30rpx;padding: 25rpx 0;background-color: white;"@click="chooseCollege(index)">
					{{college[index]}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				school:['福州大学', '福建师范大学','福建医科大学'],
				college:['数学与计算机科学学院', '土木工程学院', '经济与管理学院', '外国语学院', '化学学院'],
				color:['red', 'orange', 'yellow', 'green', 'blue', 'indigo', 'violet'],
				cur:false
			}
		},

		methods: {
			chooseSchool(index){
				var that=this
				console.log(index)
				uni.setStorageSync('school',that.school[index])
				that.cur=true
			},
			chooseCollege(index){
				var that=this
				console.log(index)
				uni.setStorageSync('college',that.college[index])
				uni.setStorageSync('cur',true)
				uni.navigateBack({
					
				})
			}
		}
	}
</script>

<style>
	
</style>


