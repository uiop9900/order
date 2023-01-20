package dev.practice.order.infrastructure.order;

import dev.practice.order.common.exeption.InvalidParamException;
import dev.practice.order.domain.order.Order;
import dev.practice.order.domain.order.OrderCommand;
import dev.practice.order.domain.order.payment.PaymentProcessor;
import dev.practice.order.domain.order.payment.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {
    private final List<PaymentValidator> paymentValidatorList;
    private final List<PaymentApiCaller> paymentApiCallerList; // 결제 처리 수단의 객체군




    @Override
    public void pay(Order order, OrderCommand.PaymentRequest paymentRequest) {
        // 반복문을 돌면서 list로 주입받은 validation을 체크한다.
        paymentValidatorList.forEach(paymentValidator -> paymentValidator.validate(order, paymentRequest));
        // 아래에서 반환받은 결제수단으로 결제한다.
        PaymentApiCaller payApiCaller = routingApiCaller(paymentRequest);
        payApiCaller.pay(paymentRequest);
    }

    // 어떤 결제수단을 사용할지에 대해 판단한다.
    private PaymentApiCaller routingApiCaller(OrderCommand.PaymentRequest request) {
        return paymentApiCallerList.stream()
                .filter(paymentApiCaller -> paymentApiCaller.support(request.getPayMethod()))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }
}
