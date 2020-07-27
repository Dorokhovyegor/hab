package com.dorokhov.hab.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.Response
import com.dorokhov.hab.ui.ResponseType
import com.dorokhov.hab.utils.ErrorCodes.UNKNOWN_ERROR
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

@InternalCoroutinesApi
abstract class DataSourceManager<ViewStateType> {

    private val TAG = "AppDebug"
    protected val result = MediatorLiveData<DataState<ViewStateType>>()
    protected lateinit var job: CompletableJob
    protected lateinit var coroutineScope: CoroutineScope

    init {
        setJob(initNewJob())
        setValue(DataState.loading(true))
        doCacheRequest()
    }

    private fun doCacheRequest() {
        coroutineScope.launch {
            delay(600)
            loadFromCache()
        }
    }

    fun onCompleteJob(dataState: DataState<ViewStateType>) {
        GlobalScope.launch(Main) {
            job.complete()
            setValue(dataState)
        }
    }

    private fun setValue(dataState: DataState<ViewStateType>) {
        result.value = dataState
    }

    fun onErrorReturn(errorMessage: String?, shouldUserDialog: Boolean, shouldUseToast: Boolean) {
        var responseType: ResponseType = ResponseType.None()
        if (shouldUseToast) {
            responseType = ResponseType.Toast()
        }
        if (shouldUserDialog) {
            responseType = ResponseType.Dialog()
        }
        onCompleteJob(
            DataState.error(
                response = Response(
                    codeError = UNKNOWN_ERROR,
                    message = errorMessage,
                    responseType = responseType
                )
            )
        )
    }

    private fun initNewJob(): Job {
        job = Job()
        job.invokeOnCompletion(
            onCancelling = true,
            invokeImmediately = true,
            handler = object : CompletionHandler {
                override fun invoke(cause: Throwable?) {
                    if (job.isCancelled) {
                        cause?.let {
                            onErrorReturn(it.message, false, true)
                        } ?: onErrorReturn("unknown error", false, true)
                    } else if (job.isCompleted) {

                    }
                }
            })
        coroutineScope = CoroutineScope(IO + job)
        return job
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    abstract suspend fun loadFromCache()

    abstract fun setJob(job: Job)
}