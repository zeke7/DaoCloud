<template>
	<view class="img">
		<image style="width: 750rpx;height: 1202rpx; z-index: -1;" src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fhbimg.huabanimg.com%2Fe6a8af63cacdb15e09efb6e373e2065ca91c1aa49744-14qOXb_fw658&refer=http%3A%2F%2Fhbimg.huabanimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627374537&t=437b8bbd25273ea803c1751fd57bec05"></image>		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				title: 'Hello'
			}
		},
		onShow() {
		 // 从本地缓存中同步获取指定 key 对应的内容，用于判断是否是第一次打开应用
		 var timer=setTimeout(function(){
		 	const value = uni.getStorageSync('token');
		 	if (value) {
		 		// 如已经有，直接去home首页
		 		uni.switchTab({
		 		  url: '/pages/home/home'
		 		});
		 	} else {
		 		// 没有值，跳到登录页
		 		uni.navigateTo({
		 			url:"../login/login"
		 		})
		 	}
		 },500)
		},
		methods: {

		}
}
</script>

<style scoped>
	.img {
		width: 100%;
		height: 100%;
	}
</style>
