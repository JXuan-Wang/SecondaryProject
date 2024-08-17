package org.example.systemserver.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseQuery {
    private String token;   // jwt

    private String filterSQL; // 数据权限的SQL过滤田间
}
