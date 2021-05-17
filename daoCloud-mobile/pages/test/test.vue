<template>
	<view>
		<view class="cu-form-group">
			<view class="title">角色</view>
			<picker @change="PickerChange" :value="index" :range="picker">
				<view class="picker">
					{{index>-1?picker[index]:'请选择'}}
				</view>
			</picker>
		</view>
		<view>{{index}}</view>
		<view>{{userole}}</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				index: -1,
				picker: ['学生', '教师'],//角色选择
				userole:'',//用户角色
			}
		},
		methods: {
			PickerChange(e) {
				this.index = e.detail.value
				this.userole=this.index==0?'student':'teacher'
			},
		}
	}
</script>

<style>

</style>
