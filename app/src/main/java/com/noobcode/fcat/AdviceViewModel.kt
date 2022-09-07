package com.noobcode.fcat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class AdviceViewModel : ViewModel() {

    private var _advice =
        MutableStateFlow(
            Result(
                state = State.Loading,
                advice = AdviceObject(Slip("", 0))
            )
        )
    val advice: StateFlow<Result> get() = _advice

    data class Result(val state: State, val advice: AdviceObject)

    enum class State {
        Success,
        Failed,
        Loading
    }

    init {
        CoroutineScope(IO).launch {
            fetchAdvice()
        }
    }

    fun fetchAdvice() {
        viewModelScope.launch {
            _advice.emit(
                Result(
                    state = State.Loading,
                    advice = AdviceObject(Slip("", 0))
                )
            )
            try {
                val call = RestClient().getService().fetchAdvice()
                val response = call.awaitResponse()
                if (response.isSuccessful) {
                    _advice.emit(
                        Result(
                            state = State.Success,
                            advice = response.body()!!
                        )
                    )
                } else {
                    _advice.emit(
                        Result(
                            state = State.Failed,
                            advice = AdviceObject(Slip("", 0))
                        )
                    )
                }
            } catch (e: Exception) {
                _advice.emit(
                    Result(
                        state = State.Failed,
                        advice = AdviceObject(Slip("", 0))
                    )
                )
            }
        }
    }
}