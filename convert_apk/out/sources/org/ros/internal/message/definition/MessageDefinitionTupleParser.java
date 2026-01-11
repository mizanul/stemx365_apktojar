package org.ros.internal.message.definition;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.p010ws.commons.util.Base64;

public class MessageDefinitionTupleParser {
    private static final String SEPARATOR = "---";

    public static List<String> parse(String definition, int size) {
        Preconditions.checkNotNull(definition);
        List<String> definitions = Lists.newArrayList();
        StringBuilder current = new StringBuilder();
        for (String line : definition.split(Base64.LINE_SEPARATOR)) {
            if (line.startsWith(SEPARATOR)) {
                definitions.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(line);
                current.append(Base64.LINE_SEPARATOR);
            }
        }
        if (current.length() > 0) {
            current.deleteCharAt(current.length() - 1);
        }
        definitions.add(current.toString());
        Preconditions.checkState(size == -1 || definitions.size() <= size, String.format("Message tuple exceeds expected size: %d > %d", new Object[]{Integer.valueOf(definitions.size()), Integer.valueOf(size)}));
        while (definitions.size() < size) {
            definitions.add("");
        }
        return definitions;
    }
}
