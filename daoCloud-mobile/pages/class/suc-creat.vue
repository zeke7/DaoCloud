<template>
	<view class="content">
		<view class="top" style="background-image: url(../../static/banner.png);"></view>
		<view class="banner">
			<dl>
				<dt><image src="../../static/logo.png" ></image></dt>
				<dd>创建成功</dd>
			</dl>

			<view class="qrcode-body" style="margin-top: 50rpx;">
				 <view class="qrcode">
					  <canvas canvas-id="myCanvas"/>
				 </view> 
			</view>
			
			<view class="sharbuttn">
				<view class="btn" @click="save">保存二维码</view>
				<navigator class="btn" url="../home/home">返回首页</navigator>
			</view>
		</view>
	</view>
</template>

<script>
	import Qrcode from '../../common/qrcode/qrcode.js'
	export default {
		data() {
			return {

			}
		},

		onShow() {
			console.log('show')
		},
		onLoad() {
			let qrcode = new Qrcode({
			        'level': 'Q',
			        'size': 200,
					'foreground':'#6495ED'
			      });
			      qrcode.draw('myCanvas', 'https://riyugo.com/i/2021/04/20/z0lark.jpg');
		},
		methods: {
			//保存图片到相册
			save(){
				uni.showActionSheet({
					itemList:['保存图片到相册'],
					success: () => {
						plus.gallery.save('http://pds.jyt123.com/wxtest/logo.png', function() {
							uni.showToast({
								title:'保存成功',
								icon:'none'
							})
						}, function() {
							uni.showToast({
								title:'保存失败，请重试！',
								icon:'none'
							})
						});
					}
				})
			},
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
</style>
