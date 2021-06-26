function addInformation(information) {
	if(information=="该用户已在该班课中"){
		uni.showToast({
		title: '请勿重复加入班课',
		icon:'none',
		duration: 2000
		});
	}else if(information=="班课不允许加入"){
		uni.showToast({
		title: '班课不允许加入',
		icon:'none',
		duration: 2000
		});
	}else if(information=="班课加入失败，参数没有通过有效性验证。"){
		uni.showToast({
		title: '班课不存在',
		icon:'none',
		duration: 2000
		});
	}else{															
		uni.showToast({
		title: '加入成功',
		icon:'none',
		duration: 2000
		});
		uni.switchTab({
			url:'../home/home'
		})	
	}
}
module.exports = {
	addInformation
}