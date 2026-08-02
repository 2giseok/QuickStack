package com.project.crawler.util;

import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import com.project.crawler.repositroy.TechBlogRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Profile("local")
@Slf4j
public class MockDataController {

    private final TechBlogRepository techBlogRepository;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/mock/{count}")
    public String insertMockData(@PathVariable int count) {
        List<TechBlog> mockList = new ArrayList<>();

        LocalDateTime localDateTime = LocalDateTime.now().minusHours(4);
        String testUrl = "https://example.com";
        for(int i=0; i<count ; i++) {
            Long customId = System.nanoTime() + i;
            TechBlog blog = TechBlog.builder()
                    .id(customId)
                    .url(testUrl+ "/?id="+i)
                    .status(Status.PENDING)
                    .publishedAt(localDateTime)
                    .build();
            mockList.add(blog);
        }
        techBlogRepository.saveAll(mockList);
        return count + "건의 더미 데이터 삽입 완료";
    }

}
