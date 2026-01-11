package org.ros.internal.message.action;

import org.ros.internal.message.MessageGenerationTemplate;
import tf2_msgs.LookupTransformFeedback;

public class ActionGenerationTemplateFeedback implements MessageGenerationTemplate {
    public String applyTemplate(String messageSource) {
        return LookupTransformFeedback._DEFINITION + messageSource;
    }
}
