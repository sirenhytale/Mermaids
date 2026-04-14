package plugin.siren.Contributions.k3kdude;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;

public class DiscordWebhook {

    private final List<EmbedObject> embeds = new ArrayList<>();

    private final String url;

    private String content, username, avatarUrl;
    private boolean tts;

    public DiscordWebhook(String username, String url) {
        this.url = url;

        this.content = "";
        this.username = username;
        this.avatarUrl = "https://cdn.discordapp.com/avatars/1463994388890783958/78b0b6671bb2a3fea9b1e4bcd274d4ac.webp?size=128";
        this.tts = false;
    }

    public String getUrl(){
        return this.url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTts(boolean tts) {
        this.tts = tts;
    }

    @SuppressWarnings("unused")
    public void execute() throws IOException {
        JSONObject json = new JSONObject();

        json.put("content", this.content);
        json.put("username", this.username);
        json.put("avatar_url", this.avatarUrl);
        json.put("tts", this.tts);

        embeds: {
            if (this.embeds.isEmpty())
                break embeds;

            List<JSONObject> embedObjects = new ArrayList<>();

            for (EmbedObject embed : this.embeds) {
                JSONObject jsonEmbed = new JSONObject();

                jsonEmbed.put("title", embed.getTitle());
                jsonEmbed.put("description", embed.getDescription());
                jsonEmbed.put("url", embed.getUrl());

                Optional.ofNullable(embed.getColor()).ifPresent(color -> {
                    int rgb = color.getRed();
                    rgb = (rgb << 8) + color.getGreen();
                    rgb = (rgb << 8) + color.getBlue();

                    jsonEmbed.put("color", rgb);
                });

                Optional.ofNullable(embed.getTimestamp()).ifPresent(timestamp -> {
                    String isoTimestamp = timestamp.toString();

                    jsonEmbed.put("timestamp", isoTimestamp);
                });

                Optional.ofNullable(embed.getFooter()).ifPresent(footer -> {
                    JSONObject jsonFooter = new JSONObject();

                    jsonFooter.put("text", footer.getText());
                    jsonFooter.put("icon_url", footer.getIconUrl());
                    jsonEmbed.put("footer", jsonFooter);
                });

                Optional.ofNullable(embed.getImage()).ifPresent(image -> {
                    JSONObject jsonImage = new JSONObject();

                    jsonImage.put("url", image.getUrl());
                    jsonEmbed.put("image", jsonImage);
                });

                Optional.ofNullable(embed.getThumbnail()).ifPresent(thumbnail -> {
                    JSONObject jsonThumbnail = new JSONObject();

                    jsonThumbnail.put("url", thumbnail.getUrl());
                    jsonEmbed.put("thumbnail", jsonThumbnail);
                });

                Optional.ofNullable(embed.getAuthor()).ifPresent(author -> {
                    JSONObject jsonAuthor = new JSONObject();

                    jsonAuthor.put("name", author.getName());
                    jsonAuthor.put("url", author.getUrl());
                    jsonAuthor.put("icon_url", author.getIconUrl());
                    jsonEmbed.put("author", jsonAuthor);
                });


                List<JSONObject> jsonFields = new ArrayList<>();

                Optional.ofNullable(embed.getFields()).ifPresent(fields -> {
                    fields.forEach(field -> {
                        JSONObject jsonField = new JSONObject();

                        jsonField.put("name", field.getName());
                        jsonField.put("value", field.getValue());
                        jsonField.put("inline", field.isInline());

                        jsonFields.add(jsonField);
                    });
                });

                jsonEmbed.put("fields", jsonFields.toArray());
                embedObjects.add(jsonEmbed);
            }

            json.put("embeds", embedObjects.toArray());
        }

        URL url = new URL(this.url);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "webhookly");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        OutputStream stream = connection.getOutputStream();
        stream.write(json.toString().getBytes());
        stream.flush();
        stream.close();

        connection.getInputStream().close();
        connection.disconnect();
    }

    private class EmbedObject {

        private String title;
        private String description;
        private String url;
        private Color color;
        private OffsetDateTime timestamp;

        private Footer footer;
        private Thumbnail thumbnail;
        private Image image;
        private Author author;

        private final List<Field> fields = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public Color getColor() {
            return color;
        }

        public OffsetDateTime getTimestamp() {
            return timestamp;
        }

        public Footer getFooter() {
            return footer;
        }

        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public Image getImage() {
            return image;
        }

        public Author getAuthor() {
            return author;
        }

        public List<Field> getFields() {
            return fields;
        }

    }

    private class Author {

        private final String name;
        private final String url;
        private final String iconUrl;

        private Author(String name, String url, String iconUrl) {
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }

    private class Field {

        private final String name;
        private final String value;
        private final boolean inline;

        private Field(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public boolean isInline() {
            return inline;
        }
    }

    private class Footer {

        private final String text;
        private final String iconUrl;

        private Footer(String text, String iconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
        }

        public String getText() {
            return text;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }

    private class Image {

        private final String url;

        private Image(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    private class Thumbnail {

        private final String url;

        private Thumbnail(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    private class JSONObject {

        private final HashMap<String, Object> map = new HashMap<>();

        void put(String key, Object value) {
            if (value == null)
                return;

            map.put(key, value);
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            builder.append("{");

            int i = 0;
            for (Map.Entry<String, Object> entry : entrySet) {
                Object val = entry.getValue();
                builder.append(escape(entry.getKey())).append(":");

                if (val instanceof String) {
                    builder.append(escape(String.valueOf(val)));
                } else if (val instanceof Integer) {
                    builder.append(Integer.valueOf(String.valueOf(val)));
                } else if (val instanceof Boolean) {
                    builder.append(val);
                } else if (val instanceof JSONObject) {
                    builder.append(val);
                } else if (val.getClass().isArray()) {
                    builder.append("[");
                    int len = Array.getLength(val);
                    for (int j = 0; j < len; j++) {
                        builder.append(Array.get(val, j).toString()).append(j != len - 1 ? "," : "");
                    }
                    builder.append("]");
                }

                builder.append(++i == entrySet.size() ? "}" : ",");
            }

            return builder.toString();
        }

        private String escape(String string) {
            StringBuilder builder = new StringBuilder("\"");
            for (char c : string.toCharArray()) {
                if (c == '\\' || c == '"' || c == '/') {
                    builder.append("\\").append(c);
                } else if (c == '\b') {
                    builder.append("\\b");
                } else if (c == '\t') {
                    builder.append("\\t");
                } else if (c == '\n') {
                    builder.append("\\n");
                } else if (c == '\f') {
                    builder.append("\\f");
                } else if (c == '\r') {
                    builder.append("\\r");
                } else if (c < ' ' || c > 0x7f) {
                    builder.append(String.format("\\u%04x", (int) c));
                } else {
                    builder.append(c);
                }
            }
            return builder.append('"').toString();
        }
    }
}
