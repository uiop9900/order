package dev.practice.order.domain.item;

import com.google.common.collect.Lists;
import dev.practice.order.common.exeption.InvalidParamException;
import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter @Entity
@NoArgsConstructor
@Table(name = "items")
public class Item extends AbstractEntity {

    private static final String PREFIX_ITEM = "item_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemToken;
    private Long partnerId;
    private String itemName;
    private Long itemPrice;


    // Item : ItemOptionGroup = 1 : N
    // mappedBy는 연관관계의 주인인 List에서, 선언한 Item과 매핑된다는 것을 선언. OntoMany하면 mappedBy 해줘야한다.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.PERSIST) // 하위객체에 생성 삭제에 따라 같이 생성 삭제된다.
    private List<ItemOptionGroup> itemOptionGroupList = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("판매준비중"),
        ON_SALES("판매중"),
        END_OF_SALES("판매종료");

        private final String description;
    }

    @Builder
    public Item(Long partnerId, String itemName, Long itemPrice) {
        if(partnerId == null) {throw new InvalidParamException(); }
        if(itemName == null) {throw new InvalidParamException(); }
        if(itemPrice == null) {throw new InvalidParamException(); }

        this.itemToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_ITEM);
        this.partnerId = partnerId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.status = Status.PREPARE;
    }

    public void changePrepare() {
        this.status = Status.PREPARE;
    }

    public void changeOnSales() {
        this.status = Status.ON_SALES;
    }

    public void changeEndOfSales() {
        this.status = Status.END_OF_SALES;
    }
}
