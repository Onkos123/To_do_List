package br.keneitec.dio.todolist.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.keneitec.dio.todolist.databinding.ActivityAddTaskBinding
import br.keneitec.dio.todolist.dataservice.TaskDataService
import br.keneitec.dio.todolist.extensions.format
import br.keneitec.dio.todolist.extensions.text
import br.keneitec.dio.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*

private lateinit var binding: ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataService.findById(taskId)?.let {
                binding.etTitle.text = it.title
                binding.etDate.text = it.date
                binding.etHour.text = it.hour
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.etDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                timeZone.getOffset(Date().time) * -1
                binding.etDate.text = Date(it).format()
            }
            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG")
        }

        binding.etHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder().build()
            timePicker.addOnPositiveButtonClickListener {
                val hour = if (timePicker.hour in 0..9) {"0"+timePicker.hour.toString()
                } else timePicker.hour.toString()
                val minute = if (timePicker.minute in 0..9) {"0"+timePicker.minute.toString()
                } else timePicker.minute.toString()
                binding.etHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnAddTask.setOnClickListener {
            val task = Task(
                title = binding.etTitle.text,
                hour = binding.etHour.text,
                date = binding.etDate.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataService.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

    }
    companion object {
        const val TASK_ID = "task_id"
    }
}