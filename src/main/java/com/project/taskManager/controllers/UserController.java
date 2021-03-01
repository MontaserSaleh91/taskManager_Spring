package com.project.taskManager.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.taskManager.models.User;
import com.project.taskManager.services.UserService;
import com.project.taskManager.validator.UserValidator;

@Controller
public class UserController {
	
		private final UserService userService;
		private final UserValidator userValidator;

		public UserController(UserService userService, UserValidator userValidator) {
			this.userService = userService;
			this.userValidator = userValidator;
		}
		
		@RequestMapping("/")
		public String index(@ModelAttribute("user") User user) {
			return "index.jsp";
		}

		@RequestMapping(value = "/registration", method = RequestMethod.POST)
		public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
			userValidator.validate(user, result);
			if (result.hasErrors()) {
				return "index.jsp";
			}
			User u = userService.registerUser(user);
			session.setAttribute("userId", u.getId());
			return "redirect:/tasks";
		}

		@RequestMapping(value = "/login", method = RequestMethod.POST)
		public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
				HttpSession session, @ModelAttribute("user") User user) {
			boolean isAuthenticated = userService.authenticateUser(email, password);
			if (isAuthenticated) {
				User u = userService.findByEmail(email);
				session.setAttribute("userId", u.getId());
				return "redirect:/tasks";
			} else {
				model.addAttribute("error", "Invalid Credentials. Please try again.");
				return "index.jsp";
			}
		}

		@RequestMapping("/logout")
		public String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/";
		}
	
	}
