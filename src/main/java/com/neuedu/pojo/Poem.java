package com.neuedu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Poem {
    private String title;
    private String poet;
    private String context;
    private String transformation;//翻译
    private String evaluate;
    private String time;
}
