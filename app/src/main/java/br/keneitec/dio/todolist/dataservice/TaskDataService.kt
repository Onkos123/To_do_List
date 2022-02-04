package br.keneitec.dio.todolist.dataservice

import br.keneitec.dio.todolist.model.Task

object TaskDataService {
    private val list = arrayListOf<Task>()

    fun getList() = list

    fun insertTask(task: Task){
        list.add(task.copy(id = list.size+1))
    }
}