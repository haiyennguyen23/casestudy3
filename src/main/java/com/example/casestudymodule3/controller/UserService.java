package com.example.casestudymodule3.controller;

//import com.example.casestudymodule3.service.user.service.ICategoryService;
import com.example.casestudymodule3.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserController", value = "/User")
public class UserService extends HttpServlet {
//    ICategoryService categoryService = new CategoryService();
//    IUserService categoryService = new CategoryService();
//    IUserService UserService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String action =request.getParameter("action");
      if(action==null){
          action="";
      }
      switch (action){
          case"create":
              showFromCreate(request,response);
              break;
          default:
              showAllUser(request,response);
      }
    }



    private void showFromCreate(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/create.isp");
//        request.setAttribute("categories",categoryService.finfAll());
        try{
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAllUser(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user/index.isp");
//        List<User> users = UserService.findAll();
//        request.setAttribute("user",users);
        try{
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("action");
        if(action==null){
            action="";
        }
        switch (action){
            case"create":
                createNewUser(request,response);
                break;
            default:
                showAllUser(request,response);
        }
    }

    private void createNewUser(HttpServletRequest request, HttpServletResponse response) {
        int id = request.getIntHeader("id");
        String name=request.getParameter("name");
        String email=request.getParameter("email");
        String address=request.getParameter("address");
        String country=request.getParameter("country");
        String[] categoriesStr = request.getParameterValues("categories");
        int[] categories = new int[categoriesStr.length];
        for (int i = 0; i < categoriesStr.length; i++) {
            categories[i] = Integer.parseInt(categoriesStr[i]);
        }

        User user = new User();
//        userService.save(user,categories);

    }


}
