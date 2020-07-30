package com.dorokhov.hab.repositories.cycle

import androidx.lifecycle.LiveData
import com.dorokhov.hab.percistance.CommonDao
import com.dorokhov.hab.percistance.HabitDao
import com.dorokhov.hab.percistance.entities.Cycle
import com.dorokhov.hab.percistance.entities.Habit
import com.dorokhov.hab.repositories.DataSourceManager
import com.dorokhov.hab.repositories.JobManager
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.Response
import com.dorokhov.hab.ui.ResponseType
import com.dorokhov.hab.ui.fragments.datastate.createcycle.CreateCycleViewState
import com.dorokhov.hab.ui.fragments.datastate.editcycle.EditCycleViewState
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.CommonProgressFields
import com.dorokhov.hab.ui.fragments.datastate.viewprogress.ViewProgressViewState
import com.dorokhov.hab.utils.ErrorCodes
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
    private val commonDao: CommonDao,
    private val habitDao: HabitDao
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
    fun getCommonInfoWithTasks(date: String): LiveData<DataState<ViewProgressViewState>> {
        return object : DataSourceManager<ViewProgressViewState>() {
            override suspend fun loadFromCache() {
                val cycle = commonDao.getCycle()
                cycle?.let { cycleInfo ->
                    val habitList = commonDao.getHabitsInCurrentCycle()
                    val taskList = commonDao.getAllDays(date)
                    Logger.loge(taskList)
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

    @InternalCoroutinesApi
    fun addNewHabit(
        title: String,
        description: String,
        monday: Boolean,
        tuesday: Boolean,
        wednesday: Boolean,
        thursday: Boolean,
        friday: Boolean,
        saturday: Boolean,
        sunday: Boolean
    ): LiveData<DataState<EditCycleViewState>> {
        return object : DataSourceManager<EditCycleViewState>() {
            override suspend fun loadFromCache() {
                val habitInserResult = habitDao.insertNewHabit(
                    Habit(
                        null,
                        0,
                        title,
                        description,
                        monday,
                        tuesday,
                        wednesday,
                        thursday,
                        friday,
                        saturday,
                        sunday
                    )
                )
                if (habitInserResult >= 0) {
                    onCompleteJob(
                        DataState.data(
                            null,
                            Response(
                                ErrorCodes.SUCCESS,
                                "Новая привычка успешно добавлена",
                                ResponseType.Toast()
                            )
                        )
                    )
                } else {
                    onCompleteJob(
                        DataState.error(
                            Response(
                                ErrorCodes.CANT_INSERT_HABIT_TO_DB,
                                "Не удалось добавить привычку",
                                ResponseType.Toast()
                            )
                        )
                    )
                }
            }

            override fun setJob(job: Job) {
                addJob("addNewHabit", job)
            }
        }.asLiveData()
    }

    @InternalCoroutinesApi
    fun deleteHabit(habitId: Int): LiveData<DataState<EditCycleViewState>> {
        return object : DataSourceManager<EditCycleViewState>() {
            override suspend fun loadFromCache() {
                val deleteResult = habitDao.deleteHabitWithId(habitId)
                val deleteDays = habitDao.deleteDaysFromHabit(habitId)
                if (deleteResult >= 0) {
                    onCompleteJob(
                        DataState.data(
                            null, Response(
                                SUCCESS,
                                "Привычка удалена из цикла",
                                ResponseType.Toast()
                            )
                        )
                    )
                } else {
                    onCompleteJob(DataState.error(Response(
                        ErrorCodes.UNKNOWN_ERROR,
                        "Не удалось удалить",
                        ResponseType.Toast()
                    )))
                }
            }

            override fun setJob(job: Job) {
                addJob("deleteHabit", job)
            }
        }.asLiveData()
    }

    @InternalCoroutinesApi
    fun updateCycleInfo(name: String): LiveData<DataState<EditCycleViewState>> {
        return object : DataSourceManager<EditCycleViewState>() {
            override suspend fun loadFromCache() {
                val updateResult = commonDao.updateCycleName(name)
                if (updateResult == 0) {
                    onCompleteJob(
                        DataState.data(
                            null,
                            Response(SUCCESS, "Имя обновлено", ResponseType.Toast())
                        )
                    )
                } else {
                    onCompleteJob(
                        DataState.error(
                            Response(
                                ErrorCodes.CANT_UPDATE_CYCLE_NAME,
                                "Имя обновлено",
                                ResponseType.Toast()
                            )
                        )
                    )
                }
            }

            override fun setJob(job: Job) {
                addJob("updateCycleInfo", job)
            }
        }.asLiveData()
    }

    @InternalCoroutinesApi
    fun getCommonInfoWithHabits(): LiveData<DataState<EditCycleViewState>> {
        return object : DataSourceManager<EditCycleViewState>() {
            override suspend fun loadFromCache() {
                val cycle = commonDao.getCycle()
                val habits = habitDao.getAllHabits()
                val viewState = EditCycleViewState()
                cycle?.let { info ->
                    viewState.cycleInfoFields.nameCycle = info.name
                } ?: onCompleteJob(
                    DataState.error(
                        Response(
                            ErrorCodes.THERE_IS_NOT_CYCLE,
                            "Не нашел подходящего цикла",
                            ResponseType.Toast()
                        )
                    )
                )
                if (habits.isNotEmpty()) {
                    viewState.habitListFields.habitList = habits as ArrayList<Habit>
                }
                onCompleteJob(DataState.data(viewState, null))
            }

            override fun setJob(job: Job) {
                addJob("getCommonInfoWithHabits", job)
            }
        }.asLiveData()
    }
}