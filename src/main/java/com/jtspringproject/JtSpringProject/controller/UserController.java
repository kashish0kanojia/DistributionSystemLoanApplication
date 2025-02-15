package com.jtspringproject.JtSpringProject.controller;


import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jtspringproject.JtSpringProject.services.userService;
import com.jtspringproject.JtSpringProject.services.productService;


@Controller
public class UserController{

	String usernameforclass = "";
	User globalUser;
	
	@Autowired
	private userService userService;

	@Autowired
	private productService productService;


	@GetMapping("/register")
	public String registerUser()
	{
		return "register";
	}

	int userlogincheck = 0;

	@GetMapping("/")
	public String userlogin(Model model) {
		
		return "userLogin";
	}

	@RequestMapping(value = {"logout"})
	public String returnIndex() {
		userlogincheck =0;
		usernameforclass = "";
		return "userLogin";
	}

	@RequestMapping(value = {"getDetails/logout"})
	public String returndiffIndex() {
		userlogincheck =0;
		usernameforclass = "";
		return "userLogin";
	}

	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public ModelAndView userlogin( @RequestParam("username") String username, @RequestParam("password") String pass,Model model,HttpServletResponse res) {
		
		System.out.println(pass);
		User u = this.userService.checkLogin(username, pass);
		globalUser = u;
		System.out.println(u.getUsername());

		if(username.equals(u.getUsername())) {
			userlogincheck = 1;

			res.addCookie(new Cookie("username", u.getUsername()));
			ModelAndView mView  = new ModelAndView("index");	
			mView.addObject("user", u);
			List<Product> products = this.productService.getProducts();

			if (products.isEmpty()) {
				mView.addObject("msg", "No products are available");
			} else {
				mView.addObject("products", products);
			}
			return mView;

		}else {
			ModelAndView mView = new ModelAndView("userLogin");
			mView.addObject("msg", "Please enter correct email and password");
			return mView;
		}
		
	}
	
	
	@GetMapping("/user/products")
	public ModelAndView getproduct() {

		ModelAndView mView = new ModelAndView("uproduct");

		List<Product> products = this.productService.getProducts();

		if(products.isEmpty()) {
			mView.addObject("msg","No products are available");
		}else {
			mView.addObject("products",products);
		}

		return mView;
	}
	@RequestMapping(value = "newuserregister", method = RequestMethod.POST)
	public ModelAndView newUseRegister(@ModelAttribute User user)
	{
		// Check if username already exists in database
		boolean exists = this.userService.checkUserExists(user.getUsername());

		if(!exists) {
			System.out.println(user.getEmail());
			user.setRole("ROLE_NORMAL");
			this.userService.addUser(user);

			System.out.println("New user created: " + user.getUsername());
			ModelAndView mView = new ModelAndView("userLogin");
			return mView;
		} else {
			System.out.println("New user not created - username taken: " + user.getUsername());
			ModelAndView mView = new ModelAndView("register");
			mView.addObject("msg", user.getUsername() + " is taken. Please choose a different username.");
			return mView;
		}
	}


	@GetMapping("/getDetails/{id}")
	public ModelAndView getProductDetail(@PathVariable("id") int id) {
		ModelAndView mView = new ModelAndView("getDetails");
		Product product = this.productService.getProduct(id);
		int sw = product.getSalary_weight();
		int gw = product.getGender_weight();
		int cw = product.getCreditScores_weight();
		System.out.println(product.getDescription());
		System.out.println(globalUser.getUsername());
		String age_string = globalUser.getAge();
		String salary_string = globalUser.getSalary();
		String credit_string = globalUser.getCreditScore();
		String gender = globalUser.getGender();
		int age = Integer.parseInt(age_string);
		int credit_score = Integer.parseInt(credit_string);
		int salary = Integer.parseInt(salary_string);

		double interest ;

		interest = (500.0-age)/(sw*salary + cw*credit_score);
		interest = interest * 100;

		System.out.println(interest);
		if(gender.equals("F")){
			System.out.println("AEE yee to ladki hai");
			interest -= gw/10.0;
		}

		System.out.println(interest);

		double amount = salary*2;



		mView.addObject("interest",interest);
		mView.addObject("amount",amount);
		mView.addObject("description",product.getDescription());
		// Add logic to fetch product details based on the 'id' parameter
		return mView;
	}

	@GetMapping("profileDisplay")
	public ModelAndView getUserInfo() {
		ModelAndView mView = new ModelAndView("getUserDetail");
		mView.addObject("userName",globalUser.getUsername());
		mView.addObject("userAge",globalUser.getAge());
		mView.addObject("userEmail",globalUser.getEmail());
		mView.addObject("userCreditScore",globalUser.getCreditScore());
		// Add logic to fetch product details based on the 'id' parameter
		return mView;
	}

	  
}
