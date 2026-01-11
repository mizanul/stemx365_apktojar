package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.random.Random;
import kotlin.random.RandomKt;

@Metadata(mo11628d1 = {"\u0000n\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001d\u001a'\u0010\u0000\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0012\u0010\u0000\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b\u001a\u0012\u0010\u0000\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t\u001a\u0012\u0010\u0000\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n\u001a'\u0010\u000b\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\u0006\u0010\f\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0012\u0010\u000b\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u0012\u0010\u000b\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u0012\u0010\u000b\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\u000b\u001a\u00020\b*\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0012\u0010\u000b\u001a\u00020\t*\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0012\u0010\u000b\u001a\u00020\n*\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a3\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u0001H\u00012\b\u0010\f\u001a\u0004\u0018\u0001H\u0001¢\u0006\u0002\u0010\u000e\u001a/\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010H\u0007¢\u0006\u0002\u0010\u0011\u001a-\u0010\r\u001a\u0002H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0002*\u0002H\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0012¢\u0006\u0002\u0010\u0013\u001a\u001a\u0010\r\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005\u001a\u001a\u0010\r\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006\u001a\u001a\u0010\r\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007\u001a\u001a\u0010\r\u001a\u00020\b*\u00020\b2\u0006\u0010\u0003\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b\u001a\u0018\u0010\r\u001a\u00020\b*\u00020\b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u001a\u001a\u0010\r\u001a\u00020\t*\u00020\t2\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\t*\u00020\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u0012\u001a\u001a\u0010\r\u001a\u00020\n*\u00020\n2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\n¢\u0006\u0002\u0010\u0019\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0002¢\u0006\u0002\b\u001b\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00060\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0002¢\u0006\u0002\b\u001c\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\u00070\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0002¢\u0006\u0002\b\u001d\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\b0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0002¢\u0006\u0002\b\u001e\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\t0\u00122\u0006\u0010\u001a\u001a\u00020\nH\u0002¢\u0006\u0002\b\u001f\u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0005H\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0006H\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\u0007H\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\bH\u0002¢\u0006\u0002\b \u001a \u0010\u0014\u001a\u00020\u0015*\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u001a\u001a\u00020\tH\u0002¢\u0006\u0002\b \u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020!2\b\u0010\u0017\u001a\u0004\u0018\u00010\bH\n¢\u0006\u0002\u0010\"\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020#2\b\u0010\u0017\u001a\u0004\u0018\u00010\tH\n¢\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0004\u001a\u0015\u0010%\u001a\u00020)*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010%\u001a\u00020(*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010%\u001a\u00020&*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0004\u001a\r\u0010*\u001a\u00020\u0018*\u00020\u0016H\b\u001a\u0014\u0010*\u001a\u00020\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\b*\u00020!H\b\u001a\u0014\u0010*\u001a\u00020\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007\u001a\r\u0010*\u001a\u00020\t*\u00020#H\b\u001a\u0014\u0010*\u001a\u00020\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020\u0016H\b¢\u0006\u0002\u0010-\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\u0018*\u00020\u00162\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u0010.\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\b*\u00020!H\b¢\u0006\u0002\u0010/\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\b*\u00020!2\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u00100\u001a\u0014\u0010,\u001a\u0004\u0018\u00010\t*\u00020#H\b¢\u0006\u0002\u00101\u001a\u001b\u0010,\u001a\u0004\u0018\u00010\t*\u00020#2\u0006\u0010*\u001a\u00020+H\u0007¢\u0006\u0002\u00102\u001a\n\u00103\u001a\u00020)*\u00020)\u001a\n\u00103\u001a\u00020&*\u00020&\u001a\n\u00103\u001a\u00020(*\u00020(\u001a\u0015\u00104\u001a\u00020)*\u00020)2\u0006\u00104\u001a\u00020\bH\u0004\u001a\u0015\u00104\u001a\u00020&*\u00020&2\u0006\u00104\u001a\u00020\bH\u0004\u001a\u0015\u00104\u001a\u00020(*\u00020(2\u0006\u00104\u001a\u00020\tH\u0004\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\u0006H\u0000¢\u0006\u0002\u00106\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\u0007H\u0000¢\u0006\u0002\u00107\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\bH\u0000¢\u0006\u0002\u00108\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\tH\u0000¢\u0006\u0002\u00109\u001a\u0013\u00105\u001a\u0004\u0018\u00010\u0005*\u00020\nH\u0000¢\u0006\u0002\u0010:\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\u0006H\u0000¢\u0006\u0002\u0010<\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\u0007H\u0000¢\u0006\u0002\u0010=\u001a\u0013\u0010;\u001a\u0004\u0018\u00010\b*\u00020\tH\u0000¢\u0006\u0002\u0010>\u001a\u0013\u0010?\u001a\u0004\u0018\u00010\t*\u00020\u0006H\u0000¢\u0006\u0002\u0010@\u001a\u0013\u0010?\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000¢\u0006\u0002\u0010A\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\u0006H\u0000¢\u0006\u0002\u0010C\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\u0007H\u0000¢\u0006\u0002\u0010D\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\bH\u0000¢\u0006\u0002\u0010E\u001a\u0013\u0010B\u001a\u0004\u0018\u00010\n*\u00020\tH\u0000¢\u0006\u0002\u0010F\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\u00052\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\u00052\u0006\u0010'\u001a\u00020\nH\u0004\u001a\u0015\u0010G\u001a\u00020\u0016*\u00020\u00182\u0006\u0010'\u001a\u00020\u0018H\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\b2\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\b2\u0006\u0010'\u001a\u00020\nH\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\t2\u0006\u0010'\u001a\u00020\nH\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\u0005H\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\bH\u0004\u001a\u0015\u0010G\u001a\u00020#*\u00020\n2\u0006\u0010'\u001a\u00020\tH\u0004\u001a\u0015\u0010G\u001a\u00020!*\u00020\n2\u0006\u0010'\u001a\u00020\nH\u0004¨\u0006H"}, mo11629d2 = {"coerceAtLeast", "T", "", "minimumValue", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "", "", "", "", "", "", "coerceAtMost", "maximumValue", "coerceIn", "(Ljava/lang/Comparable;Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "range", "Lkotlin/ranges/ClosedFloatingPointRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedFloatingPointRange;)Ljava/lang/Comparable;", "Lkotlin/ranges/ClosedRange;", "(Ljava/lang/Comparable;Lkotlin/ranges/ClosedRange;)Ljava/lang/Comparable;", "contains", "", "Lkotlin/ranges/CharRange;", "element", "", "(Lkotlin/ranges/CharRange;Ljava/lang/Character;)Z", "value", "byteRangeContains", "doubleRangeContains", "floatRangeContains", "intRangeContains", "longRangeContains", "shortRangeContains", "Lkotlin/ranges/IntRange;", "(Lkotlin/ranges/IntRange;Ljava/lang/Integer;)Z", "Lkotlin/ranges/LongRange;", "(Lkotlin/ranges/LongRange;Ljava/lang/Long;)Z", "downTo", "Lkotlin/ranges/IntProgression;", "to", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/CharProgression;", "random", "Lkotlin/random/Random;", "randomOrNull", "(Lkotlin/ranges/CharRange;)Ljava/lang/Character;", "(Lkotlin/ranges/CharRange;Lkotlin/random/Random;)Ljava/lang/Character;", "(Lkotlin/ranges/IntRange;)Ljava/lang/Integer;", "(Lkotlin/ranges/IntRange;Lkotlin/random/Random;)Ljava/lang/Integer;", "(Lkotlin/ranges/LongRange;)Ljava/lang/Long;", "(Lkotlin/ranges/LongRange;Lkotlin/random/Random;)Ljava/lang/Long;", "reversed", "step", "toByteExactOrNull", "(D)Ljava/lang/Byte;", "(F)Ljava/lang/Byte;", "(I)Ljava/lang/Byte;", "(J)Ljava/lang/Byte;", "(S)Ljava/lang/Byte;", "toIntExactOrNull", "(D)Ljava/lang/Integer;", "(F)Ljava/lang/Integer;", "(J)Ljava/lang/Integer;", "toLongExactOrNull", "(D)Ljava/lang/Long;", "(F)Ljava/lang/Long;", "toShortExactOrNull", "(D)Ljava/lang/Short;", "(F)Ljava/lang/Short;", "(I)Ljava/lang/Short;", "(J)Ljava/lang/Short;", "until", "kotlin-stdlib"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/ranges/RangesKt")
/* compiled from: _Ranges.kt */
class RangesKt___RangesKt extends RangesKt__RangesKt {
    private static final int random(IntRange $this$random) {
        return RangesKt.random($this$random, (Random) Random.Default);
    }

    private static final long random(LongRange $this$random) {
        return RangesKt.random($this$random, (Random) Random.Default);
    }

    private static final char random(CharRange $this$random) {
        return RangesKt.random($this$random, (Random) Random.Default);
    }

    public static final int random(IntRange $this$random, Random random) {
        Intrinsics.checkNotNullParameter($this$random, "$this$random");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return RandomKt.nextInt(random, $this$random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final long random(LongRange $this$random, Random random) {
        Intrinsics.checkNotNullParameter($this$random, "$this$random");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return RandomKt.nextLong(random, $this$random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static final char random(CharRange $this$random, Random random) {
        Intrinsics.checkNotNullParameter($this$random, "$this$random");
        Intrinsics.checkNotNullParameter(random, "random");
        try {
            return (char) random.nextInt($this$random.getFirst(), $this$random.getLast() + 1);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    private static final Integer randomOrNull(IntRange $this$randomOrNull) {
        return RangesKt.randomOrNull($this$randomOrNull, (Random) Random.Default);
    }

    private static final Long randomOrNull(LongRange $this$randomOrNull) {
        return RangesKt.randomOrNull($this$randomOrNull, (Random) Random.Default);
    }

    private static final Character randomOrNull(CharRange $this$randomOrNull) {
        return RangesKt.randomOrNull($this$randomOrNull, (Random) Random.Default);
    }

    public static final Integer randomOrNull(IntRange $this$randomOrNull, Random random) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "$this$randomOrNull");
        Intrinsics.checkNotNullParameter(random, "random");
        if ($this$randomOrNull.isEmpty()) {
            return null;
        }
        return Integer.valueOf(RandomKt.nextInt(random, $this$randomOrNull));
    }

    public static final Long randomOrNull(LongRange $this$randomOrNull, Random random) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "$this$randomOrNull");
        Intrinsics.checkNotNullParameter(random, "random");
        if ($this$randomOrNull.isEmpty()) {
            return null;
        }
        return Long.valueOf(RandomKt.nextLong(random, $this$randomOrNull));
    }

    public static final Character randomOrNull(CharRange $this$randomOrNull, Random random) {
        Intrinsics.checkNotNullParameter($this$randomOrNull, "$this$randomOrNull");
        Intrinsics.checkNotNullParameter(random, "random");
        if ($this$randomOrNull.isEmpty()) {
            return null;
        }
        return Character.valueOf((char) random.nextInt($this$randomOrNull.getFirst(), $this$randomOrNull.getLast() + 1));
    }

    private static final boolean contains(IntRange $this$contains, Integer element) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return element != null && $this$contains.contains(element.intValue());
    }

    private static final boolean contains(LongRange $this$contains, Long element) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return element != null && $this$contains.contains(element.longValue());
    }

    private static final boolean contains(CharRange $this$contains, Character element) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return element != null && $this$contains.contains(element.charValue());
    }

    public static final boolean intRangeContains(ClosedRange<Integer> $this$contains, byte value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Integer.valueOf(value));
    }

    public static final boolean longRangeContains(ClosedRange<Long> $this$contains, byte value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Long.valueOf((long) value));
    }

    public static final boolean shortRangeContains(ClosedRange<Short> $this$contains, byte value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Short.valueOf((short) value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean intRangeContains(ClosedRange<Integer> $this$contains, double value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean longRangeContains(ClosedRange<Long> $this$contains, double value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Long it = RangesKt.toLongExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean byteRangeContains(ClosedRange<Byte> $this$contains, double value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean shortRangeContains(ClosedRange<Short> $this$contains, double value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final boolean floatRangeContains(ClosedRange<Float> $this$contains, double value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Float.valueOf((float) value));
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean intRangeContains(ClosedRange<Integer> $this$contains, float value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean longRangeContains(ClosedRange<Long> $this$contains, float value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Long it = RangesKt.toLongExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean byteRangeContains(ClosedRange<Byte> $this$contains, float value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    @Deprecated(message = "This `contains` operation mixing integer and floating point arguments has ambiguous semantics and is going to be removed.")
    @DeprecatedSinceKotlin(errorSince = "1.4", hiddenSince = "1.5", warningSince = "1.3")
    public static final /* synthetic */ boolean shortRangeContains(ClosedRange<Short> $this$contains, float value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final boolean doubleRangeContains(ClosedRange<Double> $this$contains, float value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Double.valueOf((double) value));
    }

    public static final boolean longRangeContains(ClosedRange<Long> $this$contains, int value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Long.valueOf((long) value));
    }

    public static final boolean byteRangeContains(ClosedRange<Byte> $this$contains, int value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final boolean shortRangeContains(ClosedRange<Short> $this$contains, int value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final boolean intRangeContains(ClosedRange<Integer> $this$contains, long value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Integer it = RangesKt.toIntExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final boolean byteRangeContains(ClosedRange<Byte> $this$contains, long value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final boolean shortRangeContains(ClosedRange<Short> $this$contains, long value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Short it = RangesKt.toShortExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final boolean intRangeContains(ClosedRange<Integer> $this$contains, short value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Integer.valueOf(value));
    }

    public static final boolean longRangeContains(ClosedRange<Long> $this$contains, short value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        return $this$contains.contains(Long.valueOf((long) value));
    }

    public static final boolean byteRangeContains(ClosedRange<Byte> $this$contains, short value) {
        Intrinsics.checkNotNullParameter($this$contains, "$this$contains");
        Byte it = RangesKt.toByteExactOrNull(value);
        if (it != null) {
            return $this$contains.contains(it);
        }
        return false;
    }

    public static final IntProgression downTo(int $this$downTo, byte to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final LongProgression downTo(long $this$downTo, byte to) {
        return LongProgression.Companion.fromClosedRange($this$downTo, (long) to, -1);
    }

    public static final IntProgression downTo(byte $this$downTo, byte to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final IntProgression downTo(short $this$downTo, byte to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final CharProgression downTo(char $this$downTo, char to) {
        return CharProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final IntProgression downTo(int $this$downTo, int to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final LongProgression downTo(long $this$downTo, int to) {
        return LongProgression.Companion.fromClosedRange($this$downTo, (long) to, -1);
    }

    public static final IntProgression downTo(byte $this$downTo, int to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final IntProgression downTo(short $this$downTo, int to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final LongProgression downTo(int $this$downTo, long to) {
        return LongProgression.Companion.fromClosedRange((long) $this$downTo, to, -1);
    }

    public static final LongProgression downTo(long $this$downTo, long to) {
        return LongProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final LongProgression downTo(byte $this$downTo, long to) {
        return LongProgression.Companion.fromClosedRange((long) $this$downTo, to, -1);
    }

    public static final LongProgression downTo(short $this$downTo, long to) {
        return LongProgression.Companion.fromClosedRange((long) $this$downTo, to, -1);
    }

    public static final IntProgression downTo(int $this$downTo, short to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final LongProgression downTo(long $this$downTo, short to) {
        return LongProgression.Companion.fromClosedRange($this$downTo, (long) to, -1);
    }

    public static final IntProgression downTo(byte $this$downTo, short to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final IntProgression downTo(short $this$downTo, short to) {
        return IntProgression.Companion.fromClosedRange($this$downTo, to, -1);
    }

    public static final IntProgression reversed(IntProgression $this$reversed) {
        Intrinsics.checkNotNullParameter($this$reversed, "$this$reversed");
        return IntProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
    }

    public static final LongProgression reversed(LongProgression $this$reversed) {
        Intrinsics.checkNotNullParameter($this$reversed, "$this$reversed");
        return LongProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
    }

    public static final CharProgression reversed(CharProgression $this$reversed) {
        Intrinsics.checkNotNullParameter($this$reversed, "$this$reversed");
        return CharProgression.Companion.fromClosedRange($this$reversed.getLast(), $this$reversed.getFirst(), -$this$reversed.getStep());
    }

    public static final IntProgression step(IntProgression $this$step, int step) {
        Intrinsics.checkNotNullParameter($this$step, "$this$step");
        RangesKt.checkStepIsPositive(step > 0, Integer.valueOf(step));
        return IntProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0 ? step : -step);
    }

    public static final LongProgression step(LongProgression $this$step, long step) {
        Intrinsics.checkNotNullParameter($this$step, "$this$step");
        RangesKt.checkStepIsPositive(step > 0, Long.valueOf(step));
        return LongProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0 ? step : -step);
    }

    public static final CharProgression step(CharProgression $this$step, int step) {
        Intrinsics.checkNotNullParameter($this$step, "$this$step");
        RangesKt.checkStepIsPositive(step > 0, Integer.valueOf(step));
        return CharProgression.Companion.fromClosedRange($this$step.getFirst(), $this$step.getLast(), $this$step.getStep() > 0 ? step : -step);
    }

    public static final Byte toByteExactOrNull(int $this$toByteExactOrNull) {
        if (-128 <= $this$toByteExactOrNull && 127 >= $this$toByteExactOrNull) {
            return Byte.valueOf((byte) $this$toByteExactOrNull);
        }
        return null;
    }

    public static final Byte toByteExactOrNull(long $this$toByteExactOrNull) {
        long j = (long) 127;
        if (((long) -128) <= $this$toByteExactOrNull && j >= $this$toByteExactOrNull) {
            return Byte.valueOf((byte) ((int) $this$toByteExactOrNull));
        }
        return null;
    }

    public static final Byte toByteExactOrNull(short $this$toByteExactOrNull) {
        short s = (short) 127;
        if (((short) -128) <= $this$toByteExactOrNull && s >= $this$toByteExactOrNull) {
            return Byte.valueOf((byte) $this$toByteExactOrNull);
        }
        return null;
    }

    public static final Byte toByteExactOrNull(double $this$toByteExactOrNull) {
        double d = (double) 127;
        if ($this$toByteExactOrNull < ((double) -128) || $this$toByteExactOrNull > d) {
            return null;
        }
        return Byte.valueOf((byte) ((int) $this$toByteExactOrNull));
    }

    public static final Byte toByteExactOrNull(float $this$toByteExactOrNull) {
        float f = (float) 127;
        if ($this$toByteExactOrNull < ((float) -128) || $this$toByteExactOrNull > f) {
            return null;
        }
        return Byte.valueOf((byte) ((int) $this$toByteExactOrNull));
    }

    public static final Integer toIntExactOrNull(long $this$toIntExactOrNull) {
        long j = (long) Integer.MAX_VALUE;
        if (((long) Integer.MIN_VALUE) <= $this$toIntExactOrNull && j >= $this$toIntExactOrNull) {
            return Integer.valueOf((int) $this$toIntExactOrNull);
        }
        return null;
    }

    public static final Integer toIntExactOrNull(double $this$toIntExactOrNull) {
        double d = (double) Integer.MAX_VALUE;
        if ($this$toIntExactOrNull < ((double) Integer.MIN_VALUE) || $this$toIntExactOrNull > d) {
            return null;
        }
        return Integer.valueOf((int) $this$toIntExactOrNull);
    }

    public static final Integer toIntExactOrNull(float $this$toIntExactOrNull) {
        float f = (float) Integer.MAX_VALUE;
        if ($this$toIntExactOrNull < ((float) Integer.MIN_VALUE) || $this$toIntExactOrNull > f) {
            return null;
        }
        return Integer.valueOf((int) $this$toIntExactOrNull);
    }

    public static final Long toLongExactOrNull(double $this$toLongExactOrNull) {
        double d = (double) LongCompanionObject.MAX_VALUE;
        if ($this$toLongExactOrNull < ((double) Long.MIN_VALUE) || $this$toLongExactOrNull > d) {
            return null;
        }
        return Long.valueOf((long) $this$toLongExactOrNull);
    }

    public static final Long toLongExactOrNull(float $this$toLongExactOrNull) {
        float f = (float) LongCompanionObject.MAX_VALUE;
        if ($this$toLongExactOrNull < ((float) Long.MIN_VALUE) || $this$toLongExactOrNull > f) {
            return null;
        }
        return Long.valueOf((long) $this$toLongExactOrNull);
    }

    public static final Short toShortExactOrNull(int $this$toShortExactOrNull) {
        if (-32768 <= $this$toShortExactOrNull && 32767 >= $this$toShortExactOrNull) {
            return Short.valueOf((short) $this$toShortExactOrNull);
        }
        return null;
    }

    public static final Short toShortExactOrNull(long $this$toShortExactOrNull) {
        long j = (long) 32767;
        if (((long) -32768) <= $this$toShortExactOrNull && j >= $this$toShortExactOrNull) {
            return Short.valueOf((short) ((int) $this$toShortExactOrNull));
        }
        return null;
    }

    public static final Short toShortExactOrNull(double $this$toShortExactOrNull) {
        double d = (double) 32767;
        if ($this$toShortExactOrNull < ((double) -32768) || $this$toShortExactOrNull > d) {
            return null;
        }
        return Short.valueOf((short) ((int) $this$toShortExactOrNull));
    }

    public static final Short toShortExactOrNull(float $this$toShortExactOrNull) {
        float f = (float) 32767;
        if ($this$toShortExactOrNull < ((float) -32768) || $this$toShortExactOrNull > f) {
            return null;
        }
        return Short.valueOf((short) ((int) $this$toShortExactOrNull));
    }

    public static final IntRange until(int $this$until, byte to) {
        return new IntRange($this$until, to - 1);
    }

    public static final LongRange until(long $this$until, byte to) {
        return new LongRange($this$until, ((long) to) - 1);
    }

    public static final IntRange until(byte $this$until, byte to) {
        return new IntRange($this$until, to - 1);
    }

    public static final IntRange until(short $this$until, byte to) {
        return new IntRange($this$until, to - 1);
    }

    public static final CharRange until(char $this$until, char to) {
        if (Intrinsics.compare((int) to, 0) <= 0) {
            return CharRange.Companion.getEMPTY();
        }
        return new CharRange($this$until, (char) (to - 1));
    }

    public static final IntRange until(int $this$until, int to) {
        if (to <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange($this$until, to - 1);
    }

    public static final LongRange until(long $this$until, int to) {
        return new LongRange($this$until, ((long) to) - 1);
    }

    public static final IntRange until(byte $this$until, int to) {
        if (to <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange($this$until, to - 1);
    }

    public static final IntRange until(short $this$until, int to) {
        if (to <= Integer.MIN_VALUE) {
            return IntRange.Companion.getEMPTY();
        }
        return new IntRange($this$until, to - 1);
    }

    public static final LongRange until(int $this$until, long to) {
        if (to <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange((long) $this$until, to - 1);
    }

    public static final LongRange until(long $this$until, long to) {
        if (to <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange($this$until, to - 1);
    }

    public static final LongRange until(byte $this$until, long to) {
        if (to <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange((long) $this$until, to - 1);
    }

    public static final LongRange until(short $this$until, long to) {
        if (to <= Long.MIN_VALUE) {
            return LongRange.Companion.getEMPTY();
        }
        return new LongRange((long) $this$until, to - 1);
    }

    public static final IntRange until(int $this$until, short to) {
        return new IntRange($this$until, to - 1);
    }

    public static final LongRange until(long $this$until, short to) {
        return new LongRange($this$until, ((long) to) - 1);
    }

    public static final IntRange until(byte $this$until, short to) {
        return new IntRange($this$until, to - 1);
    }

    public static final IntRange until(short $this$until, short to) {
        return new IntRange($this$until, to - 1);
    }

    public static final <T extends Comparable<? super T>> T coerceAtLeast(T $this$coerceAtLeast, T minimumValue) {
        Intrinsics.checkNotNullParameter($this$coerceAtLeast, "$this$coerceAtLeast");
        Intrinsics.checkNotNullParameter(minimumValue, "minimumValue");
        return $this$coerceAtLeast.compareTo(minimumValue) < 0 ? minimumValue : $this$coerceAtLeast;
    }

    public static final byte coerceAtLeast(byte $this$coerceAtLeast, byte minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final short coerceAtLeast(short $this$coerceAtLeast, short minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final int coerceAtLeast(int $this$coerceAtLeast, int minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final long coerceAtLeast(long $this$coerceAtLeast, long minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final float coerceAtLeast(float $this$coerceAtLeast, float minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final double coerceAtLeast(double $this$coerceAtLeast, double minimumValue) {
        return $this$coerceAtLeast < minimumValue ? minimumValue : $this$coerceAtLeast;
    }

    public static final <T extends Comparable<? super T>> T coerceAtMost(T $this$coerceAtMost, T maximumValue) {
        Intrinsics.checkNotNullParameter($this$coerceAtMost, "$this$coerceAtMost");
        Intrinsics.checkNotNullParameter(maximumValue, "maximumValue");
        return $this$coerceAtMost.compareTo(maximumValue) > 0 ? maximumValue : $this$coerceAtMost;
    }

    public static final byte coerceAtMost(byte $this$coerceAtMost, byte maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final short coerceAtMost(short $this$coerceAtMost, short maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final int coerceAtMost(int $this$coerceAtMost, int maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final long coerceAtMost(long $this$coerceAtMost, long maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final float coerceAtMost(float $this$coerceAtMost, float maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final double coerceAtMost(double $this$coerceAtMost, double maximumValue) {
        return $this$coerceAtMost > maximumValue ? maximumValue : $this$coerceAtMost;
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T $this$coerceIn, T minimumValue, T maximumValue) {
        Intrinsics.checkNotNullParameter($this$coerceIn, "$this$coerceIn");
        if (minimumValue == null || maximumValue == null) {
            if (minimumValue != null && $this$coerceIn.compareTo(minimumValue) < 0) {
                return minimumValue;
            }
            if (maximumValue == null || $this$coerceIn.compareTo(maximumValue) <= 0) {
                return $this$coerceIn;
            }
            return maximumValue;
        } else if (minimumValue.compareTo(maximumValue) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn.compareTo(minimumValue) < 0) {
            return minimumValue;
        } else {
            if ($this$coerceIn.compareTo(maximumValue) > 0) {
                return maximumValue;
            }
        }
        return $this$coerceIn;
    }

    public static final byte coerceIn(byte $this$coerceIn, byte minimumValue, byte maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final short coerceIn(short $this$coerceIn, short minimumValue, short maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final int coerceIn(int $this$coerceIn, int minimumValue, int maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final long coerceIn(long $this$coerceIn, long minimumValue, long maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final float coerceIn(float $this$coerceIn, float minimumValue, float maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final double coerceIn(double $this$coerceIn, double minimumValue, double maximumValue) {
        if (minimumValue > maximumValue) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + maximumValue + " is less than minimum " + minimumValue + '.');
        } else if ($this$coerceIn < minimumValue) {
            return minimumValue;
        } else {
            if ($this$coerceIn > maximumValue) {
                return maximumValue;
            }
            return $this$coerceIn;
        }
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T $this$coerceIn, ClosedFloatingPointRange<T> range) {
        Intrinsics.checkNotNullParameter($this$coerceIn, "$this$coerceIn");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        } else if (range.lessThanOrEquals($this$coerceIn, range.getStart()) && !range.lessThanOrEquals(range.getStart(), $this$coerceIn)) {
            return range.getStart();
        } else {
            if (!range.lessThanOrEquals(range.getEndInclusive(), $this$coerceIn) || range.lessThanOrEquals($this$coerceIn, range.getEndInclusive())) {
                return $this$coerceIn;
            }
            return range.getEndInclusive();
        }
    }

    public static final <T extends Comparable<? super T>> T coerceIn(T $this$coerceIn, ClosedRange<T> range) {
        Intrinsics.checkNotNullParameter($this$coerceIn, "$this$coerceIn");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return RangesKt.coerceIn($this$coerceIn, (ClosedFloatingPointRange) range);
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        } else if ($this$coerceIn.compareTo(range.getStart()) < 0) {
            return range.getStart();
        } else {
            if ($this$coerceIn.compareTo(range.getEndInclusive()) > 0) {
                return range.getEndInclusive();
            }
            return $this$coerceIn;
        }
    }

    public static final int coerceIn(int $this$coerceIn, ClosedRange<Integer> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((Number) RangesKt.coerceIn(Integer.valueOf($this$coerceIn), (ClosedFloatingPointRange) range)).intValue();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        } else if ($this$coerceIn < range.getStart().intValue()) {
            return range.getStart().intValue();
        } else {
            if ($this$coerceIn > range.getEndInclusive().intValue()) {
                return range.getEndInclusive().intValue();
            }
            return $this$coerceIn;
        }
    }

    public static final long coerceIn(long $this$coerceIn, ClosedRange<Long> range) {
        Intrinsics.checkNotNullParameter(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((Number) RangesKt.coerceIn(Long.valueOf($this$coerceIn), (ClosedFloatingPointRange) range)).longValue();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        } else if ($this$coerceIn < range.getStart().longValue()) {
            return range.getStart().longValue();
        } else {
            if ($this$coerceIn > range.getEndInclusive().longValue()) {
                return range.getEndInclusive().longValue();
            }
            return $this$coerceIn;
        }
    }
}
