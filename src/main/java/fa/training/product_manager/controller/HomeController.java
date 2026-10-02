package fa.training.product_manager.controller;

import fa.training.product_manager.entity.Product;
import fa.training.product_manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${info.app.version:0.0.1-SNAPSHOT}")
    private String appVersion;

    private final ProductService productService;

    @GetMapping({"", "/"})
    public String home(Model model) {
        // ── App info ──────────────────────────────────────────────
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("serverPort", serverPort);

        // ── Server info ───────────────────────────────────────────
        model.addAttribute("javaVersion", System.getProperty("java.version"));
        model.addAttribute("osName", System.getProperty("os.name"));
        model.addAttribute("osArch", System.getProperty("os.arch"));

        try {
            model.addAttribute("hostname", InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            model.addAttribute("hostname", "Unknown");
        }

        // ── Uptime ────────────────────────────────────────────────
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        long hours   = TimeUnit.MILLISECONDS.toHours(uptimeMs);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMs) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMs) % 60;
        model.addAttribute("uptime", String.format("%02dh %02dm %02ds", hours, minutes, seconds));

        // ── Memory ────────────────────────────────────────────────
        Runtime runtime = Runtime.getRuntime();
        long totalMemMb = runtime.totalMemory() / (1024 * 1024);
        long freeMemMb  = runtime.freeMemory()  / (1024 * 1024);
        long usedMemMb  = totalMemMb - freeMemMb;
        model.addAttribute("memUsed",    usedMemMb  + " MB");
        model.addAttribute("memTotal",   totalMemMb + " MB");
        model.addAttribute("memPercent", (int) ((usedMemMb * 100.0) / totalMemMb));

        // ── Timestamp ─────────────────────────────────────────────
        model.addAttribute("serverTime",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        // ── Product stats ─────────────────────────────────────────
        List<Product> products = productService.findAll();
        long totalProducts  = products.size();
        long outOfStock     = products.stream().filter(p -> p.getStock() == 0).count();
        long inStock        = totalProducts - outOfStock;
        BigDecimal totalValue = products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getStock())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("inStock",       inStock);
        model.addAttribute("outOfStock",    outOfStock);
        model.addAttribute("totalValue",    totalValue);

        return "home";
    }
}