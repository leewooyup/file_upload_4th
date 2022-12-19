package com.ll.exam.fileUpload1205.app.home.controller;

import com.ll.exam.fileUpload1205.app.member.entity.Member;
import com.ll.exam.fileUpload1205.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping("/")
    public String showMain() {
        return "home/main";
    }

    @RequestMapping("/test/upload")
    public String upload() {
        return "home/test/upload";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "home/about";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/currentUserOrigin")
    @ResponseBody
    public Principal currentUserOrigin(Principal principal) {
        return principal;
    }
}
