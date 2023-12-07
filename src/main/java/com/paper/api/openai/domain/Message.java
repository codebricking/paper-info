package com.paper.api.openai.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message implements Serializable {
    private @NotNull String role;
    private String content;
    private String name;

    public static Builder builder() {
        return new Builder();
    }

    public Message(String role, String content, String name) {
        this.role = role;
        this.content = content;
        this.name = name;
    }

    public Message() {
    }

    private Message(Builder builder) {
        this.setRole(builder.role);
        this.setContent(builder.content);
        this.setName(builder.name);
    }

    public @NotNull String getRole() {
        return this.role;
    }

    public String getContent() {
        return this.content;
    }

    public String getName() {
        return this.name;
    }

    public void setRole(@NotNull String role) {
        this.role = role;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Message)) {
            return false;
        } else {
            Message other = (Message)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$role = this.getRole();
                    Object other$role = other.getRole();
                    if (this$role == null) {
                        if (other$role == null) {
                            break label47;
                        }
                    } else if (this$role.equals(other$role)) {
                        break label47;
                    }

                    return false;
                }

                Object this$content = this.getContent();
                Object other$content = other.getContent();
                if (this$content == null) {
                    if (other$content != null) {
                        return false;
                    }
                } else if (!this$content.equals(other$content)) {
                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Message;
    }

    public int hashCode() {
        int result = 1;
        Object $role = this.getRole();
        result = result * 59 + ($role == null ? 43 : $role.hashCode());
        Object $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "Message(role=" + this.getRole() + ", content=" + this.getContent() + ", name=" + this.getName() + ")";
    }

    public static final class Builder {
        private @NotNull String role;
        private String content;
        private String name;

        public Builder() {
        }

        public Builder role(@NotNull Role role) {
            this.role = role.getName();
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }

    public static enum Role {
        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant");

        private String name;

        public String getName() {
            return this.name;
        }

        private Role(String name) {
            this.name = name;
        }
    }
}
