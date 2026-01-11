package p001jp.jaxa.iss.kibo.rpc.sampleapk;

import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import org.opencv.core.Mat;
import p001jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

/* renamed from: jp.jaxa.iss.kibo.rpc.sampleapk.YourService */
public class YourService extends KiboRpcService {
    /* access modifiers changed from: protected */
    public void runPlan1() {
        this.api.startMission();
        this.api.moveTo(new Point(10.9d, -9.92284d, 5.195d), new Quaternion(0.0f, 0.0f, -0.707f, 0.707f), false);
        Mat matNavCam = this.api.getMatNavCam();
        this.api.setAreaInfo(1, "item_name", 1);
        this.api.reportRoundingCompletion();
        this.api.notifyRecognitionItem();
        this.api.takeTargetItemSnapshot();
    }

    /* access modifiers changed from: protected */
    public void runPlan2() {
    }

    /* access modifiers changed from: protected */
    public void runPlan3() {
    }

    private String yourMethod() {
        return "your method";
    }
}
