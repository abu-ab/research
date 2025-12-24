package org.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddMembersRequest {
    private Long projectId;
    private List<Long> researcherIds;
    private String role; // 可选
}