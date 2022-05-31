package com.example.agrisupportandtorism.controller.order;

import com.example.agrisupportandtorism.config.VnPayConfig;
import com.example.agrisupportandtorism.controller.PaymentController;
import com.example.agrisupportandtorism.dto.payment.PaymentResponseDTO;
import com.example.agrisupportandtorism.dto.order.OrderForm;
import com.example.agrisupportandtorism.entity.order.Order;
import com.example.agrisupportandtorism.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin("http://localhost:9528/")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentController paymentController;

    @PostMapping
    public PaymentResponseDTO insertOrders(@RequestBody OrderForm orderForm, HttpServletRequest request) throws Exception {
        List<Integer> orderNumbers = orderService.insertOrders(orderForm);

        if(orderForm.getPaymentMethod().equals("cod")) {
            PaymentResponseDTO paymentResponseDTO  = new PaymentResponseDTO();
            paymentResponseDTO.setStatus("cod");

            return paymentResponseDTO;
        }else {
            String ipAddress = VnPayConfig.getIpAddress(request);
            String orderInfo = "Thanh toan don hang";
            Integer amount = orderService.getAmount(orderForm.getItemOrders());
            String txnRef = OrderService.getTxfRefByOrderNumbers(orderNumbers);

            return paymentController.createPayment(ipAddress, txnRef, amount, orderInfo);
        }
    }

    @PutMapping
    public void updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
    }

    @GetMapping
    public HashMap<String, Object> getOrderById(@RequestParam Integer orderNumber) {
        return orderService.getOrderById(orderNumber);
    }

    @GetMapping("/all")
    public List<HashMap<String, Object>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/all-in-farm")
    public List<Order> getAllOrdersByFarmId(@RequestParam Integer farmId) {
        return orderService.getAllOrdersByFarmId(farmId);
    }
}