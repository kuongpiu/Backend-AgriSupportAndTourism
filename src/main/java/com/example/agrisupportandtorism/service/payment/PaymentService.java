package com.example.agrisupportandtorism.service.payment;

import com.example.agrisupportandtorism.entity.payment.Payment;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.payment.PaymentRepo;
import com.example.agrisupportandtorism.service.order.OrderService;
import com.example.agrisupportandtorism.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private OrderService orderService;

    public static String SUCCESS_CODE = "00";

    @Transactional
    public void updatePayment(Map<String, String> fields) {
        String vnp_ResponseCode = fields.get("vnp_ResponseCode");
        String vnp_TransactionStatus = fields.get("vnp_TransactionStatus");
        String txnRef = fields.get("vnp_TxnRef");

        if (vnp_ResponseCode.equals(SUCCESS_CODE) && vnp_TransactionStatus.equals(SUCCESS_CODE)) {
            // Trạng thái thanh toán thành công
            String tmmCode = fields.get("vnp_TmnCode");
            Integer amount = Integer.parseInt(fields.get("vnp_Amount")) / 100;
            String bankCode = fields.get("vnp_BankCode");
            String transactionNumber = fields.get("vnp_TransactionNo");
            String orderInfo = fields.get("vnp_OrderInfo");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime payDate = LocalDateTime.parse(fields.get("vnp_PayDate"), formatter);

            paymentRepo.save(new Payment(txnRef, tmmCode, amount, bankCode, transactionNumber, orderInfo, payDate));

            orderService.updatePaymentSuccess(txnRef);
        } else {
            orderService.updatePaymentFailure(txnRef);
        }
    }

    public Payment findById(String paymentId) {
        Optional<Payment> paymentOpt = paymentRepo.findById(paymentId);

        if (paymentOpt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Payment với mã=[%s] không tồn tại trong hệ thống", paymentId));
        }

        return paymentOpt.get();
    }
}
