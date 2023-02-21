package com.nm_2000.todo_app.ui.todo_list

import com.nm_2000.todo_app.data.Todo

sealed class TodoListEvent {

    data class onDeleteTodo(val todo: Todo): TodoListEvent()

    data class onDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()

    object onUndoDeleteClick: TodoListEvent()

    data class onTodoClick(val todo: Todo): TodoListEvent()

    object onAddTodoClick: TodoListEvent()

}
