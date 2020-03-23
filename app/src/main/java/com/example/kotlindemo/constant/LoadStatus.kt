package com.example.kotlindemo.constant

/**
 * 用object 修饰的类为静态类，里面的方法和变量都为静态的(创建的时候直接可选)
 */
object LoadStatus {
    const val STATUS_CONTENT = 0x00
    const val STATUS_LOADING = 0x01 //加载中...
    const val STATUS_UPLOADING = 0x02 //上传中...
    const val STATUS_REQUEST = 0x03 //正在请求中...
    const val STATUS_EMPTY = 0x04
    const val STATUS_ERROR = 0x05 //服务器错误
    const val STATUS_NO_NETWORK = 0x06 //网络错误
}