package com.sunnyhsu.springsecuritystudy.controller.movie;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @RequestMapping("/getMovies")
    public String getMovies() {
        return "取得電影列表";
    }

    @RequestMapping("/watchFreeMovies")
    public String watchFreeMovie() {
        return "觀看免費電影";
    }

    @RequestMapping("/watchVipMovies")
    public String watchVipMovie() {
        return "觀看 VIP 付費電影";
    }

    @RequestMapping("/uploadMovies")
    public String uploadMovie() {
        return "上傳新電影";
    }

    @RequestMapping("/deleteMovies")
    public String deleteMovie() {
        return "刪除電影";
    }

    @PostMapping("/createMovie")
    public String createMovie() {
        return "新增電影";
    }

    @PutMapping("/updateMovie")
    public String updateMovie() {
        return "修改電影";
    }
}

