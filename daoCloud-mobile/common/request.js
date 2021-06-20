const BASE_URL = 'http://112.74.55.61:8081'//接口地址
export const http = (options) => {
	return new Promise((resolve,reject) => {
		uni.request({
			url: BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			header: {Authorization:uni.getStorageSync('token')},
			success: (res) => {
					if (res == '') {
					return uni.showToast({
						icon: 'loading',
						title:'获取数据失败'
					})
				}
				resolve(res)
			},
			fail: (err) => {
				return uni.showToast({
					icon: 'loading',
					title:'请求失败'
				})
				reject(err)
			} 
		})
	})
}