package com.project.taskManager.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.taskManager.models.Task;
import com.project.taskManager.models.User;
import com.project.taskManager.services.TaskService;
import com.project.taskManager.services.UserService;

@Controller
public class TaskController {
	
	private final UserService userService;
	private final TaskService taskService;

	public TaskController(UserService userService, TaskService taskService) {
		this.userService = userService;
		this.taskService = taskService;
	}
	
	@RequestMapping("/tasks")
	public String home(HttpSession session, Model model) {

		if (session.getAttribute("userId") != null) {
			Long userId = (Long) session.getAttribute("userId");
			User u = userService.findUserById(userId);
			model.addAttribute("user", u);
			List<Task> tasklist = taskService.getAll();
			model.addAttribute("tasks", tasklist);
			return "home.jsp";
		} else {
			return "redirect:/";
		}
	}
	@RequestMapping("/tasks/new")
	public String displayTaskCreation(@ModelAttribute("task") Task myTask, Model model, HttpSession session) {
		if (session.getAttribute("userId") != null) {
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			Long userId = (Long) session.getAttribute("userId");
			User u = userService.findUserById(userId);
			model.addAttribute("user", u);
			return "task.jsp";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/tasks/new", method = RequestMethod.POST)
	public String createNewTask(@Valid @ModelAttribute("task") Task myTask, BindingResult result, HttpSession session,
			Model model, RedirectAttributes limitError) {
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("user", u);

		if (result.hasErrors()) {
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			return "task.jsp";
		} else {
			
			Long aId = (Long) myTask.getAssignee().getId();
			User myAssignee = userService.findUserById(aId);
			List<Task> myList = myAssignee.getAssigned_tasks();
			
			if (myList.size() >= 3) {
				List<User> allusers = userService.findAll();
				model.addAttribute("users", allusers);
				limitError.addFlashAttribute("errors", "User cannot be assigned more than 3 tasks");
				return "redirect:/tasks/new";
				
			} else {
				Task newTask = taskService.createTask(myTask);
				newTask.setCreator(u);
				taskService.updateTask(newTask);
				return "redirect:/tasks";
			}
		}
	}

	@RequestMapping("/tasks/{id}")
	public String displayTask(Model model, HttpSession session, @PathVariable("id") Long taskId) {
		Task thisTask = taskService.findTask(taskId);
		model.addAttribute("task", thisTask);
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("currentUserId", u.getId());
		return "show.jsp";
	}

	@RequestMapping("/tasks/{id}/edit")
	public String displayEditPage(Model model, @ModelAttribute("edit") Task myTask, @PathVariable("id") Long taskId,
			HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		Task editingTask = taskService.findTask(taskId);
		if (u.getId() == editingTask.getCreator().getId()) {
			List<User> allUsers = userService.findAll();
			model.addAttribute("creator", editingTask.getCreator());
			model.addAttribute("edit", editingTask);
			model.addAttribute("users", allUsers);
			model.addAttribute("id", editingTask.getId());
			return "edit.jsp";
		} else {
			return "redirect:/tasks";
		}

	}

	@RequestMapping(value = "/tasks/{id}/edit", method = RequestMethod.POST)
	public String updateTask(Model model, @Valid @ModelAttribute("edit") Task myTask, BindingResult result,
			@PathVariable("id") Long taskId) {
		if (result.hasErrors()) {
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			return "edit.jsp";
		} else {
			taskService.createTask(myTask);
			return "redirect:/tasks";
		}
	}

	@RequestMapping("/tasks/{id}/delete")
	public String deleteTask(@PathVariable("id") Long myId) {
		Task deltask = taskService.findTask(myId);
		if (deltask != null) {
			taskService.deleteTask(myId);
			return "redirect:/tasks";
		} else {
			return "redirect:/tasks";
		}
	}
	@RequestMapping("/lowhigh")
	public String lowHigh(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("user", u);
		List<Task> tasklist = taskService.lowToHigh();
		model.addAttribute("tasks", tasklist);
		return "home.jsp";
	}
	
	@RequestMapping("/highlow")
	public String HighLow(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("user", u);
		List<Task> tasklist = taskService.highToLow();
		model.addAttribute("tasks", tasklist);
		return "home.jsp";
	}

}
