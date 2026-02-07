package org.huangyanzhen.netherite.util.log.obj2json;

import java.util.LinkedHashMap;

public class ObjectString {
    int depth = 0;
    LinkedHashMap<String, String> keyValue = new LinkedHashMap<>();

    public ObjectString(int depth) {
        this.depth = depth;
    }

    public ObjectString put(String key, Object value) {
        return put(key, value == null ? "null" : value.toString());
    }

    public ObjectString put(String key, String value) {
        keyValue.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        String[] keys = keyValue.keySet().toArray(new String[0]);

        for (int i = 0; i < keys.length; i++) {
            sb.append("\n");
            sb.append("\t".repeat(Math.max(0, depth + 1)));
            sb.append("\"").append(keys[i]).append("\": ");
            String value = keyValue.get(keys[i]);

            if (!value.startsWith("{")){
                if (value.equals("Optional.empty") || value.equals("null")) {
                    // Value值为"Optional.empty"，视作null
                    value = "null";
                } else {
                    // Value值为"Optional[xxx]"，提取中间的xxx
                    if (value.matches("Optional\\[.*]")) {
                        value = value.substring(9, value.length() - 1);
                    }
                    value = String.format("\"%s\"", value);
                }
            }
            sb.append(value);
            if (i < keys.length - 1) sb.append(",");
        }

        sb.append("\n");

        for (int i = 0; i < depth; i++) {
            sb.append("\t");
        }

        sb.append("}");

        return sb.toString();
    }

}
