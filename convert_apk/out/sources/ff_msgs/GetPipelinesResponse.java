package ff_msgs;

import java.util.List;
import org.ros.internal.message.Message;

public interface GetPipelinesResponse extends Message {
    public static final String _DEFINITION = "\nff_msgs/LocalizationPipeline[] pipelines";
    public static final String _TYPE = "ff_msgs/GetPipelinesResponse";

    List<LocalizationPipeline> getPipelines();

    void setPipelines(List<LocalizationPipeline> list);
}
