package com.dorokhov.hab.repositories.cycle

import androidx.lifecycle.LiveData
import com.dorokhov.hab.percistance.CommonDao
import com.dorokhov.hab.percistance.entities.Cycle
import com.dorokhov.hab.repositories.DataSourceManager
import com.dorokhov.hab.repositories.JobManager
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.Response
import com.dorokhov.hab.ui.ResponseType
import com.dorokhov.hab.ui.fragments.datastate.createcycle.CreateCycleViewState
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.CommonProgressFields
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import com.dorokhov.hab.utils.ErrorCodes.CANT_INSERT_CYCLE_TO_DB
import com.dorokhov.hab.utils.ErrorCodes.SUCCESS
import com.dorokhov.hab.utils.ErrorCodes.THERE_IS_NOT_CYCLE
import com.dorokhov.hab.utils.Logger
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
                    Cycle(0, name, startDate, startDate.getEndOfCycle())
                )
                if (result.toInt() >= 0) {
                    onCompleteJob(
                        DataState.data(
                            response = Response(
                                SUCCESS,
                                "Цикл успешно создан",
                                ResponseType.Toast()
                            )
                        )
                    )
                } else {
                    onCompleteJob(
                        DataState.error(
                            Response(
                                CANT_INSERT_CYCLE_TO_DB,
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
    fun getCommonInfoCycle(date: String): LiveData<DataState<ViewProgressViewState>> {
        return object : DataSourceManager<ViewProgressViewState>() {
            override suspend fun loadFromCache() {
                val cycle = commonDao.getCycle()
                cycle?.let { cycleInfo ->

                    val habitList = commonDao.getHabitsInCurrentCycle()
                    val taskList = commonDao.getDaysForCurrentCycleInCurrentDate(date)
                    val daysList = commonDao.getAllDays("26-07-2020")
                    Logger.loge(daysList)
                    val viewState = ViewProgressViewState()
                    viewState.commonProgressFields =
                        CommonProgressFields(
                            cycleInfo.name,
                            "",
                            habitList.size.toString()
                        )
                    viewState.listOfTaskFields.taskList = taskList
                    onCompleteJob(DataState.data(viewState, null))
                } ?: onCompleteJob(
                        DataState.error(
                            Response(
                                THERE_IS_NOT_CYCLE,
                                "Вы еще не начали свой цикл",
                                ResponseType.Toast()
                            )
                        )
                    )
            }

            override fun setJob(job: Job) {
                addJob("getCommonInfoCycle", job)
            }

        }.asLiveData()
    }
}