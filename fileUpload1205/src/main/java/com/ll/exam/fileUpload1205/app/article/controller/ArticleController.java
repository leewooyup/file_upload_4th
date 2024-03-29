package com.ll.exam.fileUpload1205.app.article.controller;

import com.ll.exam.fileUpload1205.app.article.controller.input.ArticleForm;
import com.ll.exam.fileUpload1205.app.article.entity.Article;
import com.ll.exam.fileUpload1205.app.article.service.ArticleService;
import com.ll.exam.fileUpload1205.app.base.dto.RsData;
import com.ll.exam.fileUpload1205.app.fileUpload.entity.GenFile;
import com.ll.exam.fileUpload1205.app.fileUpload.service.GenFileService;
import com.ll.exam.fileUpload1205.app.member.entity.Member;
import com.ll.exam.fileUpload1205.app.security.dto.MemberContext;
import com.ll.exam.fileUpload1205.app.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/article")
@Slf4j
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final GenFileService genFileService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/write")
    public String showWrite() {
        return "article/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid ArticleForm articleForm, BindingResult bindingResult, MultipartRequest multipartRequest) {
        if(bindingResult.hasErrors()) {
            return "article/write";
        }

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        log.debug("fileMap: " + fileMap);

        Article article = articleService.write(memberContext.getId(), articleForm.getSubject(), articleForm.getContent());
        RsData<Map<String, GenFile>> saveFilesRsData = genFileService.saveFiles(article, fileMap);
        log.debug("saveFilesRsData:" + saveFilesRsData);
        String msg = "%d번 게시물이 작성되었습니다.".formatted(article.getId());
        msg = Util.url.encode(msg);
        return "redirect:/article/%d?msg=%s".formatted(article.getId(), msg);
    }

    @GetMapping("/{id}")
    public String showDetail(Model model, @PathVariable Long id) {
        Article article = articleService.getForPrintArticleById(id);
        model.addAttribute("article", article);

        return "article/detail";
    }

    @GetMapping("/{id}/json/forDebug")
    @ResponseBody
    public Article showDetailJson(Model model, @PathVariable Long id) {
        return articleService.getForPrintArticleById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, Model model, @PathVariable Long id) {
        Article article = articleService.getForPrintArticleById(id);
        if(memberContext.memberIsNot(article.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("article", article);

        return "article/modify";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, Model model, @PathVariable Long id, @Valid ArticleForm articleForm) {
        Article article = articleService.getForPrintArticleById(id);

        if(memberContext.memberIsNot(article.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        articleService.modify(article, articleForm.getSubject(), articleForm.getContent());

        String msg = Util.url.encode("%d번 게시물이 수정되었습니다.".formatted(id));
        return "redirect:/article/%d?msg=%s".formatted(id, msg);
    }

}
