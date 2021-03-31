export function toast(title){
	uni.showToast({
		icon:'none',
		title:title
	})
}
/**
 * 手机验证
 */
export function checkPhone(value){
	if (!value) {
		uni.showToast({ title: '请输入手机号', icon: 'none' });
		return true;
	}
	if (!/^[1][3,4,5,7,8,9][0-9]{9}$/.test(value)) {
		uni.showToast({ title: '请输入正确手机号', icon: 'none' });
		return true;
	}
	return false
}
/**
 * 密码验证
 */
export function checkPwd(value){
	if(value.length>=6){
		return true;
	}
	toast('密码必须大于6位');
	return false
}
/**
 * 验证码验证
 */
export function checkCode(value){
	if(value.length==6){
		return true;
	}
	toast('验证码必须是6位数字');
	return false
}