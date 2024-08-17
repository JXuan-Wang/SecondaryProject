package org.example.systemserver.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ActivityRemarkQuery extends BaseQuery{
    private Integer activityId;
    private String noteContent;
}
