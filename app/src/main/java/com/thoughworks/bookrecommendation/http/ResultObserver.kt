package com.thoughworks.bookrecommendation.http

import android.accounts.NetworkErrorException
import com.thoughworks.bookrecommendation.model.entity.BaseResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class ResultObserver<T> : Observer<BaseResponse<T>> {
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        if (!d.isDisposed) {
            onRequestStart()
        }
    }

    override fun onNext(reposnse: BaseResponse<T>) {
        onRequestEnd()
        if (reposnse.isSuccess()) {
            try {
                onSuccess(reposnse.result)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            try {
                onBusinessFail(reposnse.resultCode, reposnse.reason)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        try {
            if (e is ConnectException
                || e is TimeoutException
                || e is NetworkErrorException
                || e is UnknownHostException
            ) {
                onFailure(e, true)
            } else {
                onFailure(e, false)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
    }

    /**
     * 请求开始
     */
    open fun onRequestStart() {

    }

    /**
     * 请求结束
     */
    open fun onRequestEnd() {

    }

    /**
     * 返回成功
     *
     * @param result
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onSuccess(result: T?)

    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    @Throws(Exception::class)
    abstract fun onFailure(e: Throwable, isNetWorkError: Boolean)

    /**
     * 业务错误
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    @Throws(Exception::class)
    open fun onBusinessFail(code: Int, message: String) {
    }

}