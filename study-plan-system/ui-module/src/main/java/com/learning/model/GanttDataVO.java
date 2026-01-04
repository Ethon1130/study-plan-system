package com.learning.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GanttDataVO {
    private String taskName;
    private String start;
    @JsonProperty("end")
    private String end;
    private Integer status;
}
