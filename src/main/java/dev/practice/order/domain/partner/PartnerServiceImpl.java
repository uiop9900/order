package dev.practice.order.domain.partner;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.lang.annotation.Retention;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService{

    private final PartnerStore partnerStore;
    private final PartnerReader partnerReader;

    @Override
    @Transactional
    public PartnerInfo registerPartner(PartnerCommand command) {
        // 개인적으로 service 안에 로직은 3줄로 정의가능해야한다. 그 외의 자세한 구현은 infraStructure로 넘겨야한다.
        // 1. comman로 들어온 정보를 -> initPartner
        // 2.  initPartner -> save
        // 3. 저장된 Partner를 PartnerInfo 변환 후 return한다.

        Partner initPartner = command.toEntity();
        Partner partner = partnerStore.store(initPartner); // 저장한다.
        return new PartnerInfo(partner);
    }

    @Override
    @Transactional
    public PartnerInfo getPartnerInfo(String partnerToken) {
        // 1. partnerToken으로 partner를 읽어온다.
        // 2. partner -> partnerInfo로 리턴한다.
        Partner partner = partnerReader.getPartner(partnerToken);
        return new PartnerInfo(partner);
    }

    @Override
    @Transactional
    public PartnerInfo enablePartner(String partnerToken) {
        // 1. partnerToken으로 partner를 읽어온다.
        // 2. partner.enable
        Partner partner = partnerReader.getPartner(partnerToken);
        partner.enable();
        return new PartnerInfo(partner);
    }

    @Override
    @Transactional
    public PartnerInfo disablePartner(String partnerToken) {
        // 1. partnerToken으로 partner를 읽어온다.
        // 2. partner.enable
        Partner partner = partnerReader.getPartner(partnerToken);
        partner.disable();
        return new PartnerInfo(partner);
    }
}
