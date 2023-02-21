package com.nm_2000.todo_app.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo);

    suspend fun deleteTodo(todo: Todo);

    suspend fun getTodoById(id: Int): Todo?;

    fun getAllTodos(): Flow<List<Todo>>;

}