package com.example.agrisupportandtorism.service.order;

import com.example.agrisupportandtorism.config.VnPayConfig;
import com.example.agrisupportandtorism.dto.farm.FarmDTO;
import com.example.agrisupportandtorism.dto.order.ItemOrder;
import com.example.agrisupportandtorism.dto.order.OrderForm;
import com.example.agrisupportandtorism.dto.product.ShortProductDTO;
import com.example.agrisupportandtorism.entity.address.Address;
import com.example.agrisupportandtorism.entity.farm.Farm;
import com.example.agrisupportandtorism.entity.order.Order;
import com.example.agrisupportandtorism.entity.order.OrderDetail;
import com.example.agrisupportandtorism.entity.order.OrderDetailId;
import com.example.agrisupportandtorism.entity.product.Product;
import com.example.agrisupportandtorism.entity.user.User;
import com.example.agrisupportandtorism.exception.PermissionException;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.order.OrderDetailRepo;
import com.example.agrisupportandtorism.repository.order.OrderRepo;
import com.example.agrisupportandtorism.service.farm.FarmService;
import com.example.agrisupportandtorism.service.product.CartService;
import com.example.agrisupportandtorism.service.product.ProductService;
import com.example.agrisupportandtorism.service.user.UserService;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import com.example.agrisupportandtorism.utils.UrlUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private FarmService farmService;

    public List<Order> getAllOrdersByFarmId(Integer farmId) {
        List<Order> orders = orderRepo.getAllByFarmId(farmId);
        for (Order order : orders) {
            order.loadDateTime();
        }
        return orders;
    }

    public Order findById(Integer id) {
        Optional<Order> orderOpt = orderRepo.findById(id);
        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Đơn hàng với mã=[$s] không tồn tại trong hệ thống", id));
        }
        return orderOpt.get();
    }

    public HashMap<String, Object> getOrderById(Integer orderNumber) {
        Order orderInRepo = findById(orderNumber);
        orderInRepo.loadDateTime();
        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("orderNumber", orderInRepo.getOrderNumber());
        orderMap.put("address", orderInRepo.getAddress());
        orderMap.put("orderDate", orderInRepo.getOrderDate());
        orderMap.put("orderTime", orderInRepo.getOrderTime());
        orderMap.put("status", orderInRepo.getStatus());
        orderMap.put("paymentMethod", orderInRepo.getPaymentMethod());
        orderMap.put("paymentId", orderInRepo.getPaymentId());

        orderMap.put("orderedProducts", getOrderedProduct(orderInRepo.getOrderDetails()).get("orderedProducts"));
        return orderMap;
    }

    public List<HashMap<String, Object>> getAllOrders() {
        User currentUser = userService.getCurrentUser();
        List<Order> orders = orderRepo.getAllByCustomer(currentUser);

        List<HashMap<String, Object>> ordersResult = new ArrayList<>();
        for (Order order : orders) {
            if(order.getPaymentMethod().equals("vnpay") && order.getPaymentId() == null) continue;
            order.loadDateTime();

            HashMap<String, Object> orderedProductsAndFarmId = getOrderedProduct(order.getOrderDetails());
            HashMap<String, Object> orderMap = new HashMap<>();

            orderMap.put("farm", farmService.findById((Integer) orderedProductsAndFarmId.get("farmId")));
            orderMap.put("status", order.getStatus());
            orderMap.put("orderDate", order.getOrderDate());
            orderMap.put("orderTime", order.getOrderTime());
            orderMap.put("orderedProducts", orderedProductsAndFarmId.get("orderedProducts"));
            orderMap.put("paymentMethod", order.getPaymentMethod());
            orderMap.put("paymentId", order.getPaymentId());
            ordersResult.add(orderMap);
        }
        return ordersResult;
    }

    public Integer getAmount(List<ItemOrder> items) {
        Integer amount = 0;
        for (ItemOrder itemOrder : items) {
            Product product = productService.findById(itemOrder.getProductId());

            Integer price = product.getPrice();
            Integer quantity = itemOrder.getQuantity();

            amount += price * quantity;
        }
        return amount;
    }

    @Transactional
    public List<Integer> insertOrders(OrderForm orderForm) {
        List<Integer> orderNumbers = new ArrayList<>();
        List<ItemOrder> items = orderForm.getItemOrders();
        Address address = orderForm.getAddress();
        String paymentMethod = orderForm.getPaymentMethod();

        HashMap<Integer, List<OrderDetail>> ordersFarmMap = new HashMap<>();
        for (ItemOrder itemOrder : items) {
            Product product = productService.findById(itemOrder.getProductId());
            Integer farmId = product.getFarmId();

            Integer price = product.getPrice();
            Integer quantity = itemOrder.getQuantity();

            OrderDetailId orderDetailId = new OrderDetailId();
            orderDetailId.setProductId(product.getId());

            OrderDetail orderDetail = new OrderDetail(orderDetailId, null, price, quantity);

            if (ordersFarmMap.containsKey(farmId)) {
                ordersFarmMap.get(farmId).add(orderDetail);
            } else {
                List<OrderDetail> orderDetails = new ArrayList<>();
                orderDetails.add(orderDetail);
                ordersFarmMap.put(farmId, orderDetails);
            }
            if (paymentMethod.equals("cod")) {
                cartService.delete(product.getId());
            }
        }

        for (Integer farmId : ordersFarmMap.keySet()) {
            List<OrderDetail> orderDetails = ordersFarmMap.get(farmId);
            Order order = new Order();
            order.setCustomer(userService.getCurrentUser());
            order.setOrderDateTime(LocalDateTime.now());
            order.setFarmId(farmId);
            order.setStatus("Tiếp nhận");
            order.setPaymentMethod(paymentMethod);
            order.setAddress(address);
            Integer orderNumber = orderRepo.save(order).getOrderNumber();
            orderNumbers.add(orderNumber);
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.getOrderDetailId().setOrderNumber(orderNumber);
                orderDetailRepo.saveAndFlush(orderDetail);
            }
        }
        return orderNumbers;
    }

    @Transactional
    public void updatePaymentSuccess(String txfRef) {
        List<String> orderNumbers = getOrderNumbersByTxfRef(txfRef);
        System.out.println("updatePaymentSuccess: txfref: " + txfRef + ", number order update: " + orderNumbers.size());
        for (String orderNumber : orderNumbers) {
            Order order = findById(Integer.parseInt(orderNumber));
            order.setPaymentId(txfRef);
            orderRepo.save(order);
            System.out.println("updatePaymentSuccess: order number: " + orderNumber);
        }
    }

    @Transactional
    public void updatePaymentFailure(String txfRef) {
        List<String> orderNumbers = getOrderNumbersByTxfRef(txfRef);
        System.out.println("updatePaymentFailure: txfref: " + txfRef + ", number order update: " + orderNumbers.size());

        for (String orderNumber : orderNumbers) {
            System.out.println("updatePaymentFailure: order number: " + orderNumber);
            orderRepo.deleteById(Integer.parseInt(orderNumber));
        }
    }

    @Transactional
    public void updateOrder(Order order) {
        Order orderInRepo = findById(order.getOrderNumber());
        if (farmService.isFarmOwner(orderInRepo.getFarmId())) {
            orderInRepo.setStatus(order.getStatus());
        } else {
            throw new PermissionException(String.format("Không có quyền sửa"));
        }
    }

    private HashMap<String, Object> getOrderedProduct(List<OrderDetail> orderDetails) {
        HashMap<String, Object> orderMap = new HashMap<>();

        List<HashMap<String, Object>> orderedProducts = new ArrayList<>();
        Integer farmId = null;
        for (OrderDetail orderDetail : orderDetails) {
            HashMap<String, Object> orderedProduct = new HashMap<>();
            orderedProduct.put("quantity", orderDetail.getQuantity());
            orderedProduct.put("price", orderDetail.getPrice());

            Product product = productService.findById(orderDetail.getOrderDetailId().getProductId());

            orderedProduct.put("name", product.getName());
            orderedProduct.put("mainImage", UrlUtils.convertStringToUrlList(product.getRawStringImageUrl()).get(0));
            orderedProducts.add(orderedProduct);

            farmId = product.getFarmId();
        }
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("farmId", farmId);
        return orderMap;
    }

    public static final String ORDER_NUMBER_SPLITTER = "/";

    public static String getTxfRefByOrderNumbers(List<Integer> orderNumbers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer orderNumber : orderNumbers) {
            stringBuilder.append(orderNumber.toString() + ORDER_NUMBER_SPLITTER);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static List<String> getOrderNumbersByTxfRef(String txfRef) {
        if(!txfRef.isEmpty()){
            return Arrays.asList(txfRef.split(ORDER_NUMBER_SPLITTER));
        }else{
            return new ArrayList<>();
        }
    }

}
