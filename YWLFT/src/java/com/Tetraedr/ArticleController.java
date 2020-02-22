package com.Tetraedr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    //@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json") //!SyntaxError: Unexpected token A in JSON at position 0
    //@PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")//! Error 415.
    //@PostMapping( "/{id}")    //! catch null value
    @PostMapping(value = "/{content}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postJson(@ModelAttribute Article article) {
        //public String postJson(@RequestBody Article article) {   //!Error415
        System.out.println(article.getContent());
        System.out.println("_______________________________");
        // System.out.println(article.toString());
        // articleService.saveArticle(article);
        //System.out.println("added");
        return "Article created.";
    }

}