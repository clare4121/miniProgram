package com.minprogram.interaction.service;

import com.minprogram.article.repository.ArticleRepository;
import com.minprogram.interaction.repository.CommentRepository;
import com.minprogram.interaction.repository.FavoriteRepository;
import com.minprogram.interaction.repository.LikeRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteractionCounterService {
    private final LikeRecordRepository likeRecordRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public InteractionCounterService(LikeRecordRepository likeRecordRepository,
                                     FavoriteRepository favoriteRepository,
                                     CommentRepository commentRepository,
                                     ArticleRepository articleRepository) {
        this.likeRecordRepository = likeRecordRepository;
        this.favoriteRepository = favoriteRepository;
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void refreshCounts(Integer targetType, Long targetId) {
        if (!Integer.valueOf(1).equals(targetType)) {
            return;
        }
        var article = articleRepository.findById(targetId).orElse(null);
        if (article == null) {
            return;
        }
        int likeCount = (int) likeRecordRepository.countByTargetTypeAndTargetId(targetType, targetId);
        int favoriteCount = (int) favoriteRepository.countByTargetTypeAndTargetId(targetType, targetId);
        int commentCount = (int) commentRepository.countByTargetTypeAndTargetIdAndStatus(targetType, targetId, 1);
        article.setLikeCount(likeCount);
        article.setFavoriteCount(favoriteCount);
        article.setCommentCount(commentCount);
        articleRepository.save(article);
    }
}
