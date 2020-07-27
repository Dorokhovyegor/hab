package com.dorokhov.hab.repositories.day

import androidx.lifecycle.LiveData
import com.dorokhov.hab.percistance.CommonDao
import com.dorokhov.hab.repositories.DataSourceManager
import com.dorokhov.hab.repositories.JobManager
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.Response
import com.dorokhov.hab.ui.ResponseType
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import com.dorokhov.hab.utils.ErrorCodes.CANT_UPDATE_TASK_STATUS
import com.dorokhov.hab.utils.ErrorCodes.SUCCESS
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository
@Inject
constructor(
    private val commonDao: CommonDao
) : JobManager("TaskRepository") {

    @InternalCoroutinesApi
    fun updateTaskStatus(taskId: Int, newStatus: Int): LiveData<DataState<ViewProgressViewState>> {
        return object : DataSourceManager<ViewProgressViewState>() {

            override suspend fun loadFromCache() {
                val updateResult = commonDao.updateDayWithId(taskId, newStatus)
                if (updateResult >= 0) {
                    onCompleteJob(
                        DataState.data(
                            data = null,
                            response = Response(
                                SUCCESS, "Статус обновлен",
                                ResponseType.Toast()
                            )
                        )
                    )
                } else {
                    DataState.data(
                        data = null,
                        response = Response(
                            CANT_UPDATE_TASK_STATUS,
                            "Статус обновлен",
                            ResponseType.Toast()
                        )
                    )
                }
            }

            override fun setJob(job: Job) {
                addJob("updateTaskStatus", job)
            }

        }.asLiveData()
    }
}