package com.example.todo.controller;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    // Build Add Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodo =  todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN,USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto>getTodo(@PathVariable("id") Long todoId){
       TodoDto todoDto = todoService.getTodo(todoId);
       return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    // build get All Todos Rest Api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>>getAllTodos(){
        List<TodoDto>todoDtos = todoService.getAllTodos();
        //return new ResponseEntity<>(todoDtos,HttpStatus.OK);
        return ResponseEntity.ok(todoDtos);
    }

    // Build Update Todo Rest API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto>updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long todoId){
       TodoDto updateTodo =   todoService.updateTodo(todoDto,todoId);
        return ResponseEntity.ok(updateTodo);
    }

    // Build Delete Todo Rest API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteTodo(@PathVariable("id") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo deleted successfully!.");
    }

    // Build complete Todo Rest Api
    @PreAuthorize("hasRole('ADMIN','USER')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto>completeTodo(@PathVariable("id") Long todoId){
        TodoDto updateTodo =  todoService.completeTodo(todoId);
        return ResponseEntity.ok(updateTodo);
    }

    // Build in complete Todo Rest API
    @PreAuthorize("hasRole('ADMIN','USER')")
    @PatchMapping("/{id}/in-complete")
    public ResponseEntity<TodoDto>inCompleteTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodo =  todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }
}
