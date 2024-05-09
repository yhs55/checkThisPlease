package com.ssg.dsilbackend.controller;

import com.ssg.dsilbackend.dto.Inform.InformDTO;
import com.ssg.dsilbackend.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/informs")
public class InformController {

    @Autowired
    private InformService informService;

    // 공지사항 생성
    @PostMapping("/")
    public ResponseEntity<InformDTO> createInform(@RequestBody InformDTO informDTO) {
        InformDTO newInform = informService.createInform(informDTO);
        return ResponseEntity.ok(newInform);
    }

    // 전체 공지사항 조회
    @GetMapping("/")
    public ResponseEntity<List<InformDTO>> getAllInforms() {
        List<InformDTO> informs = informService.getAllInforms();
        return ResponseEntity.ok(informs);
    }

    // 특정 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<InformDTO> getInformById(@PathVariable Long id) {
        InformDTO inform = informService.getInformById(id);
        return ResponseEntity.ok(inform);
    }

    // 공지사항 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<InformDTO> updateInform(@PathVariable Long id, @RequestBody InformDTO informDTO) {
        InformDTO updatedInform = informService.updateInform(id, informDTO);
        return ResponseEntity.ok(updatedInform);
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInform(@PathVariable Long id) {
        informService.deleteInform(id);
        return ResponseEntity.noContent().build();
    }
}
