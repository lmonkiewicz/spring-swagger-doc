package com.lmonkiewicz.example.springSwaggerDoc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lmonkiewicz on 2017-02-10.
 */
@ApiModel("Note data")
public class Note {

    @ApiModelProperty(value = "ID of a note", example = "1", readOnly = true)
    private Integer id;

    @ApiModelProperty(value = "Title of note", example = "My important note!")
    private String title;

    @ApiModelProperty(value = "Content of note", example = "Buy milk", required = true)
    private String content;

    @ApiModelProperty("Is note marked as done?")
    private boolean done = false;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
