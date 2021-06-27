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
	if (!value) {
		uni.showToast({ title: '请输入密码', icon: 'none' });
		return true;
	}
	if (!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/.test(value)) {
		uni.showToast({ title: '密码需同时含有数字和字母，且长度要在8-16位之间', icon: 'none' });
		return true;
	}
	return false
}
/**
 * 验证码验证
 */
export function checkCode(value){
	if(value.length==4){
		return false;
	}
	toast('验证码必须是4位数字');
	return true
}
