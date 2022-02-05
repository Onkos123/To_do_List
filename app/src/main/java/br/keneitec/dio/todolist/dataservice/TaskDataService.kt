package br.keneitec.dio.todolist.dataservice

import br.keneitec.dio.todolist.model.Task

object TaskDataService {
    private val list = arrayListOf<Task>()

    fun insertTask(task: Task) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }
    }

    fun findById(taskId: Int) = list.find { it.id == taskId }

    fun getList() = list.toList()

    fun deleteTask(task: Task) {
        list.remove(task)
    }
}