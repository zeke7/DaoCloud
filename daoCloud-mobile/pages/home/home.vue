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
						<view class="text-blue" style="padding: 15upx 0upx;" @click="goAddclass()">
							创建班课
						</view> 
						<view class="text-blue" style="padding: 15upx 0upx;">
							<navigator url="../class/join">
									加入班课	
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
		<view v-if="TabCur==0" class="cu-list menu-avatar"> 
			<view v-if="bulidClass.length==0">
				<image src="../../static/NoClass.png" style="height: 350rpx;margin-top: 200rpx;margin-bottom: 50rpx;margin-left: 55rpx;"></image>
				<view style="text-align: center;font-size: 35rpx;">还没有创建的班课</view>
			</view>
			<view v-else class="cu-item" style="height: 180upx;" v-for="item,index in bulidClass" :key=index>
				<image class="cu-avatar radius lg" src="../../static/yunbanke.png" style="width: 110upx;height: 110upx;"></image>
				<view class="content" >
					<view class="text-black text-bold">{{item.className}}</view>
					<view class=" text-sm flex">
						<view style="font-size: 25rpx;">{{item.classCode}}</view> 
						<view class="text-gray" style="margin-left: 30upx;font-size: 25rpx;">{{item.classSemester}}</view>
					</view>
				</view>
				<view class="action" >
					<text class="cuIcon-right text-gray" @click="manage_class(index)" ></text>
				</view>
			</view>
		</view>
		<!--	我加入的班课列表    	-->
		<view v-else class="cu-list menu-avatar"  >
			<view v-if="joinClass.length==0">
				<image src="../../static/NoClass.png" style="height: 350rpx;margin-top: 200rpx;margin-bottom: 50rpx;margin-left: 55rpx;"></image>
				<view style="text-align: center;font-size: 35rpx;">还没有加入的班课</view>
			</view>
			<view v-else class="cu-item" style="height: 180upx;" v-for="item,index in joinClass" :key=index >
				<image class="cu-avatar radius lg" src="../../static/yunbanke.png" style="width: 110upx;height: 110upx;"></image>
				<view class="content" >
					<view class="text-black text-bold">{{item.className}}</view>
					<view class=" text-sm flex">
						<view >{{item.classCode}}</view> 
						<!-- <view class="text-gray" style="margin-left: 20upx;">2021-1</view> -->
					</view>
					<view class="choose" >
						<view><text class="cuIcon-focus" @click="onSignin(index)"></text>签到</view>
						<view><text class="cuIcon-flashbuyfill"></text>举手</view>
						<view><text class="cuIcon-mark"></text>抢答</view>
					</view>
				</view>
				<view class="action" >
					<text class="cuIcon-right text-gray" @click="onDetail(index)" ></text>
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
				tabList:["我创建的","我加入的"],
				modalName: null,//模态框
				bulidClass:[],//创建的班课（教师）
				joinClass:[],//加入的班课
				user:null
			}
		},
		onShow() {
			var that=this;	
			that.user=uni.getStorageSync('data')
			//获取加入的班课
			uni.request({
				url:'http://112.74.55.61:8081/classinfodto',
				header: {Authorization:uni.getStorageSync('token')},
				method:'GET',
				data:{
					userphone:that.user.userPhone			
				},
				success: (res) => {
					console.log(res.data.data)
					that.joinClass=res.data.data.classinfos
				},
				fail: (res) => {
					console.log(res)
				}
			})
			//如果是教师额外获取创建的班课
			if(that.user.roleId=='2'){
				uni.request({
					url:'http://112.74.55.61:8081/classesdto',
					header: {Authorization:uni.getStorageSync('token')},
					method:'GET',
					data:{
						userphone:that.user.userPhone			
					}, 
					success: (res) => {
						console.log(res.data.data)
						that.bulidClass=res.data.data.classes
					},
					fail: (res) => {
						console.log(res)
					}
				})
			}else{
				that.bulidClass=[]
			}
		},
		onHide() {
			var that=this
			that.modalName = null
		},
		methods: {
			//切换操作条  我创建的/我加入的
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
			},
			//模态框对应操作 显示/隐藏
			showModal(e) {
				this.modalName = e.currentTarget.dataset.target
			},
			hideModal(e) {
				this.modalName = null
			},
			//班课详情
			onDetail(index){
				var that=this;
				uni.setStorageSync('classType','0')
				uni.setStorageSync('classCode',that.joinClass[index].classCode)
				uni.setStorageSync('className',that.joinClass[index].className)
				uni.setStorageSync('classIsclose',that.joinClass[index].classIsclose)
				uni.navigateTo({
					url:'../class/index'
				})
			},
			//管理班课
			manage_class(index){
				var that=this;
				uni.setStorageSync('classType','1')
				uni.setStorageSync('classCode',that.bulidClass[index].classCode)
				uni.setStorageSync('className',that.bulidClass[index].className)
				uni.setStorageSync('classIsclose',that.bulidClass[index].classIsclose)
				uni.setStorageSync('classIsallowed',that.bulidClass[index].classIsallowed)
				uni.navigateTo({
					url:'../class/index'
				})
			},
			//创建班课
			goAddclass(){
				var that=this
				if(that.user.roleId=='3'){
					uni.showToast({ title: '未拥有创建班课的权限', icon: 'none' });
				}else{
					uni.navigateTo({
						url:"../class/creat"
					})
				}		
			},
			//去签到
			onSignin(index){
				var that=this;
				uni.setStorageSync('classType','0')
				uni.setStorageSync('classCode',that.joinClass[index].classCode)
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
