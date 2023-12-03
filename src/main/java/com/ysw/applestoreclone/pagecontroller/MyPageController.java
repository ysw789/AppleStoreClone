package com.ysw.applestoreclone.pagecontroller;

import com.ysw.applestoreclone.javabean.OrderBean;
import com.ysw.applestoreclone.service.OrderService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/my-page")
public class MyPageController extends HttpServlet {
    OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        // 주문 정보를 가져오는 로직
        List<OrderBean> orderList;
        try {
            orderList = orderService.getOrderByBuyerId(session.getAttribute("userId").toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.setAttribute("orderList", orderList);

        String viewPath = "/user/my-page.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
        dispatcher.forward(req, res);
    }
}
