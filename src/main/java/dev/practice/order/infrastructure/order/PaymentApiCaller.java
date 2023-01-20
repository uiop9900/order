package dev.practice.order.infrastructure.order;

import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.payment.PayMethod;

public interface PaymentApiCaller {
    boolean support(PayMethod payMethod); // 어떤 결제수단인지 판별(조건문같이)
    void pay(OrderCommand.PaymentRequest request);
}
