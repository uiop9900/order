package dev.practice.order.domain.item;

import dev.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final PartnerReader partnerReader;
    private final ItemStore itemStore;
    private final ItemReader itemReader;
    private final ItemOptionSeriesFactory itemOptionSeriesFactory;

    @Override
    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        // 1. 상품 등록시 필요한 partnerId를 가지고 온다.
        var partner = partnerReader.getPartner(partnerToken);
        // 2. 입력받은 item Command를 엔티티로 변환한 다음에 저장한다.
        var initItem = command.toEntity(partner.getId());
        var item = itemStore.store(initItem);
        // 2번 forEach 돌면서 OptionGroup과 Option이 저장되었음 -> Factory에 넘김.
        itemOptionSeriesFactory.store(command, item); // 연관관계 매핑으로 item이 생성되어야 나머지 ItemOptionGroup과 ItemOption이 저장이 가능하다.
        // 3. 반환받은 itemToken을 리턴한다.
        return item.getItemToken();

        // item은 따로 서비스단에서 엔티티로 만들고 저장하지만, 그 외에 option들은 따로 Factory를 통해서 엔티티를 만들고 저장한다.
        // 둘다 결국엔 엔티티로 만든는건데 왜 아이템은 따로 만들죠? 만드는 김에 같이 만들면 좋잖아요!
        // 추상화레벨이 너무 높아지면 이해하기 오히려 힘들다. 그래서 의도적으로 아이템은 도메인에서 저장하고 그 외의 option들은 factory에서 저장한다.
    }

    @Override
    @Transactional
    public void changeOnSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeOnSale();
    }

    @Override
    @Transactional
    public void changeEndOfSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeEndOfSale();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        var itemOptionGroupInfoList = itemReader.getItemOptionSeries(item);
        return new ItemInfo.Main(item, itemOptionGroupInfoList);
    }
}
