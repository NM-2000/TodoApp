package com.nm_2000.todo_app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo);

    @Delete
    suspend fun deleteTodo(todo: Todo);

    @Query("SELECT * FROM Todo WHERE id = :id")
    suspend fun getTodoById(id: Int): Todo?;

    @Query("SELECT * FROM Todo")
    fun getAllTodos(): Flow<List<Todo>>;

}