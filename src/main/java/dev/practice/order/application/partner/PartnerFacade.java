package dev.practice.order.application.partner;

import dev.practice.order.domain.notification.NotificationService;
import dev.practice.order.domain.partner.PartnerCommand;
import dev.practice.order.domain.partner.PartnerInfo;
import dev.practice.order.domain.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerFacade {

    private final PartnerService partnerService;
    private final NotificationService notificationService;

    // 파트너 등록
    public PartnerInfo registerPartner(PartnerCommand command) { // 1번이 성공되면 2번이 실패해도 롤백되지 않게 할 것이다.
        // 1. partnerService.register.
        PartnerInfo partnerInfo = partnerService.registerPartner(command);
        // 2. email 보내기.
        notificationService.sendEmail(partnerInfo.getEmail(), "title", "description");
        return partnerInfo;
    }
}
