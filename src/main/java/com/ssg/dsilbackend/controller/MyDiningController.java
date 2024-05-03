package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.myDinig.MydiningReserveDTO;
import com.ssg.dsilbackend.service.MyDiningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/myDining")
@Log4j2
@RequiredArgsConstructor
public class MyDiningController {
    private final MyDiningService myDiningService;

    @GetMapping("/{id}")
    public List<MydiningReserveDTO> getMydiningListById(@PathVariable Long id){
        return myDiningService.getMydiningListById(id);
    }

}
