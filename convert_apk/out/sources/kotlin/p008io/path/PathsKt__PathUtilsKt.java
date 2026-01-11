package kotlin.p008io.path;

import java.io.IOException;
import java.net.URI;
import java.nio.file.CopyOption;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.ros.node.topic.Subscriber;

@Metadata(mo11628d1 = {"\u0000²\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0011\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a*\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00012\u0012\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u001a\"\u00020\u0001H\b¢\u0006\u0002\u0010\u001b\u001a?\u0010\u001c\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007¢\u0006\u0002\u0010!\u001a6\u0010\u001c\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u0010\"\u001aK\u0010#\u001a\u00020\u00022\b\u0010\u001d\u001a\u0004\u0018\u00010\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\u0007¢\u0006\u0002\u0010%\u001aB\u0010#\u001a\u00020\u00022\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00012\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u0010&\u001a\u001c\u0010'\u001a\u00020(2\u0006\u0010\u0017\u001a\u00020\u00022\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*H\u0001\u001a\r\u0010+\u001a\u00020\u0002*\u00020\u0002H\b\u001a\r\u0010,\u001a\u00020\u0001*\u00020\u0002H\b\u001a.\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\b¢\u0006\u0002\u00101\u001a\u001f\u0010-\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\b\u001a.\u00104\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u00105\u001a.\u00106\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u00105\u001a.\u00107\u001a\u00020\u0002*\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u00105\u001a\u0015\u00108\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u0002H\b\u001a6\u00109\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u001a\u0010\u001f\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030 0\u001a\"\u0006\u0012\u0002\b\u00030 H\b¢\u0006\u0002\u0010:\u001a\r\u0010;\u001a\u00020<*\u00020\u0002H\b\u001a\r\u0010=\u001a\u000203*\u00020\u0002H\b\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\n\u001a\u0015\u0010>\u001a\u00020\u0002*\u00020\u00022\u0006\u0010?\u001a\u00020\u0001H\n\u001a&\u0010@\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a2\u0010C\u001a\u0002HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010F\u001a4\u0010G\u001a\u0004\u0018\u0001HD\"\n\b\u0000\u0010D\u0018\u0001*\u00020E*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010F\u001a\r\u0010H\u001a\u00020I*\u00020\u0002H\b\u001a\r\u0010J\u001a\u00020K*\u00020\u0002H\b\u001a.\u0010L\u001a\u00020<*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020<0OH\bø\u0001\u0000\u001a0\u0010P\u001a\u0004\u0018\u00010Q*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010S\u001a&\u0010T\u001a\u00020U*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010V\u001a(\u0010W\u001a\u0004\u0018\u00010X*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010Y\u001a,\u0010Z\u001a\b\u0012\u0004\u0012\u00020\\0[*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010]\u001a&\u0010^\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a\r\u0010_\u001a\u000203*\u00020\u0002H\b\u001a\r\u0010`\u001a\u000203*\u00020\u0002H\b\u001a\r\u0010a\u001a\u000203*\u00020\u0002H\b\u001a&\u0010b\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a\u0015\u0010c\u001a\u000203*\u00020\u00022\u0006\u0010?\u001a\u00020\u0002H\b\u001a\r\u0010d\u001a\u000203*\u00020\u0002H\b\u001a\r\u0010e\u001a\u000203*\u00020\u0002H\b\u001a\u001c\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00020g*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u0001H\u0007\u001a.\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u0002000\u001a\"\u000200H\b¢\u0006\u0002\u00101\u001a\u001f\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u00102\u001a\u000203H\b\u001a&\u0010i\u001a\u000203*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010B\u001a2\u0010j\u001a\u0002Hk\"\n\b\u0000\u0010k\u0018\u0001*\u00020l*\u00020\u00022\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010m\u001a<\u0010j\u001a\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0006\u0012\u0004\u0018\u00010Q0n*\u00020\u00022\u0006\u0010\u001f\u001a\u00020\u00012\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010o\u001a\r\u0010p\u001a\u00020\u0002*\u00020\u0002H\b\u001a\u0014\u0010q\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0016\u0010r\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a\u0014\u0010s\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0007\u001a8\u0010t\u001a\u00020\u0002*\u00020\u00022\u0006\u0010R\u001a\u00020\u00012\b\u0010u\u001a\u0004\u0018\u00010Q2\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020A0\u001a\"\u00020AH\b¢\u0006\u0002\u0010v\u001a\u0015\u0010w\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020UH\b\u001a\u0015\u0010x\u001a\u00020\u0002*\u00020\u00022\u0006\u0010u\u001a\u00020XH\b\u001a\u001b\u0010y\u001a\u00020\u0002*\u00020\u00022\f\u0010u\u001a\b\u0012\u0004\u0012\u00020\\0[H\b\u001a\r\u0010z\u001a\u00020\u0002*\u00020{H\b\u001a@\u0010|\u001a\u0002H}\"\u0004\b\u0000\u0010}*\u00020\u00022\b\b\u0002\u0010M\u001a\u00020\u00012\u0018\u0010~\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u0012\u0004\u0012\u0002H}0OH\bø\u0001\u0000¢\u0006\u0003\u0010\u0001\"\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u001f\u0010\u0007\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\b\u0010\u0004\u001a\u0004\b\t\u0010\u0006\"\u001e\u0010\n\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006\"\u001e\u0010\r\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006\"\u001e\u0010\u0010\u001a\u00020\u0001*\u00020\u00028FX\u0004¢\u0006\f\u0012\u0004\b\u0011\u0010\u0004\u001a\u0004\b\u0012\u0010\u0006\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00028Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0004\u001a\u0004\b\u0015\u0010\u0006\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0001"}, mo11629d2 = {"extension", "", "Ljava/nio/file/Path;", "getExtension$annotations", "(Ljava/nio/file/Path;)V", "getExtension", "(Ljava/nio/file/Path;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath$annotations", "getInvariantSeparatorsPath", "invariantSeparatorsPathString", "getInvariantSeparatorsPathString$annotations", "getInvariantSeparatorsPathString", "name", "getName$annotations", "getName", "nameWithoutExtension", "getNameWithoutExtension$annotations", "getNameWithoutExtension", "pathString", "getPathString$annotations", "getPathString", "Path", "path", "base", "subpaths", "", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", "createTempDirectory", "directory", "prefix", "attributes", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createTempFile", "suffix", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "(Ljava/lang/String;Ljava/lang/String;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "fileAttributeViewNotAvailable", "", "attributeViewClass", "Ljava/lang/Class;", "absolute", "absolutePathString", "copyTo", "target", "options", "Ljava/nio/file/CopyOption;", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)Ljava/nio/file/Path;", "overwrite", "", "createDirectories", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "createDirectory", "createFile", "createLinkPointingTo", "createSymbolicLinkPointingTo", "(Ljava/nio/file/Path;Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "deleteExisting", "", "deleteIfExists", "div", "other", "exists", "Ljava/nio/file/LinkOption;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "fileAttributesView", "V", "Ljava/nio/file/attribute/FileAttributeView;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileAttributeView;", "fileAttributesViewOrNull", "fileSize", "", "fileStore", "Ljava/nio/file/FileStore;", "forEachDirectoryEntry", "glob", "action", "Lkotlin/Function1;", "getAttribute", "", "attribute", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/lang/Object;", "getLastModifiedTime", "Ljava/nio/file/attribute/FileTime;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/FileTime;", "getOwner", "Ljava/nio/file/attribute/UserPrincipal;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/UserPrincipal;", "getPosixFilePermissions", "", "Ljava/nio/file/attribute/PosixFilePermission;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/util/Set;", "isDirectory", "isExecutable", "isHidden", "isReadable", "isRegularFile", "isSameFileAs", "isSymbolicLink", "isWritable", "listDirectoryEntries", "", "moveTo", "notExists", "readAttributes", "A", "Ljava/nio/file/attribute/BasicFileAttributes;", "(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Ljava/nio/file/attribute/BasicFileAttributes;", "", "(Ljava/nio/file/Path;Ljava/lang/String;[Ljava/nio/file/LinkOption;)Ljava/util/Map;", "readSymbolicLink", "relativeTo", "relativeToOrNull", "relativeToOrSelf", "setAttribute", "value", "(Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/Object;[Ljava/nio/file/LinkOption;)Ljava/nio/file/Path;", "setLastModifiedTime", "setOwner", "setPosixFilePermissions", "toPath", "Ljava/net/URI;", "useDirectoryEntries", "T", "block", "Lkotlin/sequences/Sequence;", "(Ljava/nio/file/Path;Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib-jdk7"}, mo11630k = 5, mo11631mv = {1, 5, 1}, mo11633xi = 1, mo11634xs = "kotlin/io/path/PathsKt")
/* renamed from: kotlin.io.path.PathsKt__PathUtilsKt */
/* compiled from: PathUtils.kt */
class PathsKt__PathUtilsKt extends PathsKt__PathReadWriteKt {
    public static /* synthetic */ void getExtension$annotations(Path path) {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use invariantSeparatorsPathString property instead.", replaceWith = @ReplaceWith(expression = "invariantSeparatorsPathString", imports = {}))
    public static /* synthetic */ void getInvariantSeparatorsPath$annotations(Path path) {
    }

    public static /* synthetic */ void getInvariantSeparatorsPathString$annotations(Path path) {
    }

    public static /* synthetic */ void getName$annotations(Path path) {
    }

    public static /* synthetic */ void getNameWithoutExtension$annotations(Path path) {
    }

    public static /* synthetic */ void getPathString$annotations(Path path) {
    }

    public static final String getName(Path $this$name) {
        Intrinsics.checkNotNullParameter($this$name, "$this$name");
        Path fileName = $this$name.getFileName();
        String obj = fileName != null ? fileName.toString() : null;
        return obj != null ? obj : "";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0011, code lost:
        r0 = kotlin.text.StringsKt.substringBeforeLast$default((r0 = r0.toString()), ".", (java.lang.String) null, 2, (java.lang.Object) null);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String getNameWithoutExtension(java.nio.file.Path r4) {
        /*
            java.lang.String r0 = "$this$nameWithoutExtension"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.nio.file.Path r0 = r4.getFileName()
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = r0.toString()
            if (r0 == 0) goto L_0x001c
            r1 = 2
            r2 = 0
            java.lang.String r3 = "."
            java.lang.String r0 = kotlin.text.StringsKt.substringBeforeLast$default((java.lang.String) r0, (java.lang.String) r3, (java.lang.String) r2, (int) r1, (java.lang.Object) r2)
            if (r0 == 0) goto L_0x001c
            goto L_0x001e
        L_0x001c:
            java.lang.String r0 = ""
        L_0x001e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathUtilsKt.getNameWithoutExtension(java.nio.file.Path):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0013, code lost:
        r0 = kotlin.text.StringsKt.substringAfterLast((r0 = r0.toString()), '.', "");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String getExtension(java.nio.file.Path r3) {
        /*
            java.lang.String r0 = "$this$extension"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            java.nio.file.Path r0 = r3.getFileName()
            java.lang.String r1 = ""
            if (r0 == 0) goto L_0x001c
            java.lang.String r0 = r0.toString()
            if (r0 == 0) goto L_0x001c
            r2 = 46
            java.lang.String r0 = kotlin.text.StringsKt.substringAfterLast((java.lang.String) r0, (char) r2, (java.lang.String) r1)
            if (r0 == 0) goto L_0x001c
            r1 = r0
        L_0x001c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathUtilsKt.getExtension(java.nio.file.Path):java.lang.String");
    }

    private static final String getPathString(Path $this$pathString) {
        return $this$pathString.toString();
    }

    public static final String getInvariantSeparatorsPathString(Path $this$invariantSeparatorsPathString) {
        Intrinsics.checkNotNullParameter($this$invariantSeparatorsPathString, "$this$invariantSeparatorsPathString");
        FileSystem fileSystem = $this$invariantSeparatorsPathString.getFileSystem();
        Intrinsics.checkNotNullExpressionValue(fileSystem, "fileSystem");
        String separator = fileSystem.getSeparator();
        if (!(!Intrinsics.areEqual((Object) separator, (Object) "/"))) {
            return $this$invariantSeparatorsPathString.toString();
        }
        String obj = $this$invariantSeparatorsPathString.toString();
        Intrinsics.checkNotNullExpressionValue(separator, "separator");
        return StringsKt.replace$default(obj, separator, "/", false, 4, (Object) null);
    }

    private static final String getInvariantSeparatorsPath(Path $this$invariantSeparatorsPath) {
        return PathsKt.getInvariantSeparatorsPathString($this$invariantSeparatorsPath);
    }

    private static final Path absolute(Path $this$absolute) {
        Path absolutePath = $this$absolute.toAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "toAbsolutePath()");
        return absolutePath;
    }

    private static final String absolutePathString(Path $this$absolutePathString) {
        return $this$absolutePathString.toAbsolutePath().toString();
    }

    public static final Path relativeTo(Path $this$relativeTo, Path base) {
        Intrinsics.checkNotNullParameter($this$relativeTo, "$this$relativeTo");
        Intrinsics.checkNotNullParameter(base, "base");
        try {
            return PathRelativizer.INSTANCE.tryRelativeTo($this$relativeTo, base);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            throw new IllegalArgumentException(Intrinsics.stringPlus(message, "\nthis path: " + $this$relativeTo + "\nbase path: " + base), e);
        }
    }

    public static final Path relativeToOrSelf(Path $this$relativeToOrSelf, Path base) {
        Intrinsics.checkNotNullParameter($this$relativeToOrSelf, "$this$relativeToOrSelf");
        Intrinsics.checkNotNullParameter(base, "base");
        Path relativeToOrNull = PathsKt.relativeToOrNull($this$relativeToOrSelf, base);
        return relativeToOrNull != null ? relativeToOrNull : $this$relativeToOrSelf;
    }

    public static final Path relativeToOrNull(Path $this$relativeToOrNull, Path base) {
        Intrinsics.checkNotNullParameter($this$relativeToOrNull, "$this$relativeToOrNull");
        Intrinsics.checkNotNullParameter(base, "base");
        try {
            return PathRelativizer.INSTANCE.tryRelativeTo($this$relativeToOrNull, base);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    static /* synthetic */ Path copyTo$default(Path $this$copyTo, Path target, boolean overwrite, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            overwrite = false;
        }
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path copy = Files.copy($this$copyTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(copy, "Files.copy(this, target, *options)");
        return copy;
    }

    private static final Path copyTo(Path $this$copyTo, Path target, boolean overwrite) throws IOException {
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path copy = Files.copy($this$copyTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(copy, "Files.copy(this, target, *options)");
        return copy;
    }

    private static final Path copyTo(Path $this$copyTo, Path target, CopyOption... options) throws IOException {
        Path copy = Files.copy($this$copyTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(copy, "Files.copy(this, target, *options)");
        return copy;
    }

    private static final boolean exists(Path $this$exists, LinkOption... options) {
        return Files.exists($this$exists, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean notExists(Path $this$notExists, LinkOption... options) {
        return Files.notExists($this$notExists, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean isRegularFile(Path $this$isRegularFile, LinkOption... options) {
        return Files.isRegularFile($this$isRegularFile, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean isDirectory(Path $this$isDirectory, LinkOption... options) {
        return Files.isDirectory($this$isDirectory, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final boolean isSymbolicLink(Path $this$isSymbolicLink) {
        return Files.isSymbolicLink($this$isSymbolicLink);
    }

    private static final boolean isExecutable(Path $this$isExecutable) {
        return Files.isExecutable($this$isExecutable);
    }

    private static final boolean isHidden(Path $this$isHidden) throws IOException {
        return Files.isHidden($this$isHidden);
    }

    private static final boolean isReadable(Path $this$isReadable) {
        return Files.isReadable($this$isReadable);
    }

    private static final boolean isWritable(Path $this$isWritable) {
        return Files.isWritable($this$isWritable);
    }

    private static final boolean isSameFileAs(Path $this$isSameFileAs, Path other) throws IOException {
        return Files.isSameFile($this$isSameFileAs, other);
    }

    public static /* synthetic */ List listDirectoryEntries$default(Path path, String str, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            str = Subscriber.TOPIC_MESSAGE_TYPE_WILDCARD;
        }
        return PathsKt.listDirectoryEntries(path, str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002d, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0029, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002a, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r0, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.util.List<java.nio.file.Path> listDirectoryEntries(java.nio.file.Path r5, java.lang.String r6) throws java.io.IOException {
        /*
            java.lang.String r0 = "$this$listDirectoryEntries"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            java.lang.String r0 = "glob"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.nio.file.DirectoryStream r0 = java.nio.file.Files.newDirectoryStream(r5, r6)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            r2 = r0
            java.nio.file.DirectoryStream r2 = (java.nio.file.DirectoryStream) r2     // Catch:{ all -> 0x0027 }
            r3 = 0
            java.lang.String r4 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)     // Catch:{ all -> 0x0027 }
            r4 = r2
            java.lang.Iterable r4 = (java.lang.Iterable) r4     // Catch:{ all -> 0x0027 }
            java.util.List r4 = kotlin.collections.CollectionsKt.toList(r4)     // Catch:{ all -> 0x0027 }
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
            return r4
        L_0x0027:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0029 }
        L_0x0029:
            r2 = move-exception
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathUtilsKt.listDirectoryEntries(java.nio.file.Path, java.lang.String):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
        if (kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0) == false) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
        if (r0 != null) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0050, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0053, code lost:
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0056, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.lang.Object useDirectoryEntries$default(java.nio.file.Path r6, java.lang.String r7, kotlin.jvm.functions.Function1 r8, int r9, java.lang.Object r10) throws java.io.IOException {
        /*
            r10 = 1
            r9 = r9 & r10
            if (r9 == 0) goto L_0x0006
            java.lang.String r7 = "*"
        L_0x0006:
            r9 = 0
            java.nio.file.DirectoryStream r0 = java.nio.file.Files.newDirectoryStream(r6, r7)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            r2 = 0
            r3 = r0
            java.nio.file.DirectoryStream r3 = (java.nio.file.DirectoryStream) r3     // Catch:{ all -> 0x003c }
            r4 = 0
            java.lang.String r5 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)     // Catch:{ all -> 0x003c }
            r5 = r3
            java.lang.Iterable r5 = (java.lang.Iterable) r5     // Catch:{ all -> 0x003c }
            kotlin.sequences.Sequence r5 = kotlin.collections.CollectionsKt.asSequence(r5)     // Catch:{ all -> 0x003c }
            java.lang.Object r5 = r8.invoke(r5)     // Catch:{ all -> 0x003c }
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r10, r10, r2)
            if (r2 == 0) goto L_0x0032
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
            goto L_0x0038
        L_0x0032:
            if (r0 != 0) goto L_0x0035
            goto L_0x0038
        L_0x0035:
            r0.close()
        L_0x0038:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            return r5
        L_0x003c:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x003e }
        L_0x003e:
            r3 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r10)
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r10, r10, r2)
            if (r2 != 0) goto L_0x0050
            if (r0 == 0) goto L_0x0053
            r0.close()     // Catch:{ all -> 0x004e }
            goto L_0x0053
        L_0x004e:
            r0 = move-exception
            goto L_0x0053
        L_0x0050:
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
        L_0x0053:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r10)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathUtilsKt.useDirectoryEntries$default(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1, int, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        if (kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        if (r1 != null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004b, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0051, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final <T> T useDirectoryEntries(java.nio.file.Path r8, java.lang.String r9, kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<? extends java.nio.file.Path>, ? extends T> r10) throws java.io.IOException {
        /*
            r0 = 0
            java.nio.file.DirectoryStream r1 = java.nio.file.Files.newDirectoryStream(r8, r9)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = 0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = 0
            r4 = 1
            r5 = r1
            java.nio.file.DirectoryStream r5 = (java.nio.file.DirectoryStream) r5     // Catch:{ all -> 0x0037 }
            r6 = 0
            java.lang.String r7 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r7)     // Catch:{ all -> 0x0037 }
            r7 = r5
            java.lang.Iterable r7 = (java.lang.Iterable) r7     // Catch:{ all -> 0x0037 }
            kotlin.sequences.Sequence r7 = kotlin.collections.CollectionsKt.asSequence(r7)     // Catch:{ all -> 0x0037 }
            java.lang.Object r7 = r10.invoke(r7)     // Catch:{ all -> 0x0037 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            boolean r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3)
            if (r3 == 0) goto L_0x002d
            kotlin.p008io.CloseableKt.closeFinally(r1, r2)
            goto L_0x0033
        L_0x002d:
            if (r1 != 0) goto L_0x0030
            goto L_0x0033
        L_0x0030:
            r1.close()
        L_0x0033:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            return r7
        L_0x0037:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0039 }
        L_0x0039:
            r5 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            boolean r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3)
            if (r3 != 0) goto L_0x004b
            if (r1 == 0) goto L_0x004e
            r1.close()     // Catch:{ all -> 0x0049 }
            goto L_0x004e
        L_0x0049:
            r1 = move-exception
            goto L_0x004e
        L_0x004b:
            kotlin.p008io.CloseableKt.closeFinally(r1, r2)
        L_0x004e:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathUtilsKt.useDirectoryEntries(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004d, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0055, code lost:
        if (kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0) == false) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0057, code lost:
        if (r0 != null) goto L_0x0059;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005f, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0062, code lost:
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0065, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void forEachDirectoryEntry$default(java.nio.file.Path r9, java.lang.String r10, kotlin.jvm.functions.Function1 r11, int r12, java.lang.Object r13) throws java.io.IOException {
        /*
            r13 = 1
            r12 = r12 & r13
            if (r12 == 0) goto L_0x0006
            java.lang.String r10 = "*"
        L_0x0006:
            r12 = 0
            java.nio.file.DirectoryStream r0 = java.nio.file.Files.newDirectoryStream(r9, r10)
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            r2 = 0
            r3 = r0
            java.nio.file.DirectoryStream r3 = (java.nio.file.DirectoryStream) r3     // Catch:{ all -> 0x004b }
            r4 = 0
            java.lang.String r5 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r5)     // Catch:{ all -> 0x004b }
            r5 = r3
            java.lang.Iterable r5 = (java.lang.Iterable) r5     // Catch:{ all -> 0x004b }
            r6 = 0
            java.util.Iterator r7 = r5.iterator()     // Catch:{ all -> 0x004b }
        L_0x0022:
            boolean r8 = r7.hasNext()     // Catch:{ all -> 0x004b }
            if (r8 == 0) goto L_0x0030
            java.lang.Object r8 = r7.next()     // Catch:{ all -> 0x004b }
            r11.invoke(r8)     // Catch:{ all -> 0x004b }
            goto L_0x0022
        L_0x0030:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x004b }
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r13, r13, r2)
            if (r2 == 0) goto L_0x0041
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
            goto L_0x0047
        L_0x0041:
            if (r0 != 0) goto L_0x0044
            goto L_0x0047
        L_0x0044:
            r0.close()
        L_0x0047:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            return
        L_0x004b:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x004d }
        L_0x004d:
            r3 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r13)
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r13, r13, r2)
            if (r2 != 0) goto L_0x005f
            if (r0 == 0) goto L_0x0062
            r0.close()     // Catch:{ all -> 0x005d }
            goto L_0x0062
        L_0x005d:
            r0 = move-exception
            goto L_0x0062
        L_0x005f:
            kotlin.p008io.CloseableKt.closeFinally(r0, r1)
        L_0x0062:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r13)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathUtilsKt.forEachDirectoryEntry$default(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1, int, java.lang.Object):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0048, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0049, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
        if (kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0) == false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0052, code lost:
        if (r1 != null) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005a, code lost:
        kotlin.p008io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005d, code lost:
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0060, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final void forEachDirectoryEntry(java.nio.file.Path r11, java.lang.String r12, kotlin.jvm.functions.Function1<? super java.nio.file.Path, kotlin.Unit> r13) throws java.io.IOException {
        /*
            r0 = 0
            java.nio.file.DirectoryStream r1 = java.nio.file.Files.newDirectoryStream(r11, r12)
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = 0
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            r3 = 0
            r4 = 1
            r5 = r1
            java.nio.file.DirectoryStream r5 = (java.nio.file.DirectoryStream) r5     // Catch:{ all -> 0x0046 }
            r6 = 0
            java.lang.String r7 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r7)     // Catch:{ all -> 0x0046 }
            r7 = r5
            java.lang.Iterable r7 = (java.lang.Iterable) r7     // Catch:{ all -> 0x0046 }
            r8 = 0
            java.util.Iterator r9 = r7.iterator()     // Catch:{ all -> 0x0046 }
        L_0x001d:
            boolean r10 = r9.hasNext()     // Catch:{ all -> 0x0046 }
            if (r10 == 0) goto L_0x002b
            java.lang.Object r10 = r9.next()     // Catch:{ all -> 0x0046 }
            r13.invoke(r10)     // Catch:{ all -> 0x0046 }
            goto L_0x001d
        L_0x002b:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0046 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            boolean r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3)
            if (r3 == 0) goto L_0x003c
            kotlin.p008io.CloseableKt.closeFinally(r1, r2)
            goto L_0x0042
        L_0x003c:
            if (r1 != 0) goto L_0x003f
            goto L_0x0042
        L_0x003f:
            r1.close()
        L_0x0042:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            return
        L_0x0046:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0048 }
        L_0x0048:
            r5 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            boolean r3 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r4, r4, r3)
            if (r3 != 0) goto L_0x005a
            if (r1 == 0) goto L_0x005d
            r1.close()     // Catch:{ all -> 0x0058 }
            goto L_0x005d
        L_0x0058:
            r1 = move-exception
            goto L_0x005d
        L_0x005a:
            kotlin.p008io.CloseableKt.closeFinally(r1, r2)
        L_0x005d:
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p008io.path.PathsKt__PathUtilsKt.forEachDirectoryEntry(java.nio.file.Path, java.lang.String, kotlin.jvm.functions.Function1):void");
    }

    private static final long fileSize(Path $this$fileSize) throws IOException {
        return Files.size($this$fileSize);
    }

    private static final void deleteExisting(Path $this$deleteExisting) throws IOException {
        Files.delete($this$deleteExisting);
    }

    private static final boolean deleteIfExists(Path $this$deleteIfExists) throws IOException {
        return Files.deleteIfExists($this$deleteIfExists);
    }

    private static final Path createDirectory(Path $this$createDirectory, FileAttribute<?>... attributes) throws IOException {
        Path createDirectory = Files.createDirectory($this$createDirectory, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createDirectory, "Files.createDirectory(this, *attributes)");
        return createDirectory;
    }

    private static final Path createDirectories(Path $this$createDirectories, FileAttribute<?>... attributes) throws IOException {
        Path createDirectories = Files.createDirectories($this$createDirectories, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createDirectories, "Files.createDirectories(this, *attributes)");
        return createDirectories;
    }

    private static final Path moveTo(Path $this$moveTo, Path target, CopyOption... options) throws IOException {
        Path move = Files.move($this$moveTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(move, "Files.move(this, target, *options)");
        return move;
    }

    static /* synthetic */ Path moveTo$default(Path $this$moveTo, Path target, boolean overwrite, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            overwrite = false;
        }
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path move = Files.move($this$moveTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(move, "Files.move(this, target, *options)");
        return move;
    }

    private static final Path moveTo(Path $this$moveTo, Path target, boolean overwrite) throws IOException {
        CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[0];
        Path move = Files.move($this$moveTo, target, (CopyOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(move, "Files.move(this, target, *options)");
        return move;
    }

    private static final FileStore fileStore(Path $this$fileStore) throws IOException {
        FileStore fileStore = Files.getFileStore($this$fileStore);
        Intrinsics.checkNotNullExpressionValue(fileStore, "Files.getFileStore(this)");
        return fileStore;
    }

    private static final Object getAttribute(Path $this$getAttribute, String attribute, LinkOption... options) throws IOException {
        return Files.getAttribute($this$getAttribute, attribute, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final Path setAttribute(Path $this$setAttribute, String attribute, Object value, LinkOption... options) throws IOException {
        Path attribute2 = Files.setAttribute($this$setAttribute, attribute, value, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(attribute2, "Files.setAttribute(this,…tribute, value, *options)");
        return attribute2;
    }

    private static final /* synthetic */ <V extends FileAttributeView> V fileAttributesView(Path $this$fileAttributesView, LinkOption... options) {
        Intrinsics.reifiedOperationMarker(4, "V");
        V fileAttributeView = Files.getFileAttributeView($this$fileAttributesView, FileAttributeView.class, (LinkOption[]) Arrays.copyOf(options, options.length));
        if (fileAttributeView != null) {
            return fileAttributeView;
        }
        Intrinsics.reifiedOperationMarker(4, "V");
        PathsKt.fileAttributeViewNotAvailable($this$fileAttributesView, FileAttributeView.class);
        throw new KotlinNothingValueException();
    }

    public static final Void fileAttributeViewNotAvailable(Path path, Class<?> attributeViewClass) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(attributeViewClass, "attributeViewClass");
        throw new UnsupportedOperationException("The desired attribute view type " + attributeViewClass + " is not available for the file " + path + '.');
    }

    private static final /* synthetic */ <A extends BasicFileAttributes> A readAttributes(Path $this$readAttributes, LinkOption... options) throws IOException {
        Intrinsics.reifiedOperationMarker(4, "A");
        A readAttributes = Files.readAttributes($this$readAttributes, BasicFileAttributes.class, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(readAttributes, "Files.readAttributes(thi… A::class.java, *options)");
        return readAttributes;
    }

    private static final Map<String, Object> readAttributes(Path $this$readAttributes, String attributes, LinkOption... options) throws IOException {
        Map<String, Object> readAttributes = Files.readAttributes($this$readAttributes, attributes, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(readAttributes, "Files.readAttributes(this, attributes, *options)");
        return readAttributes;
    }

    private static final FileTime getLastModifiedTime(Path $this$getLastModifiedTime, LinkOption... options) throws IOException {
        FileTime lastModifiedTime = Files.getLastModifiedTime($this$getLastModifiedTime, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(lastModifiedTime, "Files.getLastModifiedTime(this, *options)");
        return lastModifiedTime;
    }

    private static final Path setLastModifiedTime(Path $this$setLastModifiedTime, FileTime value) throws IOException {
        Path lastModifiedTime = Files.setLastModifiedTime($this$setLastModifiedTime, value);
        Intrinsics.checkNotNullExpressionValue(lastModifiedTime, "Files.setLastModifiedTime(this, value)");
        return lastModifiedTime;
    }

    private static final UserPrincipal getOwner(Path $this$getOwner, LinkOption... options) throws IOException {
        return Files.getOwner($this$getOwner, (LinkOption[]) Arrays.copyOf(options, options.length));
    }

    private static final Path setOwner(Path $this$setOwner, UserPrincipal value) throws IOException {
        Path owner = Files.setOwner($this$setOwner, value);
        Intrinsics.checkNotNullExpressionValue(owner, "Files.setOwner(this, value)");
        return owner;
    }

    private static final Set<PosixFilePermission> getPosixFilePermissions(Path $this$getPosixFilePermissions, LinkOption... options) throws IOException {
        Set<PosixFilePermission> posixFilePermissions = Files.getPosixFilePermissions($this$getPosixFilePermissions, (LinkOption[]) Arrays.copyOf(options, options.length));
        Intrinsics.checkNotNullExpressionValue(posixFilePermissions, "Files.getPosixFilePermissions(this, *options)");
        return posixFilePermissions;
    }

    private static final Path setPosixFilePermissions(Path $this$setPosixFilePermissions, Set<? extends PosixFilePermission> value) throws IOException {
        Path posixFilePermissions = Files.setPosixFilePermissions($this$setPosixFilePermissions, value);
        Intrinsics.checkNotNullExpressionValue(posixFilePermissions, "Files.setPosixFilePermissions(this, value)");
        return posixFilePermissions;
    }

    private static final Path createLinkPointingTo(Path $this$createLinkPointingTo, Path target) throws IOException {
        Path createLink = Files.createLink($this$createLinkPointingTo, target);
        Intrinsics.checkNotNullExpressionValue(createLink, "Files.createLink(this, target)");
        return createLink;
    }

    private static final Path createSymbolicLinkPointingTo(Path $this$createSymbolicLinkPointingTo, Path target, FileAttribute<?>... attributes) throws IOException {
        Path createSymbolicLink = Files.createSymbolicLink($this$createSymbolicLinkPointingTo, target, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createSymbolicLink, "Files.createSymbolicLink…his, target, *attributes)");
        return createSymbolicLink;
    }

    private static final Path readSymbolicLink(Path $this$readSymbolicLink) throws IOException {
        Path readSymbolicLink = Files.readSymbolicLink($this$readSymbolicLink);
        Intrinsics.checkNotNullExpressionValue(readSymbolicLink, "Files.readSymbolicLink(this)");
        return readSymbolicLink;
    }

    private static final Path createFile(Path $this$createFile, FileAttribute<?>... attributes) throws IOException {
        Path createFile = Files.createFile($this$createFile, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createFile, "Files.createFile(this, *attributes)");
        return createFile;
    }

    static /* synthetic */ Path createTempFile$default(String prefix, String suffix, FileAttribute[] attributes, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            prefix = null;
        }
        if ((i & 2) != 0) {
            suffix = null;
        }
        Path createTempFile = Files.createTempFile(prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempFile, "Files.createTempFile(prefix, suffix, *attributes)");
        return createTempFile;
    }

    private static final Path createTempFile(String prefix, String suffix, FileAttribute<?>... attributes) throws IOException {
        Path createTempFile = Files.createTempFile(prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempFile, "Files.createTempFile(prefix, suffix, *attributes)");
        return createTempFile;
    }

    public static /* synthetic */ Path createTempFile$default(Path path, String str, String str2, FileAttribute[] fileAttributeArr, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            str2 = null;
        }
        return PathsKt.createTempFile(path, str, str2, fileAttributeArr);
    }

    public static final Path createTempFile(Path directory, String prefix, String suffix, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (directory != null) {
            Path createTempFile = Files.createTempFile(directory, prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(createTempFile, "Files.createTempFile(dir…fix, suffix, *attributes)");
            return createTempFile;
        }
        Path createTempFile2 = Files.createTempFile(prefix, suffix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempFile2, "Files.createTempFile(prefix, suffix, *attributes)");
        return createTempFile2;
    }

    static /* synthetic */ Path createTempDirectory$default(String prefix, FileAttribute[] attributes, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            prefix = null;
        }
        Path createTempDirectory = Files.createTempDirectory(prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempDirectory, "Files.createTempDirectory(prefix, *attributes)");
        return createTempDirectory;
    }

    private static final Path createTempDirectory(String prefix, FileAttribute<?>... attributes) throws IOException {
        Path createTempDirectory = Files.createTempDirectory(prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempDirectory, "Files.createTempDirectory(prefix, *attributes)");
        return createTempDirectory;
    }

    public static /* synthetic */ Path createTempDirectory$default(Path path, String str, FileAttribute[] fileAttributeArr, int i, Object obj) throws IOException {
        if ((i & 2) != 0) {
            str = null;
        }
        return PathsKt.createTempDirectory(path, str, fileAttributeArr);
    }

    public static final Path createTempDirectory(Path directory, String prefix, FileAttribute<?>... attributes) throws IOException {
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        if (directory != null) {
            Path createTempDirectory = Files.createTempDirectory(directory, prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
            Intrinsics.checkNotNullExpressionValue(createTempDirectory, "Files.createTempDirector…ory, prefix, *attributes)");
            return createTempDirectory;
        }
        Path createTempDirectory2 = Files.createTempDirectory(prefix, (FileAttribute[]) Arrays.copyOf(attributes, attributes.length));
        Intrinsics.checkNotNullExpressionValue(createTempDirectory2, "Files.createTempDirectory(prefix, *attributes)");
        return createTempDirectory2;
    }

    private static final Path div(Path $this$div, Path other) {
        Intrinsics.checkNotNullParameter($this$div, "$this$div");
        Path resolve = $this$div.resolve(other);
        Intrinsics.checkNotNullExpressionValue(resolve, "this.resolve(other)");
        return resolve;
    }

    private static final Path div(Path $this$div, String other) {
        Intrinsics.checkNotNullParameter($this$div, "$this$div");
        Path resolve = $this$div.resolve(other);
        Intrinsics.checkNotNullExpressionValue(resolve, "this.resolve(other)");
        return resolve;
    }

    private static final Path Path(String path) {
        Path path2 = Paths.get(path, new String[0]);
        Intrinsics.checkNotNullExpressionValue(path2, "Paths.get(path)");
        return path2;
    }

    private static final Path Path(String base, String... subpaths) {
        Path path = Paths.get(base, (String[]) Arrays.copyOf(subpaths, subpaths.length));
        Intrinsics.checkNotNullExpressionValue(path, "Paths.get(base, *subpaths)");
        return path;
    }

    private static final Path toPath(URI $this$toPath) {
        Path path = Paths.get($this$toPath);
        Intrinsics.checkNotNullExpressionValue(path, "Paths.get(this)");
        return path;
    }
}
