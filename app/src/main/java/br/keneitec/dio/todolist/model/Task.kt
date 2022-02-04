package br.keneitec.dio.todolist.model

data class Task(
    val title: String,
    val hours: String,
    val dates: String,
    val id: Int = 0
)
