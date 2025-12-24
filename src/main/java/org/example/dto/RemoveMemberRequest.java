package org.example.dto;

import lombok.Data;

@Data
public class RemoveMemberRequest {
    private Long projectId;
    private Long researcherId;
}
