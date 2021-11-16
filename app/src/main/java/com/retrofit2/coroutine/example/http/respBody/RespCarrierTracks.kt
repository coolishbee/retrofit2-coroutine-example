package com.retrofit2.coroutine.example.http.respBody

data class RespCarrierTracks (
    val from: Position,
    val to: Position,
    val state: State,
    val carrier: Carrier,

    val progresses: List<Progress>
)

data class Location(
    val name: String
)
data class Position (
    val name: String,
    val time: String
)

data class State(
    val id: String,
    val text: String
)

data class Carrier(
    val id: String,
    val name: String,
    val tel: String
)

data class Progress(
    val time: String,
    val location: Location,
    val status: State,
    val description: String
)