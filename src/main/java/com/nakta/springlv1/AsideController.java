package com.nakta.springlv1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AsideController {
    @GetMapping("/seller") // 상품관리페이지
    public String showStore(Model model) {
        return "store"; // 상품 관리 페이지로 이동
    }
}
