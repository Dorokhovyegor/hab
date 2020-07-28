package com.dorokhov.hab.repositories.habit

import androidx.lifecycle.LiveData
import com.dorokhov.hab.percistance.CommonDao
import com.dorokhov.hab.percistance.HabitDao
import com.dorokhov.hab.percistance.entities.Habit
import com.dorokhov.hab.repositories.DataSourceManager
import com.dorokhov.hab.repositories.JobManager
import com.dorokhov.hab.ui.DataState
import com.dorokhov.hab.ui.Response
import com.dorokhov.hab.ui.ResponseType
import com.dorokhov.hab.ui.fragments.datastate.HabitFields
import com.dorokhov.hab.ui.fragments.datastate.ViewHabitViewState
import com.dorokhov.hab.ui.fragments.datastate.createhabit.CreateNewHabitViewState
import com.dorokhov.hab.utils.DaysFactory
import com.dorokhov.hab.utils.ErrorCodes
import com.dorokhov.hab.utils.ErrorCodes.SUCCESS
import com.dorokhov.hab.utils.Logger
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import javax.inject.Inject

class HabitRepository
@Inject
constructor(
    val habitDao: HabitDao,
    val commonDao: CommonDao
) : JobManager("HabitRepository") {

    @InternalCoroutinesApi
    fun createAndAddNewHabit(
        title: String,
        description: String,
        monday: Boolean,
        tuesday: Boolean,
        wednesday: Boolean,
        thursday: Boolean,
        friday: Boolean,
        saturday: Boolean,
        sunday: Boolean
    ): LiveData<DataState<CreateNewHabitViewState>> {
        return object : DataSourceManager<CreateNewHabitViewState>() {

            override suspend fun loadFromCache() {
                val habit = Habit(null, 0, title, description, monday, tuesday, wednesday, thursday, friday, saturday, sunday)
                val resultInsertNewHabit = habitDao.insertNewHabit(habit)
                if (resultInsertNewHabit >= 0) {
                    val startDate = commonDao.getCycle()?.startDate!!
                    val endDate = commonDao.getCycle()?.endDate!!
                    val listDays = DaysFactory.createDays(startDate, endDate, habit.updateObject(resultInsertNewHabit.toInt()))
                    val results = habitDao.addDaysToCurrentHabit(listDays)
                    Logger.loge("вставленные дни ${results}")
                    onCompleteJob(DataState.data(null, Response(
                        SUCCESS,
                        "Привычка добавлена",
                        ResponseType.Toast()
                    )))
                } else {
                    onCompleteJob(
                        DataState.error(
                            Response(
                                ErrorCodes.CANT_INSERT_HABIT_TO_DB,
                                "Can't insert new habit into db",
                                ResponseType.Dialog()
                            )
                        )
                    )

        }
    }
            override fun setJob(job: Job) {
                addJob("createNewHabit", job)
            }
        }.asLiveData()
    }

/*    @InternalCoroutinesApi
    fun deleteHabitById(habitId: Int): LiveData<DataState<>> {

    }*/

    @InternalCoroutinesApi
    fun getAllHabits(): LiveData<DataState<ViewHabitViewState>> {
        return object : DataSourceManager<ViewHabitViewState>() {
            override suspend fun loadFromCache() {
                habitDao.getAllHabits().let { habits ->
                    onCompleteJob(DataState.data(ViewHabitViewState(HabitFields(habits)), null))
                }
            }

            override fun setJob(job: Job) {
                addJob("getAllHabits", job)
            }
        }.asLiveData()
    }
}