package dev.practice.order.domain.partner;

public interface PartnerService {
    // 등록
    PartnerInfo registerPartner(PartnerCommand command);

    // 조회
    PartnerInfo getPartnerInfo(String partnerToken);

    // 상태변화
    PartnerInfo enablePartner(String partnerToken);
    PartnerInfo disablePartner(String partnerToken);
}
