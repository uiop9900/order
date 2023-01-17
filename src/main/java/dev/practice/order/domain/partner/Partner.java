package dev.practice.order.domain.partner;

import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Entity
@Slf4j
@NoArgsConstructor // 기본생성자
@Table(name = "partners")
@Getter
public class Partner extends AbstractEntity {

    private static final String PREFIX_PARTNER = "ptn_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String partnerToken;
    private String partnerName;
    private String businessNo;
    private String email;

    @Enumerated(EnumType.STRING) // 숫자값이 아닌 영어이름이 실제 DB에 들어간다.
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ENABLE("활성화"), DISABLE("비활성화");
        private final String description;
    }

    public void enable() {
        this.status = Status.ENABLE;
    }

    public void disable() {
        this.status = Status.DISABLE;
    }

    @Builder
    public Partner(String partnerName, String businessNo, String email) { // 필수값들이라 무조건 받게끔 만든다.
        if (StringUtils.isEmpty(partnerName)) throw new RuntimeException("empty partnerName");
        if (StringUtils.isEmpty(businessNo)) throw new RuntimeException("empty businessNo");
        if (StringUtils.isEmpty(email)) throw new RuntimeException("empty email");

        this.partnerToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_PARTNER);
        this.partnerName = partnerName;
        this.businessNo = businessNo;
        this.email = email;
        this.status = Status.ENABLE; // 파트너가 등록하면 보통 활성화상태이기에, 활성화로 등록한다.
    }
}
