package org.example.spring.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BinaryTree {
    private UUID id;
    private String tree_type;
    private Integer value;
    private Integer height;
    private String color;
    private UUID parent_id;
    private UUID left_id;
    private UUID right_id;
}
