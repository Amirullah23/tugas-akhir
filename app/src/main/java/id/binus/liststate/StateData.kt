package id.binus.liststate

data class StateData(
    val data: List<ResultDt>
)

data class ResultDt(
    val State: String,
    val Population: String,
)