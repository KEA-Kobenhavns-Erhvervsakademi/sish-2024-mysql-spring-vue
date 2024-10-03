package com.example.restservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(origins = "*", methods = { GET, HEAD, POST, OPTIONS })
public class TodoController {
	@Autowired
	private TodoItemRepository todoItemRepository;

	@PostMapping(path = "/todo/create")
	@ResponseBody
	public TodoItemResponse createTodo(@RequestParam String title, @RequestParam TodoStatus status) {
		TodoItem item = new TodoItem(title, status);
		todoItemRepository.save(item);
		return new TodoItemResponse(true, item);
	}

	@GetMapping("/todo/list")
	@ResponseBody
	public Iterable<TodoItem> readTodos() {
		return todoItemRepository.findAll();
	}

	@PostMapping(path = "/todo/update")
	@ResponseBody
	public TodoResponse updateTodo(
			@RequestParam Integer id,
			@RequestParam Optional<String> title,
			@RequestParam Optional<TodoStatus> status) {
		Optional<TodoItem> maybeItem = todoItemRepository.findById(id);
		maybeItem.ifPresent(item -> {
			title.ifPresent((t) -> {
				item.title = t;
			});
			status.ifPresent((s) -> {
				item.status = s;
			});
			todoItemRepository.save(item);
		});
		return new TodoResponse(maybeItem.isPresent());
	}

	@PostMapping(path = "/todo/delete")
	@ResponseBody
	public TodoResponse deleteTodo(@RequestParam Integer id) {
		Optional<TodoItem> maybeItem = todoItemRepository.findById(id);
		maybeItem.ifPresent(item -> {
			todoItemRepository.delete(item);
		});
		return new TodoResponse(maybeItem.isPresent());
	}

	record TodoResponse(boolean success) {
	}

	record TodoItemResponse(boolean success, TodoItem item) {
	}

}
