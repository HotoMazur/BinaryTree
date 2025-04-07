package org.example.spring.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "binary_tree_nodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinaryTreeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String tree_type;
    private Integer value;
    private Integer height;
    private String color;
    private UUID parent_id;
    private UUID left_id;
    private UUID right_id;
}
