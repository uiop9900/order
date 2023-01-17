package dev.practice.order.interfaces.partner;

import dev.practice.order.application.partner.PartnerFacade;
import dev.practice.order.common.response.CommonResponse;
import dev.practice.order.domain.partner.PartnerCommand;
import dev.practice.order.domain.partner.PartnerInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners")
public class PartnerApiController {

    private final PartnerFacade partnerFacade;

    @PostMapping
    public CommonResponse registerPartner(PartnerDto.RegisterRequest request) {
        // 1. 외부에서 전달된 파라미터(dto) -> Command, Criteria로 변경
        PartnerCommand command = request.toCommand();
        // 2. FACADE 호출해서 PartnerInfo return 받음
        PartnerInfo partnerInfo = partnerFacade.registerPartner(command);
        // 3. return 된 PartnerInfo 를 응답값인 CommonResponse로 변환해서 최종 return
        PartnerDto.RegisterResponse response = new PartnerDto.RegisterResponse(partnerInfo);

        return CommonResponse.success(response);
    }
}
