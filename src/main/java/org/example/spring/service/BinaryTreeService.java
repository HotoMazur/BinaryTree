package org.example.spring.service;

import org.example.spring.data.entity.BinaryTreeEntity;
import org.example.spring.data.repository.BinaryTreeRepository;
import org.example.spring.web.model.BinaryTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BinaryTreeService {
    private final BinaryTreeRepository binaryTreeRepository;

    public BinaryTreeService(BinaryTreeRepository binaryTreeRepository) {
        this.binaryTreeRepository = binaryTreeRepository;
    }

    public List<BinaryTree> getAllBinaryTrees() {
        List<BinaryTreeEntity> binaryTreeEntities = this.binaryTreeRepository.findAll();
        List<BinaryTree> binaryTrees = new ArrayList<>(binaryTreeEntities.size());
        binaryTreeEntities.forEach(binaryTreeEntity -> binaryTrees.add(getBinaryTreeFromEntity(binaryTreeEntity)));
        return binaryTrees;
    }

    public BinaryTree getBinaryTreeById(UUID id) {
        Optional<BinaryTreeEntity> binaryTreeEntity = binaryTreeRepository.findById(id);
        if (binaryTreeEntity.isEmpty()) {
            return null;
        } else{
            return this.getBinaryTreeFromEntity(binaryTreeEntity.get());
        }
    }

    public BinaryTree addBinaryTree(BinaryTree binaryTree) {
        BinaryTreeEntity binaryTreeEntity = this.getBinaryTreeEntityFromBinaryTree(binaryTree);
        BinaryTreeEntity savedBinaryTreeEntity = this.binaryTreeRepository.save(binaryTreeEntity);
        return this.getBinaryTreeFromEntity(savedBinaryTreeEntity);
    }

    public BinaryTree updateBinaryTree(BinaryTree binaryTree){
        BinaryTreeEntity binaryTreeEntity = this.getBinaryTreeEntityFromBinaryTree(binaryTree);
        BinaryTreeEntity updatedBinaryTreeEntity = binaryTreeRepository.save(binaryTreeEntity);
        return this.getBinaryTreeFromEntity(updatedBinaryTreeEntity);
    }

    public void deleteBinaryTree(UUID id) {
        binaryTreeRepository.deleteById(id);
    }

    private BinaryTree getBinaryTreeFromEntity(BinaryTreeEntity binaryTreeEntity) {
        return new BinaryTree(binaryTreeEntity.getId(), binaryTreeEntity.getTree_type(), binaryTreeEntity.getValue(), binaryTreeEntity.getHeight(), binaryTreeEntity.getColor(), binaryTreeEntity.getParent_id(), binaryTreeEntity.getLeft_id(), binaryTreeEntity.getRight_id());
    }

    private BinaryTreeEntity getBinaryTreeEntityFromBinaryTree(BinaryTree binaryTree) {
        return new BinaryTreeEntity(binaryTree.getId(), binaryTree.getTree_type(), binaryTree.getValue(), binaryTree.getHeight(), binaryTree.getColor(), binaryTree.getParent_id(), binaryTree.getLeft_id(), binaryTree.getRight_id());
    }
}
