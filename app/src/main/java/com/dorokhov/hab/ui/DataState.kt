package com.dorokhov.hab.ui

// оборачиваем viewState Этим классом
data class DataState<T>(
    var error: Event<StateError>? = null,
    var loading: Loading = Loading(false),
    var data: Data<T>? = null
) {
    companion object {

        fun <T> error(
            response: Response
        ): DataState<T> {
            return DataState(
                error = Event(
                    StateError(
                        response
                    )
                )
            )
        }

        fun <T> loading(
            isLoading: Boolean
        ): DataState<T> {
            return DataState(
                error = null,
                loading = Loading(isLoading),
                data = null
            )
        }

        fun <T> data(
            data: T? = null,
            response: Response? = null
        ): DataState<T> {
            return DataState(
                data = Data(
                    Event.dataEvent(data),
                    Event.responseEvent(response)
                )
            )
        }
    }
}