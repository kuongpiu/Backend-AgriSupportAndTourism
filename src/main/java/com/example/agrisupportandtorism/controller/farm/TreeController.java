package com.example.agrisupportandtorism.controller.farm;

import com.example.agrisupportandtorism.entity.farm.Tree;
import com.example.agrisupportandtorism.service.farm.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:9528/")
@RequestMapping("/tree")
public class TreeController{
    @Autowired
    private TreeService treeService;

    @GetMapping("/all")
    public List<Tree> findAll(){
        return treeService.findAll();
    }

    @GetMapping("/search")
    public List<Tree> findByName(@RequestParam String name){
        return treeService.findByName(name);
    }
    @GetMapping
    public Tree findTreeById(@RequestParam Integer id){
        return treeService.findById(id);
    }

    @PostMapping
    public Tree insertTree(@RequestBody @Valid Tree tree){
        return treeService.insertTree(tree);
    }
}
