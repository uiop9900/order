package dev.practice.order.interfaces.partner;

import dev.practice.order.domain.partner.Partner;
import dev.practice.order.domain.partner.PartnerCommand;
import dev.practice.order.domain.partner.PartnerInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PartnerDto {

    // static inner class -> 부를때마다 생성 -> 동시성 이슈 없음.
    @Getter
    @Setter
    @ToString
    public static class RegisterRequest{
        @NotEmpty(message = "partnerName 는 필수값입니다")
        private String partnerName;

        @NotEmpty(message = "businessNo 는 필수값입니다")
        private String businessNo;

        @Email(message = "email 형식에 맞아야 합니다")
        @NotEmpty(message = "email 는 필수값입니다")
        private String email;

        public PartnerCommand toCommand() {
            return PartnerCommand.builder()
                    .partnerName(partnerName)
                    .businessNo(businessNo)
                    .email(email)
                    .build();
        }
    }

    public static class RegisterResponse{
        private final String partnerToken;
        private final String partnerName;
        private final String businessNo;
        private final String email;
        private final Partner.Status status;

        // 생성자
        public RegisterResponse(PartnerInfo partnerInfo) {
            this.partnerToken = partnerInfo.getPartnerToken();
            this.partnerName = partnerInfo.getPartnerName();
            this.businessNo = partnerInfo.getBusinessNo();
            this.email = partnerInfo.getEmail();
            this.status = partnerInfo.getStatus();
        }
    }
}
