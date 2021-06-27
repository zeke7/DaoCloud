<template>
	<view>
		<view class="header">
			<view class="bg">
				<view class="box">
					<view class="box-hd">
						<view class="avator">
							<img src="../../static/face.png">
						</view>
						<view style="text-align: center; width: 100%;">{{userName}}</view>
					</view>
					<view class="box-bd">
						<view class="item" style="padding-right: 100rpx;">
							<view style="color: #007AFF;">166</view>
							<view class="text">经验值</view>
						</view>
						<view class="item" style="padding-right: 50rpx;">
							<view style="color: #007AFF;">0</view>
							<view class="text">蓝豆</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="list-content">
			<view class="list">
				<view class="li noborder" >
					<view class="icon"><image src="../../static/cc-card1.png"></image></view>
					<view class="text">我的信息</view>
					<!-- <image class="to" src="../../static/user/to.png"></image> -->
				</view>
			</view>
			<view class="list">
				<view class="li " >
					<view class="icon"><image src="../../static/help.png"></image></view>
					<view class="text">帮助中心</view>
					<!-- <image class="to" src="../../static/user/to.png"></image> -->
				</view>
				<view class="li " >
					<view class="icon"><image src="../../static/about.png"></image></view>
					<view class="text">关于我们</view>
					<!-- <image class="to" src="../../static/user/to.png"></image> -->
				</view>
				<view class="li " >
					<view class="icon"><image src="../../static/opinion.png"></image></view>
					<view class="text">意见反馈</view>
					<!-- <image class="to" src="../../static/user/to.png"></image> -->
				</view>
			</view>
			<view class="list">
				<view class="li noborder" >
					<view class="icon"><image src="../../static/set.png"></image></view>
					<view class="text" @click="test">系统设置</view>
					<!-- <image class="to" src="../../static/user/to.png"></image> -->
				</view>
			</view>
		</view>
		<button @click="loginOut" style="color: red;margin-top: 50rpx;">登 出</button>
	</view>
</template>
<script>
	export default {
		data() {
			return {
				user:null,
				userName:'',
				exp:'10'
			};
		},
		async onLoad() {					
			await this.getExp()
		},
		methods: {
			loginOut(){
				uni.showModal({
						content: '是否退出当前账号',
						success: function (res) {
							if (res.confirm) {
								uni.clearStorageSync()
								uni.navigateTo({
									url:'../login/login'
								})
							} else if (res.cancel) {
								console.log('用户点击取消');
							}
						}
				});			
			},
			getExp(){
				this.user=uni.getStorageSync('data')
				this.userName=this.user.userName
				uni.request({
					url:'http://112.74.55.61:8081/allstuexp',
					header: {Authorization:uni.getStorageSync('token')},
					method:'POST',
					data:{
						studentphone:this.user.userPhone
					}, 
					success: (res) => {
						console.log(res.data.data)
						this.exp=res.data.data
						
					},
					fail: (res) => {
						console.log(res)
					}
				})
			}
		}
	}
</script>

<style lang="scss">
page{
	background-color:#f1f1f1;
	font-size: 30upx;
}
.header{
	background: #fff;
	height: 390upx;
	padding-bottom: 110upx;
	.bg{
		width: 100%;
		height:200upx;
		padding-top:100upx;
		background-color:#4191ea;
	}
}
.box{
	width: 650upx;
	height: 280upx;
	border-radius: 20upx;
	margin: 0 auto;
	background: #fff;
	box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, .2); 
	.box-hd{
		display: flex;
		flex-wrap: wrap;
		flex-direction: row;
		justify-content: center;
		.avator{
			width: 160upx;
			height: 160upx;
			background: #fff;
			border: 5upx solid #fff;
			border-radius: 50%;
			margin-top: -80upx;
			overflow: hidden;
			img{
				width: 100%;
				height: 100%;
			}
		}
	}
	.box-bd{
		display: flex;
		flex-wrap: nowrap;
		flex-direction: row;
		justify-content: center;
		.item{
			flex: 1 1 auto;
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;
			border-right: 1px solid #f1f1f1;
			margin: 15upx 0;
			&:last-child{
				border: none;
			}
			.icon{
				width: 60upx;
				height: 60upx;
				img{
					width: 100%;
					height: 100%;
				}
			}
			.text{
				width: 100%;
				text-align: center;
				margin-top: 10upx;
			}
		}
	}
}
.list-content{
	background: #fff;
}
.list{
	width:100%;
	border-bottom:15upx solid  #f1f1f1;
	background: #fff;
	&:last-child{
		border: none;
	}
	.li{
		width:92%;
		height:100upx;
		padding:0 4%;
		border-bottom:1px solid rgb(243,243,243);
		display:flex;
		align-items:center;
	&.noborder{
		border-bottom:0
		}
		.icon{
			flex-shrink:0;
			width:50upx;
			height:50upx;
			image{
				width:50upx;
				height:50upx;
			}
		}
		.text{
			padding-left:20upx;
			width:100%;
			color:#666;
		}
		.to{
			flex-shrink:0;
			width:40upx;
			height:40upx;
		}
	}
}

.button {
	width: 600rpx;
	font-size: 28rpx;
	background: #5677fc;
	color: #fff;
	height: 90rpx;
	line-height: 90rpx;
	margin-top: 160rpx;
	border-radius: 50rpx;
	box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
}
</style>
