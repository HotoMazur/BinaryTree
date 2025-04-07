package org.example.spring.data.repository;

import org.example.spring.data.entity.BinaryTreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BinaryTreeRepository extends JpaRepository<BinaryTreeEntity, UUID> {
}
