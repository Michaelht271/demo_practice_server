package fa.training.product_manager.config;

import fa.training.product_manager.entity.Product;
import fa.training.product_manager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInit {

    private final ProductRepository productRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Chỉ seed nếu bảng products chưa có dữ liệu
            if (productRepository.count() > 0) {
                log.info("Database already has data — skipping DataInit.");
                return;
            }

            List<Product> products = List.of(
                    Product.builder()
                            .name("Laptop Dell XPS 15")
                            .description("Laptop cao cấp dành cho dân sáng tạo, chip Intel Core i9, RAM 32GB, SSD 1TB.")
                            .price(new BigDecimal("45990000"))
                            .stock(10)
                            .build(),

                    Product.builder()
                            .name("iPhone 16 Pro Max")
                            .description("Điện thoại Apple mới nhất với chip A18 Pro, camera 48MP, màn hình OLED 6.9\".")
                            .price(new BigDecimal("34990000"))
                            .stock(25)
                            .build(),

                    Product.builder()
                            .name("Samsung Galaxy S25 Ultra")
                            .description("Android flagship với bút S Pen tích hợp, RAM 12GB, bộ nhớ 256GB.")
                            .price(new BigDecimal("31990000"))
                            .stock(30)
                            .build(),

                    Product.builder()
                            .name("Tai nghe Sony WH-1000XM5")
                            .description("Tai nghe chống ồn hàng đầu, pin 30 giờ, kết nối Bluetooth 5.2.")
                            .price(new BigDecimal("8490000"))
                            .stock(50)
                            .build(),

                    Product.builder()
                            .name("Bàn phím cơ Keychron Q1 Pro")
                            .description("Bàn phím 75%, switch Gateron Pro, hotswap, kết nối có dây và Bluetooth.")
                            .price(new BigDecimal("3990000"))
                            .stock(40)
                            .build(),

                    Product.builder()
                            .name("Màn hình LG UltraWide 34\"")
                            .description("Màn hình cong 34 inch, độ phân giải 3440×1440, tần số 160Hz, tấm nền IPS.")
                            .price(new BigDecimal("12990000"))
                            .stock(15)
                            .build(),

                    Product.builder()
                            .name("Chuột Logitech MX Master 3S")
                            .description("Chuột không dây cao cấp, cuộn từ tính MagSpeed, pin 70 ngày, kết nối Bolt.")
                            .price(new BigDecimal("2490000"))
                            .stock(60)
                            .build(),

                    Product.builder()
                            .name("iPad Pro M4 11\"")
                            .description("Máy tính bảng Apple chip M4, màn hình OLED 11 inch, hỗ trợ Apple Pencil Pro.")
                            .price(new BigDecimal("27990000"))
                            .stock(20)
                            .build(),

                    Product.builder()
                            .name("Ổ cứng SSD Samsung 970 EVO Plus 1TB")
                            .description("SSD NVMe M.2, tốc độ đọc 3500 MB/s, bảo hành 5 năm.")
                            .price(new BigDecimal("1990000"))
                            .stock(80)
                            .build(),

                    Product.builder()
                            .name("Webcam Logitech StreamCam")
                            .description("Camera Full HD 1080p/60fps, kết nối USB-C, tự động lấy nét AI.")
                            .price(new BigDecimal("2190000"))
                            .stock(35)
                            .build()
            );

            productRepository.saveAll(products);
            log.info("DataInit: Đã chèn {} sản phẩm mẫu vào database.", products.size());
        };
    }
}
