package org.codetracker.change;

import org.codetracker.api.CodeElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface Change {

    Type getType();

    Optional<EvolutionHook<? extends CodeElement>> getEvolutionHook();

    enum Type {
        NO_CHANGE("not changed"),
        INTRODUCED("introduced"),
        REMOVED("removed"),
        CONTAINER_CHANGE("container change"),
        MULTI_CHANGE("changed multiple times"),
        BODY_CHANGE("body change"),
        CATCH_BLOCK_CHANGE("catch block change"),
        CATCH_BLOCK_ADDED("catch block added"),
        CATCH_BLOCK_REMOVED("catch block removed"),
        FINALLY_BLOCK_CHANGE("finally block change"),
        FINALLY_BLOCK_ADDED("finally block added"),
        FINALLY_BLOCK_REMOVED("finally block removed"),
        BLOCK_SPLIT("block split"),
        METHOD_SPLIT("method split"),
        METHOD_MERGE("method merge"),
        REPLACE_PIPELINE_WITH_LOOP("pipeline replaced with loop"),
        REPLACE_LOOP_WITH_PIPELINE("loop replaced with pipeline"),
        DOCUMENTATION_CHANGE("documentation change"),
        RENAME("rename"),
        MODIFIER_CHANGE("modifier change"),
        ACCESS_MODIFIER_CHANGE("access modifier change"),
        RETURN_TYPE_CHANGE("return type change"),
        TYPE_CHANGE("type change"),
        EXCEPTION_CHANGE("exception change"),
        PARAMETER_CHANGE("parameter change"),
        ANNOTATION_CHANGE("annotation change"),
        MOVED("moved"),
        EXPRESSION_CHANGE("expression change");

        private static final Map<String, Type> lookup = new HashMap<>();

        static {
            for (Type type : Type.values()) {
                lookup.put(type.title, type);
            }
        }

        private final String title;

        Type(String title) {
            this.title = title;
        }

        public static Type get(String title) {
            return lookup.get(title);
        }

        public String getTitle() {
            return title;
        }
    }
}
