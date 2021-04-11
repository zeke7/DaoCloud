// module.exports = {
// 	lintOnSave: false,
// 	devServer: {
// 		// port: process.env.NODE_ENV == "production" ? 8000 : 9000,
// 		proxy: {
// 			'/api': {
// 				target: 'http://112.74.55.61:8081/',
// 				// 允许跨域
// 				changeOrigin: true,
// 				ws: true,
// 				pathRewrite: {
// 					'^/api': ''
// 				}
// 			}
// 		}
// 	}
// }
// module.exports = {
// 	devServer: { // webpack-dev-server 相关配置
// 		overlay: {
// 			warning: false,
// 			err: false
// 		},
// 		hot: true,
// 		hotOnly: true, // 是否热更新
// 		host: '0.0.0.0',
// 		port: '8081',
// 		open: true,
// 		disableHostCheck: true,
// 		proxyTable: {
// 			'/pro-api': {
// 				target: 'http://112.74.55.61:8081',
// 				changeOrigin: true,
// 				pathRewrite: {
// 					'/pro-api': ''
// 				}
// 			}
// 		},
// 	},
//
// }
// module.exports = {
// 	lintOnSave: false,
// 	devServer: {
// 		port: process.env.NODE_ENV == "production" ? 8000 : 9000,
// 		proxy: {
// 			'/api': {
// 				target: process.env.VUE_APP_URL,
// 				// 允许跨域
// 				changeOrigin: true,
// 				ws: true,
// 				pathRewrite: {
// 					'^/api': ''
// 				}
// 			}
// 		}
// 	}
// }
module.exports = {
    css: {
        loaderOptions: {
            sass: {
                prependData: `
                 @import "@/assets/scss/_variable.scss";
            `
            }
        }
    }
}
