package store.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class StoreFixtures {
        public static  Customer someCustomer()
        {
                return Customer.builder()
                               .userName("usermarek")
                               .email("marekgmail.com")
                               .name("marek")
                               .surname("kowal")
                               .dateOfBirth(LocalDate.of(1990,3,12))
                               .telephoneNumber("+43534324")
                               .build();
        }
        
        public static Opinion someOpinion(Customer customer, Product product) {
                return Opinion.builder()
                        
                               .customer(customer)
                               .product(product)
                               .stars((byte)4)
                               .comment("my comment")
                               .dateTime(OffsetDateTime.of(2020, 1, 4, 12, 44, 30, 0, ZoneOffset.ofHours(5)))
                               .build();
        }
        
        public static Producer someProducer() {
                return Producer.builder()
                
                               .producerName("someproducer")
                               .address("someaddress")
                               .build();
        }
        public static Product someProduct1(Producer producer) {
                return Product.builder()
                
                               .productCode("code1")
                               .productName("someproduct1")
                               .productPrice(BigDecimal.valueOf(114,23))
                               .adultsOnly(false)
                               .description("somedescription1")
                               .producer(producer)
                               .build();
        }
        
        public static Product someProduct2(Producer producer) {
                return Product.builder()
                        
                               .productCode("code2")
                               .productName("someproduct2")
                               .productPrice(BigDecimal.valueOf(214,23))
                               .adultsOnly(false)
                               .description("somedescription2")
                               .producer(producer)
                               .build();
        }
        
        public static Purchase somePurchase(Customer customer, Product product) {
                return Purchase.builder()
                
                               .customer(customer)
                               .product(product)
                               .quantity(4)
                               .dateTime(OffsetDateTime.of(2020, 1, 3, 1, 5, 2, 0, ZoneOffset.ofHours(4)))
                               .build();
        }
        
}
