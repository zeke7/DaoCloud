<template>
	<view class="page_edu">
		<view class="page_edu_header">
			<view class="header_content">
				<view class="left">
					<text class="title">工程实践</text>
					<text class="sub_title">任课教师：池zb</text>
				</view>
				<view>
					<image src="/static/right.png" style="width: 131px;height: 122px;"></image>
				</view>
			</view>
		</view>
		
		<view class="page_content">
			<view class="menu">
				<template v-for="(it,i) in menus">
					<view class="item" :key="'menu_'+i">
						<view class="img_view" :style="{background: it.bg}">
							<image :src="it.icon" class="image"></image>
						</view>
						<text class="txt">{{it.txt}}</text>
					</view>
				</template>
			</view>
		</view>
		
		<view style="display: flex;justify-content: space-between;font-size: 115%;margin-top: 30rpx;" >
				<view style="color: black;margin-left: 30rpx; ">成员</view>
				<view style="color: gray;margin-right: 20rpx; ">93 人</view>
		</view>	
			
		<view class="cu-list menu-avatar" style="margin-top: 15rpx;" >			
			<view class="cu-item" v-for="item in Students">
				<view class="cu-avatar radius lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10001.jpg)"></view>
				<view class="content">
					<view class="text-black">{{item.name}}</view>
					<view class="text-gray text-sm flex">
						<view class="text-cut">
							{{item.no}}
						</view> 
					</view>
				</view>
			</view>	
		</view>
					
	</view>
</template>

<script>
	export default {
		data() {
			return {
				menus: [{
						bg: 'linear-gradient(0deg,rgba(9,216,162,1),rgba(90,242,217,1))',
						icon: '/static/signin.png',
						txt: '参与签到',
						isFree: true
					},
					{
						bg: 'linear-gradient(0deg,rgb(238, 130, 238),rgb(238, 130, 238))',
						icon: '/static/gift.png',
						txt: '心意卡片',
						isFree: false
					},
					{
						bg: 'linear-gradient(0deg,rgba(255,126,34,1),rgba(240,184,27,1))',
						icon: '/static/team.png',
						txt: '小组方案',
						isFree: true
					}
				],
				
				Students:[{name:'张三',no:'2000'},{name:'李四',no:'2001'}],//加入的班课
			}
		},
		methods: {
		
		}
	}
</script>

<style>
	page {
		width: 100%;
		background-color: #ebebeb;
	}
</style>
<style lang="scss" scoped>
	@function realSize($args) {
		@return $args / 1.5;
	}
	
	.page_edu {
		width: 100%;
	}
	.page_edu_header {
		padding-top: var(--status-bar-height);
		background-color: #0bc99d;
		width: 100%;
		height: realSize(315px);
	
		.header {
			display: flex;
			flex-direction: row;
			align-items: center;
			padding: realSize(20px);
	
			.btn {
				width: realSize(36px);
				height: realSize(30px);
			}
	
			.input {
				height: realSize(59px);
				width: 100%;
				margin-left: realSize(20px);
				margin-right: realSize(20px);
				background: rgba(255, 255, 255, 1);
				border-radius: realSize(30px);
				display: flex;
				flex-direction: row;
				align-items: center;
	
				.search {
					width: realSize(30px);
					height: realSize(30px);
					margin-left: realSize(20px);
					margin-right: realSize(20px);
				}
			}
		}
	
		.header_content {
			display: flex;
			flex-direction: row;
	
			.left {
				display: flex;
				flex-direction: column;
				width: 57%;
				margin-top: 10px;
				margin-left: 15px;
				margin-right: 15px;
	
				.title {
					width: realSize(419px);
					height: realSize(59px);
					font-size: realSize(47px);
					font-weight: bold;
					color: rgba(255, 255, 255, 1);
				}
	
				.sub_title {
					margin-top: 3px;
					font-size: realSize(21px);
					font-weight: 400;
					color: rgba(255, 255, 255, 1);
	
					background: linear-gradient(0deg, rgba(120, 255, 224, 1) 0%, rgba(255, 255, 255, 1) 100%);
					-webkit-background-clip: text;
					-webkit-text-fill-color: transparent;
				}
	
				.btn {
					margin-top: 3px;
					width: realSize(198px);
					height: realSize(60px);
					background: linear-gradient(-30deg, rgba(252, 135, 29, 1), rgba(246, 185, 9, 1));
					box-shadow: 0px 4px 10px 0px rgba(255, 121, 0, 0.5);
					border-radius: realSize(30px);
					color: rgba(255, 255, 255, 1);
					display: flex;
					align-items: center;
					justify-content: center;
				}
			}
		}
	}
	.page_content {
		width: 100%;
		margin-top: -74px;
	
		.menu {
			margin-left: 10px;
			margin-right: 10px;
			padding-left: 10px;
			padding-right: 10px;
			height: realSize(176px);
			background: rgba(255, 255, 255, 1);
			box-shadow: 0px 10px 10px 0px rgba(0, 161, 124, 0.1);
			border-radius: 10px;
			display: flex;
			flex-direction: row;
			align-items: stretch;
			justify-content: space-between;
	
			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
	
				.img_view {
					width: 60px;
					height: 60px;
					border-radius: 30px;
					display: flex;
					align-items: center;
					justify-content: center;
	
					.image {
						width: 50px;
						height: 50px;
					}
				}
	
				.txt {
					margin-top: 5px;
					font-size: 14px;
					color: rgba(51, 51, 51, 1);
				}
			}
		}
	
		.s_menu {
			display: flex;
			flex-direction: row;
			align-items: stretch;
			justify-content: space-between;
			margin-top: 15px;
			margin-left: 10px;
			margin-right: 10px;
			padding-left: 10px;
			padding-right: 10px;
	
			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
	
				.image {
					width: 35px;
					height: 35px;
				}
	
				.txt {
					margin-top: 5px;
					font-size: 14px;
					color: rgba(51, 51, 51, 1);
				}
			}
		}
	
		.ad {
			width: 100%;
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: center;
	
			.bg {
				position: absolute;
				width: 120px;
				height: 105px;
				left: 0;
			}
	
			.ad_btn {
				width: 100%;
				height: 63px;
				margin: 30px;
				background: linear-gradient(0deg, rgba(253, 155, 28, 1), rgba(251, 197, 33, 1));
				border-radius: 67px;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
	
				.title {
					font-size: realSize(38px);
					font-family: PingFang-SC-Heavy;
					font-weight: 800;
					color: rgba(255, 255, 255, 1);
				}
	
				.sub_title {
					background: linear-gradient(0deg, rgba(255, 128, 37, 1), rgba(255, 153, 32, 1));
					box-shadow: 0px 4px 5px 0px rgba(92, 53, 48, 0.3), 0px 1px 0px 0px rgba(228, 228, 228, 1);
					border-radius: realSize(24px);
					font-size: realSize(24px);
					font-family: PingFang-SC-Heavy;
					font-weight: 800;
					font-style: italic;
					color: rgba(255, 236, 177, 1);
					line-height: realSize(26px);
				}
			}
		}
	}
</style>
