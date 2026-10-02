package fa.training.product_manager.controller;

import fa.training.product_manager.entity.Product;
import fa.training.product_manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /** Danh sách products */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list";
    }

    /** Hiển thị form thêm mới */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }

    /** Xử lý lưu (thêm mới hoặc cập nhật) */
    @PostMapping("/save")
    public String save(@ModelAttribute Product product, RedirectAttributes ra) {
        productService.save(product);
        ra.addFlashAttribute("successMsg", "Lưu sản phẩm thành công!");
        return "redirect:/products";
    }

    /** Hiển thị form chỉnh sửa */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return productService.findById(id)
                .map(p -> {
                    model.addAttribute("product", p);
                    return "products/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("successMsg", "Không tìm thấy sản phẩm #" + id);
                    return "redirect:/products";
                });
    }

    /** Xem chi tiết sản phẩm */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return productService.findById(id)
                .map(p -> {
                    BigDecimal stockValue = p.getPrice()
                            .multiply(BigDecimal.valueOf(p.getStock()));
                    model.addAttribute("product", p);
                    model.addAttribute("stockValue", stockValue);
                    return "products/detail";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("successMsg", "Không tìm thấy sản phẩm #" + id);
                    return "redirect:/products";
                });
    }

    /** Xóa product */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        productService.deleteById(id);
        ra.addFlashAttribute("successMsg", "Đã xóa sản phẩm!");
        return "redirect:/products";
    }
}
