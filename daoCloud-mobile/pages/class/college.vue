<template>
	<view style="font-size: 40rpx;text-align: center;">
		<view v-for="(item,index) in college" :key="index"  style="margin-top: 30rpx;padding: 25rpx 0;background-color: white;"@click="backCreat(index)">
			{{college[index]}}
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				college:['数学与计算机科学学院', '土木工程学院', '经济与管理学院', '外国语学院', '化学学院'],
			}
		},

		methods: {
			backCreat(index){
				var that=this
				console.log(index)
				uni.setStorageSync('college',that.school[index])
				uni.navigateTo({
					url:'creat'
				})
			}
		}
	}
</script>

<style>
	
</style>


