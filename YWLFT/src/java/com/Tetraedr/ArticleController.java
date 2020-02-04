package com.Tetraedr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/popup")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String getArticleForm() {
        return "popup";
    }

    @PutMapping("/{id}")
    @ResponseBody
    public String createNewArticle(@RequestBody Article article) {
        articleService.saveArticle(article);
        System.out.println(article.toString());
        System.out.println("\n\n\n\n");
        return "Article created.";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable("id") long id, Model model) {
        Article article = articleService.getArticleById(id);
        System.out.println(article.toString());
        System.out.println("\n\n\n");
        model.addAttribute("article", article);
        return "article-page";
    }
}