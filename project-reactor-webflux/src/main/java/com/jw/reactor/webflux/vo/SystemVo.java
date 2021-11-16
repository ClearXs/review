package com.jw.reactor.webflux.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data(staticConstructor = "of")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SystemVo implements Serializable {

    private String id;

    private String name;

    public static SystemVo of(String id) {
        return of(id, "");
    }
}
