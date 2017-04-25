package com.ae.upwork.multithreadtest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Service response
 *
 * @author AEsik
 * Date 25.04.2017
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceResponse {

    private Long userId;

    private Long id;

    private String title;

    private String body;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
