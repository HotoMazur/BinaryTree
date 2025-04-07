package org.example.spring.web.api;

import org.example.spring.service.BinaryTreeService;
import org.example.spring.web.model.BinaryTree;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/binarytree")
public class BinaryTreeApiController {

    private final BinaryTreeService binaryTreeService;

    public BinaryTreeApiController(BinaryTreeService binaryTreeService) {
        this.binaryTreeService = binaryTreeService;
    }

    @GetMapping
    public List<BinaryTree> getAll(){
        return this.binaryTreeService.getAllBinaryTrees();
    }

    @GetMapping("/{id}")
    public BinaryTree getRoom(@PathVariable UUID id){
        return this.binaryTreeService.getBinaryTreeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BinaryTree addBinaryTree(@RequestBody BinaryTree binaryTree){
        return this.binaryTreeService.addBinaryTree(binaryTree);
    }

    @PutMapping("/{id}")
    public BinaryTree updateBinaryTree(@PathVariable(name="id") UUID id, @RequestBody BinaryTree binaryTree){
        return this.binaryTreeService.updateBinaryTree(binaryTree);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable(name="id") UUID id){
        this.binaryTreeService.deleteBinaryTree(id);
    }
}
