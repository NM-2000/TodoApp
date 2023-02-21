package com.nm_2000.todo_app.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nm_2000.todo_app.data.Todo
import com.nm_2000.todo_app.data.TodoRepository
import com.nm_2000.todo_app.util.Routes
import com.nm_2000.todo_app.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todos = todoRepository.getAllTodos()

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent) {
        when(event) {
            is TodoListEvent.onTodoClick -> {
                sendUIEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.onAddTodoClick -> {
                sendUIEvent(UIEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.onDeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    todoRepository.deleteTodo(event.todo)
                    sendUIEvent(UIEvent.ShowSnackBar("Todo deleted", "Undo"))
                }
            }
            is TodoListEvent.onDoneChange -> {
                viewModelScope.launch {
                    todoRepository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is TodoListEvent.onUndoDeleteClick -> {
                deletedTodo?.let {
                    todo -> viewModelScope.launch {
                        todoRepository.insertTodo(todo)
                    }
                }
            }
        }
    }

    fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}