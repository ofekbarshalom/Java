package com.securefromscratch.busybee.safety;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DescriptionTest {

    @Test
    public void allowedHtmlIsPreserved() {
        String input = "This is <b>bold</b> and <i>italic</i>. " +
                "See <a href=\"https://example.com\">docs</a>. " +
                "<img src=\"/image?img=pic.png\" alt=\"pic\">";
        Description d = new Description(input);
        String out = d.get();
        assertTrue(out.contains("<b>") && out.contains("</b>"), "bold tag should be preserved");
        assertTrue(out.contains("<i>") && out.contains("</i>"), "italic tag should be preserved");
        assertTrue(out.contains("https://example.com"), "link href should be preserved");
        assertTrue(out.contains("<img"), "img tag should be preserved");
    }

    @Test
    public void scriptTagIsRemoved() {
        String input = "Hello <script>alert('x')</script><b>ok</b>";
        Description d = new Description(input);
        String out = d.get();
        assertFalse(out.toLowerCase().contains("<script"), "script tags should be removed");
        assertTrue(out.contains("<b>ok</b>"), "allowed tags should remain");
    }

    @Test
    public void javascriptHrefIsBlocked() {
        String input = "Click <a href=\"javascript:alert(1)\">here</a> and <a href=\"mailto:me@example.com\">mail</a>";
        Description d = new Description(input);
        String out = d.get();
        assertFalse(out.toLowerCase().contains("javascript:"), "javascript: URIs must be blocked");
        assertTrue(out.contains("mailto:me@example.com") || out.contains("mail"), "mailto should be allowed");
    }

    @Test
    public void imageSrcRelativeOrHttpAllowed() {
        String input = "<img src=\"/uploads/pic.png\"> and <img src=\"https://example.com/p.png\">";
        Description d = new Description(input);
        String out = d.get();
        assertTrue(out.contains("/uploads/pic.png") || out.contains("https://example.com/p.png"), "image src should be allowed for relative and http(s)");
    }

    @Test
    public void emptyDescriptionThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Description(""));
    }

    @Test
    public void tooLongDescriptionThrows() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Description.MAX_LENGTH + 10; ++i) sb.append('a');
        assertThrows(IllegalArgumentException.class, () -> new Description(sb.toString()));
    }
}
