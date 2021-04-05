<template>
	<view>
		<!--	操作条    	-->
		<scroll-view scroll-x class="bg-white nav">
			<view class="flex text-center">
				<view class="cu-item flex-sub" :class="index==TabCur?'text-blue cur':''" v-for="(item,index) in tabList" :key="index" @tap="tabSelect" :data-id="index">
					{{tabList[index]}}
				</view>
				<image src="../../static/Add.png" style="width: 75upx;height: 75upx;padding:15upx 15upx 0 0;margin-left: 30upx;"></image>
			</view>
		</scroll-view>
		<!--	搜索栏    	-->
		<view class="cu-bar search bg-white" style="margin-top: 2upx;">
		<view class="search-form round">
			<text class="cuIcon-search"></text>
			<input type="text" placeholder="搜索班课" ></input>
		</view>
		<view class="action">
			<text style="color: rgb(30,144,255); font-size: 115%;" >取消</text>
		</view>
		</view>
		<!--	我加入的班课列表    	-->
		<view class="cu-list menu-avatar"  >
			<view class="cu-item" style="height: 180upx;" >
				<!-- <view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png);"></view> -->
				<image class="cu-avatar radius lg" src="../../static/yunbanke.png" style="width: 110upx;height: 110upx;"></image>
				<view class="content" >
					<view class="text-black text-bold">工程实践</view>
					<view class=" text-sm flex">
						<view >池芝标</view> 
						<view class="text-gray" style="margin-left: 20upx;">2020-1</view>
					</view>
					<view class="choose" >
						<view><text class="cuIcon-focus"></text>签到</view>
						<view><text class="cuIcon-flashbuyfill"></text>举手</view>
						<view><text class="cuIcon-mark"></text>抢答</view>
					</view>
				</view>
				<view class="action">
					<text class="cuIcon-right text-gray" ></text>
				</view>
			</view>
		</view>
		<!--    最新待完成任务     -->
		<view class="bg-white" style="padding-left: 25upx;">
		<view class="text-black text-bold" style="padding: 25upx 0 25upx 0;">最新待完成任务</view>
		<view style="padding: 0 0 15upx 0;">第二周项目任务</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				TabCur: 0,
				scrollLeft: 0,
				tabList:["我创建的","我加入的"]
			}
		},
		
		methods: {
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			}
		}
	}
</script>

<style>
.choose{
	display: flex;
	align-items: center;
	justify-content: space-between;
}
</style>
