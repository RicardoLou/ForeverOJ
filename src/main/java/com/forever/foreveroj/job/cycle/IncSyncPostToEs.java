package com.forever.foreveroj.job.cycle;

import com.forever.foreveroj.esdao.PostEsDao;
import com.forever.foreveroj.mapper.PostMapper;
import com.forever.foreveroj.model.dto.post.PostEsDTO;
import com.forever.foreveroj.model.entity.Post;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 增量同步帖子到 es
 *
 * @author <a href="https://github.com/RicardoLou">RicardoLou</a>
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class IncSyncPostToEs {

    @Resource
    private PostMapper postMapper;

    @Resource
    private PostEsDao postEsDao;

    /**
     * 每分钟执行一次
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 查询近 5 分钟内的数据
        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
        List<Post> postList = postMapper.listPostWithDelete(fiveMinutesAgoDate);
        if (CollectionUtils.isEmpty(postList)) {
            log.info("no inc post");
            return;
        }
        List<PostEsDTO> postEsDTOList = postList.stream()
                .map(PostEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = postEsDTOList.size();
        log.info("IncSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            postEsDao.saveAll(postEsDTOList.subList(i, end));
        }
        log.info("IncSyncPostToEs end, total {}", total);
    }
}
