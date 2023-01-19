package dev.practice.order.interfaces.partner;

import dev.practice.order.domain.partner.PartnerCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, // 생성자로 주입한다.
        unmappedTargetPolicy = ReportingPolicy.ERROR // 없는 컬럼 있을시, 에러 반환
)
public interface PartnerDtoMapper {
    // target method(Source source)
    // 외부에서 전달된 파라미터(dto) -> Command, Criteria로 변경

    PartnerCommand of(PartnerDto.RegisterRequest request); // 상단의 build Project 해야지 코드가 만들어진다.

}
