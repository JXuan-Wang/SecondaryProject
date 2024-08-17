package org.example.systemserver.result;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public enum DicEnum {

    APPELLATION("appellation"),

    SOURCE("source"),

    STATE("clueState"),

    INTENTIONSTATE("intentionState"),

    NEEDLOAN("needLoan"),

    PRODUCT("product"),

    ACTIVITY("activity")

    ;

    @Setter
    @Getter
    private String code;
}
