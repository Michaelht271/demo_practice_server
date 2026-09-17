package fa.training.demo_deploy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Controller
public class HomeController {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${info.app.version:0.0.1-SNAPSHOT}")
    private String appVersion;

    @GetMapping({"", "/"})
    public String home(Model model) {
        // App info
        model.addAttribute("appName", appName);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("serverPort", serverPort);

        // Server info
        model.addAttribute("javaVersion", System.getProperty("java.version"));
        model.addAttribute("osName", System.getProperty("os.name"));
        model.addAttribute("osArch", System.getProperty("os.arch"));

        // Hostname
        try {
            model.addAttribute("hostname", InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            model.addAttribute("hostname", "Unknown");
        }

        // Uptime
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        long hours   = TimeUnit.MILLISECONDS.toHours(uptimeMs);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMs) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMs) % 60;
        model.addAttribute("uptime", String.format("%02dh %02dm %02ds", hours, minutes, seconds));

        // Memory
        Runtime runtime = Runtime.getRuntime();
        long totalMemMb = runtime.totalMemory() / (1024 * 1024);
        long freeMemMb  = runtime.freeMemory()  / (1024 * 1024);
        long usedMemMb  = totalMemMb - freeMemMb;
        model.addAttribute("memUsed",  usedMemMb  + " MB");
        model.addAttribute("memTotal", totalMemMb + " MB");
        model.addAttribute("memPercent", (int) ((usedMemMb * 100.0) / totalMemMb));

        // Timestamp
        model.addAttribute("serverTime",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        return "home";
    }
}