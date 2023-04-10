package store.infrastructure.database;

import org.springframework.stereotype.Component;
import store.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class DataBaseMapper {
        
        public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");
        
        public Map<String, ?> mapProduct(Product product) {
                return Map.of(
                        "product_code", product.getProductCode(),
                        "product_name", product.getProductName(),
                        "product_price", product.getProductPrice(),
                        "adults_only", product.getAdultsOnly(),
                        "description", product.getDescription(),
                        "producer_id", product.getProducer().getId()
                
                );
        }
        
        public Map<String, ?> mapPurchase(Purchase purchase) {
                return Map.of(
                        "customer_id", purchase.getCustomer().getId(),
                        "product_id", purchase.getProduct().getId(),
                        "quantity", purchase.getQuantity(),
                        "date_time", FORMAT.format(purchase.getDateTime())
                );
        }
        
        public Map<String, ?> mapOpinion(Opinion opinion) {
                return Map.of(
                        "customer_id", opinion.getCustomer().getId(),
                        "product_id", opinion.getProduct().getId(),
                        "stars", opinion.getStars(),
                        "comment", opinion.getComment(),
                        "date_time", FORMAT.format(opinion.getDateTime())
                
                );
        }
        
        
        @SuppressWarnings("unused")
        public Customer mapCustomer(ResultSet resultSet, int rowNumber) throws SQLException {
                return Customer.builder()
                               .id(resultSet.getLong("ID"))
                               .userName(resultSet.getString("user_name"))
                               .email(resultSet.getString("email"))
                               .name(resultSet.getString("name"))
                               .surname(resultSet.getString("surname"))
                               .dateOfBirth(LocalDate.parse(resultSet.getString("date_of_birth")))
                               .telephoneNumber(resultSet.getString("telephone_number"))
                               .build();
                
                
        }
        
        @SuppressWarnings("unused")
        Purchase mapPurchase2(ResultSet resultSet, int rowNum) throws SQLException {
                return Purchase.builder()
                               .id(resultSet.getLong("ID"))
                               .customer(Customer.builder().id(resultSet.getLong("CUSTOMER_ID")).build())
                               .product(Product.builder().id(resultSet.getLong("PRODUCT_ID")).build())
                               .quantity(resultSet.getInt("QUANTITY"))
                               .dateTime(OffsetDateTime.parse(resultSet.getString("DATE_TIME"), FORMAT).withOffsetSameInstant(ZoneOffset.UTC))
                               .build();
        }
        
        @SuppressWarnings("unused")
        public Opinion mapOpinion2(ResultSet resultSet, int rowNum) throws SQLException {
                return Opinion.builder()
               .id(resultSet.getLong("id"))
               .customer(Customer.builder().id(resultSet.getLong("customer_id")).build())
               .product(Product.builder().id(resultSet.getLong("product_id")).build())
               .stars(resultSet.getByte("stars"))
               .comment(resultSet.getString("comment"))
               .dateTime(OffsetDateTime.parse(resultSet.getString("date_time"),FORMAT).withOffsetSameInstant(ZoneOffset.UTC))
               .build();
        }
        
        public Producer mapProducer(ResultSet resultSet, int rowNum) throws SQLException {
                return Producer.builder()
                    .id(resultSet.getLong("id"))
                               .producerName(resultSet.getString("producer_name"))
                               .address(resultSet.getString("address"))
                               .build();
        }
        public Product mapProduct2(ResultSet resultSet, int rowNum) throws SQLException {
                return Product.builder()
                               .id(resultSet.getLong("id"))
                               .productCode(resultSet.getString("product_code"))
                .productName(resultSet.getString("product_name"))
                .productPrice(resultSet.getBigDecimal("product_price"))
                .adultsOnly(resultSet.getBoolean("adults_only"))
                .description(resultSet.getString("description"))
                .producer(Producer.builder().id(resultSet.getLong("producer_id")).build())
                         .build();
                
                
        }
}
