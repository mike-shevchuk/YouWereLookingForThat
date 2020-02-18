package com.Tetraedr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String getArticleForm() {
        System.out.println("getArticleForm()");
        return "popup.html";
    }

    //chrome-extension://hglecnfpombpokcccgpeapcceoekmjfb/popup.html#
    //@PutMapping("/{id}")
    //@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    //
    @PostMapping("/{id}")
    @ResponseBody
    public String postJson(@ModelAttribute Article article) {
        System.out.println(article.getContent());
        System.out.println(article.toString());
        articleService.saveArticle(article);
        System.out.println("added");
        return "Article created.";
    }
    /*public String createNewArticle(@RequestBody Article article) {
        System.out.println("createNewArticle()");
        articleService.saveArticle(article);
        System.out.println(article.toString());
        System.out.println("\n\n\n\n");
        return "Article created.";
    }*/
/*
    @GetMapping("/{id}")
    public String getArticle(@PathVariable("id") long id, Model model) {
        System.out.println("getArticle()");
        Article article = articleService.getArticleById(id);
        System.out.println(article.toString());
        System.out.println("\n\n\n");
        model.addAttribute("article", article);
        return "article-page";
    }
 */
}