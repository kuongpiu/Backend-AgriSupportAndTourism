package com.example.agrisupportandtorism.controller;

import com.example.agrisupportandtorism.config.VnPayConfig;
import com.example.agrisupportandtorism.dto.payment.PaymentResponseDTO;
import com.example.agrisupportandtorism.dto.payment.PaymentResult;
import com.example.agrisupportandtorism.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin("http://localhost:9528/")

public class PaymentController {

    @Autowired
    private VnPayConfig vnPayConfig;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/ipn-url")
    public HashMap<String, Object> getCodeIPNURL(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = vnPayConfig.hashAllFields(fields);
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");

        HashMap<String, Object> result = new HashMap<>();
        if (!signValue.equals(vnp_SecureHash)) {
            result.put("RspCode", "97");
            result.put("Message", "Invalid signature");
            return result;
        }

        boolean checkOrderId = true; // vnp_TxnRef exists in your database
        if (!checkOrderId) {
            result.put("RspCode", "01");
            result.put("Message", "Order not found");
            return result;
        }

        boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
        if (!checkAmount) {
            result.put("RspCode", "04");
            result.put("Message", "invalid amount");
            return result;
        }

        boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)
        if (!checkOrderStatus) {
            result.put("RspCode", "02");
            result.put("Message", "Order already confirmed");
            return result;
        }

        final String successCode = "00";
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");

        if (vnp_ResponseCode.equals(successCode) && vnp_TransactionStatus.equals(successCode)) {
            // Trạng thái thanh toán thành công
        } else {
            // Trạng thái thanh toán thất bại / lỗi
        }

        result.put("RspCode", "00");
        result.put("Message", "Confirm Success");
        return result;
    }

    @GetMapping("/result")
    public ResponseEntity updatePaymentResult(HttpServletRequest request, HttpServletRequest response) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }

        final String successCode = PaymentService.SUCCESS_CODE;
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");

        String notificationUrl;

        paymentService.updatePayment(fields);

        if (vnp_ResponseCode.equals(successCode) && vnp_TransactionStatus.equals(successCode)) {
            // Trạng thái thanh toán thành công
            notificationUrl = "http://localhost:9528/#/confirm-order-information/result/00";
        } else {
            // Trạng thái thanh toán thất bại / lỗi
            notificationUrl = "http://localhost:9528/#/confirm-order-information/result/01";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(notificationUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping
    public PaymentResponseDTO createPayment(String vnp_IpAddr, String vnp_TxnRef, Integer amount, String vnp_OrderInfo) throws Exception {
        String vnp_Version = vnPayConfig.getVersion();
        String vnp_Command = vnPayConfig.getCommand();
        String vnp_TmnCode = vnPayConfig.getTmnCode();

        amount = amount * 100;

        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        LocalDateTime createDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String vnp_CreateDate = createDate.format(dateTimeFormatter);

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        LocalDateTime expireDate = createDate.minusMinutes(15);
        String vnp_ExpireDate = expireDate.format(dateTimeFormatter);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);


        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayConfig.hmacSHA512(hashData.toString());
        queryUrl += "&vnp_SecureHashType=HmacSHA512&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnPayConfig.getPayUrl() + "?" + queryUrl;

        PaymentResponseDTO paymentRes = new PaymentResponseDTO();
        paymentRes.setStatus("00");
        paymentRes.setMessage("success");
        paymentRes.setUrl(paymentUrl);

        return paymentRes;
    }
}
