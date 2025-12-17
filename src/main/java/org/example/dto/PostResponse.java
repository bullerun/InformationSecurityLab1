package org.example.dto;

import java.time.LocalDateTime;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public PostResponse() {
    }

    public PostResponse(Long id, String title, String content, String author, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    public static PostResponseBuilder builder() {
        return new PostResponseBuilder();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class PostResponseBuilder {
        private Long id;
        private String title;
        private String content;
        private String author;
        private LocalDateTime createdAt;

        public PostResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostResponseBuilder author(String author) {
            this.author = author;
            return this;
        }

        public PostResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostResponse build() {
            return new PostResponse(id, title, content, author, createdAt);
        }
    }
}
