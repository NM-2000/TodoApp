package com.nm_2000.todo_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insertTodo(todo: Todo);

    @Delete
    suspend fun deleteTodo(todo: Todo);

    @Query("SELECT * FROM Todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?;

    @Query("SELECT * FROM Todo")
    fun getAllTodos(): Flow<List<Todo>>;

}