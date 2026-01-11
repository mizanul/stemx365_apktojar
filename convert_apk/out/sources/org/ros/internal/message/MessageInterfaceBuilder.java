package org.ros.internal.message;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.Set;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.p010ws.commons.util.Base64;
import org.ros.exception.RosMessageRuntimeException;
import org.ros.internal.message.context.MessageContext;
import org.ros.internal.message.context.MessageContextProvider;
import org.ros.internal.message.field.Field;
import org.ros.internal.message.field.FieldType;
import org.ros.internal.message.field.MessageFields;
import org.ros.internal.message.field.PrimitiveFieldType;
import org.ros.message.MessageDeclaration;
import org.ros.message.MessageFactory;

public class MessageInterfaceBuilder {
    private boolean addConstantsAndMethods;
    private String interfaceName;
    private MessageDeclaration messageDeclaration;
    private String nestedContent;
    private String packageName;

    private static String escapeJava(String str) {
        return StringEscapeUtils.escapeJava(str).replace("\\/", "/").replace("'", "\\'");
    }

    public MessageDeclaration getMessageDeclaration() {
        return this.messageDeclaration;
    }

    public MessageInterfaceBuilder setMessageDeclaration(MessageDeclaration messageDeclaration2) {
        Preconditions.checkNotNull(messageDeclaration2);
        this.messageDeclaration = messageDeclaration2;
        return this;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public MessageInterfaceBuilder setPackageName(String packageName2) {
        this.packageName = packageName2;
        return this;
    }

    public String getInterfaceName() {
        return this.interfaceName;
    }

    public MessageInterfaceBuilder setInterfaceName(String interfaceName2) {
        Preconditions.checkNotNull(interfaceName2);
        this.interfaceName = interfaceName2;
        return this;
    }

    public boolean getAddConstantsAndMethods() {
        return this.addConstantsAndMethods;
    }

    public void setAddConstantsAndMethods(boolean enabled) {
        this.addConstantsAndMethods = enabled;
    }

    public String getNestedContent() {
        return this.nestedContent;
    }

    public void setNestedContent(String nestedContent2) {
        this.nestedContent = nestedContent2;
    }

    public String build(MessageFactory messageFactory) {
        Preconditions.checkNotNull(this.messageDeclaration);
        Preconditions.checkNotNull(this.interfaceName);
        StringBuilder builder = new StringBuilder();
        String str = this.packageName;
        if (str != null) {
            builder.append(String.format("package %s;\n\n", new Object[]{str}));
        }
        builder.append(String.format("public interface %s extends org.ros.internal.message.Message {\n", new Object[]{this.interfaceName}));
        builder.append(String.format("  static final java.lang.String _TYPE = \"%s\";\n", new Object[]{this.messageDeclaration.getType()}));
        builder.append(String.format("  static final java.lang.String _DEFINITION = \"%s\";\n", new Object[]{escapeJava(this.messageDeclaration.getDefinition())}));
        if (this.addConstantsAndMethods) {
            MessageContext messageContext = new MessageContextProvider(messageFactory).get(this.messageDeclaration);
            appendConstants(messageContext, builder);
            appendSettersAndGetters(messageContext, builder);
        }
        if (this.nestedContent != null) {
            builder.append(Base64.LINE_SEPARATOR);
            builder.append(this.nestedContent);
        }
        builder.append("}\n");
        return builder.toString();
    }

    /* renamed from: org.ros.internal.message.MessageInterfaceBuilder$1 */
    static /* synthetic */ class C09661 {
        static final /* synthetic */ int[] $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType;

        static {
            int[] iArr = new int[PrimitiveFieldType.values().length];
            $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType = iArr;
            try {
                iArr[PrimitiveFieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.FLOAT32.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.BYTE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.CHAR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.INT8.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.UINT8.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.INT16.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.UINT16.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.INT32.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.UINT32.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.INT64.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.UINT64.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[PrimitiveFieldType.FLOAT64.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    private String getJavaValue(PrimitiveFieldType primitiveFieldType, String value) {
        switch (C09661.$SwitchMap$org$ros$internal$message$field$PrimitiveFieldType[primitiveFieldType.ordinal()]) {
            case 1:
                return Boolean.valueOf(!value.equals("0") && !value.equals("false")).toString();
            case 2:
                return value + "f";
            case 3:
                return "\"" + escapeJava(value) + "\"";
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
                return value;
            default:
                throw new RosMessageRuntimeException("Unsupported PrimitiveFieldType: " + primitiveFieldType);
        }
    }

    private void appendConstants(MessageContext messageContext, StringBuilder builder) {
        for (Field field : new MessageFields(messageContext).getFields()) {
            if (field.isConstant()) {
                Preconditions.checkState(field.getType() instanceof PrimitiveFieldType);
                FieldType fieldType = field.getType();
                builder.append(String.format("  static final %s %s = %s;\n", new Object[]{fieldType.getJavaTypeName(), field.getName(), getJavaValue((PrimitiveFieldType) fieldType, field.getValue().toString())}));
            }
        }
    }

    private void appendSettersAndGetters(MessageContext messageContext, StringBuilder builder) {
        MessageFields messageFields = new MessageFields(messageContext);
        Set<String> getters = Sets.newHashSet();
        for (Field field : messageFields.getFields()) {
            if (!field.isConstant()) {
                String type = field.getJavaTypeName();
                String getter = messageContext.getFieldGetterName(field.getName());
                String setter = messageContext.getFieldSetterName(field.getName());
                if (!getters.contains(getter)) {
                    getters.add(getter);
                    builder.append(String.format("  %s %s();\n", new Object[]{type, getter}));
                    builder.append(String.format("  void %s(%s value);\n", new Object[]{setter, type}));
                }
            }
        }
    }
}
