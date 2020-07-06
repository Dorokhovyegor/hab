package com.dorokhov.hab.ui

interface ChangeDataStateListener {
    fun onDataStateChanged(dataState: DataState<*>)
}