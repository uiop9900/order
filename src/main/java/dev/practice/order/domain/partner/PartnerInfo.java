package dev.practice.order.domain.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PartnerInfo {
    private Long id;
    private String partnerToken;
    private String partnerName;
    private String businessNo;
    private String email;
    private Partner.Status status;

    public PartnerInfo(Partner partner) {
        this.id = partner.getId();
        this.partnerToken = partner.getPartnerToken();
        this.partnerName = partner.getPartnerName();
        this.businessNo = partner.getBusinessNo();
        this.email = partner.getEmail();
        this.status = partner.getStatus();
    }
}
