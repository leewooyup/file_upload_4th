package com.ll.exam.fileUpload1205.app.base;

import com.ll.exam.fileUpload1205.app.article.entity.Article;
import com.ll.exam.fileUpload1205.app.article.service.ArticleService;
import com.ll.exam.fileUpload1205.app.member.entity.Member;
import com.ll.exam.fileUpload1205.app.member.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev") // 이 클래스에 정의된 Bean 들은 dev 모드에서만 활성화된다.
public class DevInitData {
    // CommandLineRunner: 앱 실행 직후 초기데이터 세팅 및 초기화에 사용된다.
    @Bean
    CommandLineRunner init(MemberService memberService, ArticleService articleService, PasswordEncoder passwordEncoder) {
        return args -> {
            String password = passwordEncoder.encode("1234");
            Member member1 = memberService.join("user1", password, "user1@test.com");
            memberService.setProfileImgByUrl(member1, "https://picsum.photos/200/300");

            Member member2 = memberService.join("user2", password, "user2@test.com");
            memberService.setProfileImgByUrl(member2, "https://picsum.photos/200/300");

            Article article1 = articleService.write(member1, "제목", "내용");
            articleService.addGenFileByUrl(article1, "common", "inBody", 1, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article1, "common", "inBody", 2, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article1, "common", "inBody", 3, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article1, "common", "inBody", 4, "https://picsum.photos/200/300");

            Article article2 = articleService.write(member2, "제목", "내용");
            articleService.addGenFileByUrl(article2, "common", "inBody", 1, "https://picsum.photos/200/300");
            articleService.addGenFileByUrl(article2, "common", "inBody", 2, "https://picsum.photos/200/300");
        };
    }
}
