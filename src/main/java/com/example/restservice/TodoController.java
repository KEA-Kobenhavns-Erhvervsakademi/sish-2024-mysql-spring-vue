package com.example.restservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoController {
	@Autowired
	private TodoItemRepository todoItemRepository;

	@PostMapping(path = "/todo")
	@ResponseBody
	public TodoResponse create_todo(@RequestParam String title, @RequestParam TodoStatus status) {
		TodoItem item = new TodoItem(title, status);
		todoItemRepository.save(item);
		return new TodoResponse(true);
	}

	@GetMapping("/todo")
	@ResponseBody
	public Iterable<TodoItem> read_todos() {
		return todoItemRepository.findAll();
	}

	@PutMapping(path = "/todo")
	@ResponseBody
	public TodoResponse update_todo(
			@RequestParam Integer id,
			@RequestParam String title,
			@RequestParam TodoStatus status) {
		Optional<TodoItem> maybeItem = todoItemRepository.findById(id);
		maybeItem.ifPresent(item -> {
			item.title = title;
			item.status = status;
			todoItemRepository.save(item);
		});
		return new TodoResponse(maybeItem.isPresent());
	}

	@DeleteMapping(path = "/todo")
	@ResponseBody
	public TodoResponse delete_todo(@RequestParam Integer id) {
		Optional<TodoItem> maybeItem = todoItemRepository.findById(id);
		maybeItem.ifPresent(item -> {
			todoItemRepository.delete(item);
		});
		return new TodoResponse(maybeItem.isPresent());
	}

	record TodoResponse(boolean success) {
	}

}
