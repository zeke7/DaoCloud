<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import "colorui/main.css";
	@import "colorui/icon.css";
	.input-box {
		padding: 50upx;
		font-size: 30upx;
		.input-item { 
			display: flex;
			background: white;
			border-bottom: 1upx solid #eeeeee;
			line-height: 100upx;
			height: 100upx;
			.input-label {
				width: 150upx;
			}
			.input-body{
				position: relative;
				height: 100upx;
				width: calc(100% - 150upx);
				.input {
					line-height: 100upx;
					height: 100upx;
					position: relative;
					font-size: 28upx;
				}
				.eye{
					position: absolute;
					height: 50upx;
					width: 50upx;
					right: 0;
					top: 50%;
					transform: translateY(-50%);
				}
				.btn-code{
				    position: absolute;
				    right: 0rpx;
				    top:50%;
				    transform: translateY(-50%);
					background: #5677fc;
					color: #fff;
					font-size: 24rpx;
					box-sizing: border-box;
					border-radius: 50rpx;
					height: 50rpx;
					line-height: 50rpx;
					padding: 0 25rpx;
				}
			}
			
		}
		.select {
			padding-top: 40upx;
			display: flex;
			justify-content: space-between;
			color: #003B67;
		}
	}
.img-b {
	position: absolute;
	width: 50%;
	bottom: 0;
	left: -50rpx;
	margin-bottom: -200rpx;
}
</style>
