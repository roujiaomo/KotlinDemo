package com.example.kotlindemo.base

class BaseResponse<T> {
    private var status = 0
    private var msg: String? = null
    private var content: T? = null

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }

    fun getMsg(): String? {
        return msg
    }

    fun setMsg(msg: String?) {
        this.msg = msg
    }

    fun getContent(): T? {
        return content
    }

    fun setContent(content: T?) {
        this.content = content
    }
}