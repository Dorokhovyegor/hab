package com.dorokhov.hab.repositories.habit

import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import javax.inject.Inject

class HabitRepository
@Inject
constructor(
    val habitDao: HabitDao
) : JobManager("HabitRepository") {

    @InternalCoroutinesApi
    fun createNewHabit(
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
                val result = habitDao.insertNewHabit(
                    Habit(
                        null,
                        null,
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

                if (result >= 0) {
                    onCompleteJob(DataState.data())
                } else {
                    onCompleteJob(
                        DataState.error(
                            Response(
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