package com.Tetraedr;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

public interface ArticleService {

    void saveArticle(Article article);

    Article getArticleById(long id);

    @Service
    static class DefaultArticleService implements ArticleService {
        private Map<Long, Article> articleMap = new HashMap<>();

        @Override
        public void saveArticle(Article article) {
            articleMap.put(article.getId(), article);
        }

        @Override
        public Article getArticleById(long id) {
            return articleMap.get(id);
        }
    }
}