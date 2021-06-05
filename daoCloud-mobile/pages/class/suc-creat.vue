<template>
	<view class="content">
		<view class="top" style="background-image: url(../../static/banner.png);"></view>
		<view class="banner">
			<dl>
				<dt><image src="../../static/logo.png" ></image></dt>
				<dd>创建成功</dd>
			</dl>

		<!-- 	<view class="qrcode-body" style="margin-top: 50rpx;">
				 <view class="qrcode">
					  <canvas canvas-id="myCanvas"/>
				 </view> 
			</view> -->
			
			<view class="box">
				<image class="se" :src = "img"></image>
			</view>
			
			<view class="sharbuttn">
				<view class="btn" @click="save(img)">保存二维码</view>
				<view class="btn" @click="go_home()">返回首页</view>
			</view>
		</view>
	</view>
</template>

<script>
	import Qrcode from '../../common/qrcode/qrcode.js'
	export default {
		data() {
			return {
				img:"",
				kr:"2333333"
			}
		},

		onShow() {
			console.log('show')
		},
		onLoad() {
			// let qrcode = new Qrcode({
			//         'level': 'Q',
			//         'size': 200,
			// 		'foreground':'#6495ED'
			//       });
			var str1 = this.kr.replace(' ','+')		
			//将创建成功的班课号生成二维码
			this.img='https://tool.oschina.net/action/qrcode/generate?data='+str1+'&output=image%2Fgif&error=L&type=0&margin=0&size=4&1621760013581'		 
		},
		methods: {
			//保存图片到相册
			save(url){
				const that = this;
				uni.downloadFile({
					url,
					success: res => {
						if (res.statusCode === 200) {
							uni.saveImageToPhotosAlbum({
								filePath: res.tempFilePath,
								success: function() {
									this.tools.toast('保存成功');
								},
								fail: function() {
									this.tools.toast('保存失败，请稍后重试');
								}
							});
						} else {
							this.tools.toast('保存失败，请稍后重试');
						}
					}
				});				
			},
			//返回首页
			go_home(){
				uni.switchTab({
					url:'../home/home'
				})
				
			}
		},
	}
</script>

<style>
	 .qrcode-body {
	    width: 100%;
	  }
	 
	  .qrcode-body .qrcode {
	    display: flex;
	    justify-content: center;
	  }
	  canvas{
	    height: 400rpx;
	    width: 400rpx;
	  }
	  .banner{
	  	width: 100% ;
	  	background-color: #FFFFFF;
	  	border-radius: 60upx 60upx 0 0;
	  	margin-top: -60upx;
	  }
	  .banner dl{
	  	margin-top: -80upx;
	  }
	  .banner dl dt{
	  	text-align: center;
	  }
	  .banner dl dt image{
	  	width: 160upx;
	  	height: 160upx;
	  	border-radius:50% ;
	  }
	  .banner dl dd{
	  	text-align: center;
	  }
	  .top{
	  	width: 100%;
	  	height:400upx;
	  	background-size:100% ;
	  	background-position:center center;
	  }
	  .sharbuttn{
	  	display: flex;
	  	justify-content: center;
	  }
	  .btn{
	  	width: 260upx;
	  	height: 60upx;
	  	line-height: 60upx;
	  	margin: 0 auto;
	  	margin-top: 60upx;
	  	border-radius: 40upx;
	  	border: 0;
	  	font-size: 26upx;
	  	outline: 0;
	  	background: #33b17b;
	  	color: #FFFFFF;
	  	text-align: center;
	  }
	  .content{
	  	width: 100%;
	  	background-color: #ffffff;
	  }
	  .box{
	  	width: 100%;
	  	height: 450rpx;  	
	  	text-align: center;  
	  }
	  .se{
	  	margin-top:20rpx;
	  	width:450rpx;
	  	height:100%;
	  }
</style>
