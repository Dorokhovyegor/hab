package com.dorokhov.hab.repositories.cycle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dorokhov.hab.percistance.CommonDao
import com.dorokhov.hab.percistance.entities.Cycle
import com.dorokhov.hab.repositories.DataSourceManager
import com.dorokhov.hab.repositories.JobManager
import com.dorokhov.hab.ui.Data
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.Response
import com.dorokhov.hab.ui.ResponseType
import com.dorokhov.hab.ui.fragments.datastate.createcycle.CreateCycleViewState
import com.dorokhov.hab.ui.fragments.datastate.viewcycleinfo.ViewCycleViewState
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.CommonProgressFields
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ListOfTask
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressStateEvent
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import com.dorokhov.hab.utils.getEndOfCycle
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import javax.inject.Inject

class CycleRepository
@Inject
constructor(
    private val commonDao: CommonDao
) : JobManager("CycleRepository") {

    @InternalCoroutinesApi
    fun createNewCycle(
        name: String,
        startDate: String
    ): LiveData<DataState<CreateCycleViewState>> {
        return object : DataSourceManager<CreateCycleViewState>() {
            override suspend fun loadFromCache() {
                val result = commonDao.insertNewCycle(
                    Cycle(null, name, startDate, startDate.getEndOfCycle())
                )
                if (result.toInt() >= 0) {
                    onCompleteJob(
                        DataState.data(
                            response = Response(
                                "Цикл успешно создан",
                                ResponseType.Toast()
                            )
                        )
                    )
                } else {
                    onCompleteJob(
                        DataState.error(
                            Response(
                                "Can't insert new cycle into db",
                                ResponseType.Dialog()
                            )
                        )
                    )
                }
            }

            override fun setJob(job: Job) {
                addJob("createNewCycle", job)
            }
        }.asLiveData()
    }

    @InternalCoroutinesApi
    fun getCommonInfoCycle(): LiveData<DataState<ViewProgressViewState>> {
        return object : DataSourceManager<ViewProgressViewState>() {
            override suspend fun loadFromCache() {
                val cycle = commonDao.getCycle()
                val habitList = commonDao.getHabitsInCurrentCycle()
                if (cycle != null) {
                    onCompleteJob(
                        DataState.data(
                            ViewProgressViewState(
                                CommonProgressFields(
                                    cycle.name,
                                    "8",
                                    habitList.size.toString()
                                )
                            )
                        )
                    )
                }
            }

            override fun setJob(job: Job) {
                addJob("getCommonInfoCycle", job)
            }
        }.asLiveData()
    }

    @InternalCoroutinesApi
    fun getTaskForCurrentDate(date: String): LiveData<DataState<ViewProgressViewState>> {
        return object : DataSourceManager<ViewProgressViewState>() {
            override suspend fun loadFromCache() {
                val taskList = commonDao.getDaysForCurrentCycleInCurrentDate(date)
                onCompleteJob(
                    DataState.data(ViewProgressViewState(
                        listOfTask = ListOfTask(
                            taskList
                        )
                    ))
                )
            }

            override fun setJob(job: Job) {
                addJob("getTaskForCurrentDate", job)
            }
        }.asLiveData()
    }

}