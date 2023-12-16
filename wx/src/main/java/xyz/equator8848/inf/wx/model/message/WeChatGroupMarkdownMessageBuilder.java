package xyz.equator8848.inf.wx.model.message;

import java.util.LinkedList;
import java.util.List;

public class WeChatGroupMarkdownMessageBuilder {
    private String title;

    private String description;

    private List<String> lines = new LinkedList<>();

    public WeChatGroupMarkdownMessageBuilder(String title) {
        this.title = title;
    }

    public WeChatGroupMarkdownMessageBuilder addDescription(String description) {
        this.description = description;
        return this;
    }

    public WeChatGroupMarkdownMessageBuilder addLine(String line) {
        if (line.endsWith("\n")) {
            this.lines.add(line);
        } else {
            this.lines.add(line + "\n");
        }
        return this;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("### ").append(title).append("\n");
        if (description != null) {
            sb.append("> ").append(description).append("\n\n");
        }
        for (String line : lines) {
            sb.append(line);
        }
        return sb.toString();
    }
}
