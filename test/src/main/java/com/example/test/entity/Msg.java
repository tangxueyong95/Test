package com.example.test.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Msg implements Serializable {
    private static final long serialVersionUID = 2569194276749276661L;
    private String code;
    private List<Object> data;
    private String msg;

    public Msg(String code, String msg) {
        this(code,null,msg);
    }

    public Msg(String code, List<Object> data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
