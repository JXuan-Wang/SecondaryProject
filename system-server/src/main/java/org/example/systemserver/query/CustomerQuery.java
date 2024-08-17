package org.example.systemserver.query;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerQuery extends BaseQuery {

    private Integer clueId;

    private Integer product;

    private String description;

    private Date nextContactTime;
}
