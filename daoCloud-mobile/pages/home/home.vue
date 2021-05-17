<template>
	<view>
		<!--	操作条    	-->
		<scroll-view scroll-x class="bg-white nav">
			<view class="flex text-center">
				<view class="cu-item flex-sub" :class="index==TabCur?'text-blue cur':''" v-for="(item,index) in tabList" :key="index" @tap="tabSelect" :data-id="index">
					{{tabList[index]}}
				</view>
				<!--	模态框    	-->
				<image src="../../static/Add.png" style="width: 75upx;height: 75upx;padding:15upx 15upx 0 0;margin-left: 30upx;" @tap="showModal" data-target="bottomModal"></image>
				<view class="cu-modal bottom-modal" :class="modalName=='bottomModal'?'show':''">
					<view class="cu-dialog">
						<view class="text-blue" style="padding: 15upx 0upx;" @click="go_addclass()">
							创建班课
						</view> 
						<view class="text-blue" style="padding: 15upx 0upx;">
							<navigator url="../class/join">
									使用班课号加入班课	
							</navigator>							
						</view>
						<view class="text-blue" @tap="hideModal" style="padding: 15upx 0upx;">
							取消
						</view>
					</view>
				</view>
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
		<!--	我创建的班课列表    	-->
		<view v-if="TabCur==0" class="cu-list menu-avatar"  >
			<view v-if="bulidClass.length==0">
				<image src="../../static/8.png" style="width: 700rpx; height: 450rpx; margin-left: 25rpx;"></image>
				<view style="text-align: center;">当前没有创建班课</view>
			</view>
			<view v-else>
				<view class="cu-item" style="height: 180upx;" >
					<!-- <view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png);"></view> -->
					<image class="cu-avatar radius lg" src="../../static/yunbanke.png" style="width: 110upx;height: 110upx;"></image>
					<view class="content" >
						<view class="text-black text-bold">2</view>
						<view class=" text-sm flex">
							<view >池芝标</view> 
							<view class="text-gray" style="margin-left: 20upx;">2020-1</view>
						</view> 
					</view>
					<view class="action">
						<text class="cuIcon-right text-gray" ></text>
					</view>
				</view>
			</view>
		</view>
		<!--	我加入的班课列表    	-->
		<view v-else class="cu-list menu-avatar"  >
			<view class="cu-item" style="height: 180upx;" v-for="item in joinClass" >
				<image class="cu-avatar radius lg" src="../../static/yunbanke.png" style="width: 110upx;height: 110upx;"></image>
				<view class="content" >
					<view class="text-black text-bold">{{item.name}}</view>
					<view class=" text-sm flex">
						<view >{{item.teacher}}</view> 
						<view class="text-gray" style="margin-left: 20upx;">2020-1</view>
					</view>
					<view class="choose" >
						<view><text class="cuIcon-focus" @click="go_signin"></text>签到</view>
						<view><text class="cuIcon-flashbuyfill"></text>举手</view>
						<view><text class="cuIcon-mark"></text>抢答</view>
					</view>
				</view>
				<view class="action">
					<text class="cuIcon-right text-gray" ></text>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				TabCur: 1,//我创建的、我加入的
				scrollLeft: 0,
				tabList:["我创建的","我加入的"],
				modalName: null,//模态框
				bulidClass:[],//创建的班课
				joinClass:[{name:'工程训练',teacher:'池芝标'},{name:'工程英语',teacher:'陈勃'}],//加入的班课
			}
		},
		methods: {
			//切换操作条  我创建的/我加入的
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			//模态框对应操作 显示/隐藏
			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(e) {
				this.modalName = null
			},
			//创建班课
			go_addclass(){
				uni.navigateTo({
					url:"../class/creat"
				})
			},
			//加入班课
			go_signin(){
				uni.navigateTo({
					url:"../signin/signin"
				})
			},
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
