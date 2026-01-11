package gov.nasa.arc.astrobee;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface RobotFactory {
    String getLocalName();

    Robot getRobot() throws AstrobeeException, InterruptedException;

    Robot getRobot(long j, TimeUnit timeUnit) throws AstrobeeException, InterruptedException, TimeoutException;

    Robot getRobot(String str) throws AstrobeeException, InterruptedException;

    Robot getRobot(String str, long j, TimeUnit timeUnit) throws AstrobeeException, InterruptedException, TimeoutException;

    void shutdown();
}
