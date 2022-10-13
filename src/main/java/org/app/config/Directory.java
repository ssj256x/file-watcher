package org.app.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Directory {
    private List<String> categories;
    private String move;
    private List<String> track;
}