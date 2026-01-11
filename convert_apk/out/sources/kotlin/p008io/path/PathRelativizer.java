package kotlin.p008io.path;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(mo11628d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, mo11629d2 = {"Lkotlin/io/path/PathRelativizer;", "", "()V", "emptyPath", "Ljava/nio/file/Path;", "kotlin.jvm.PlatformType", "parentPath", "tryRelativeTo", "path", "base", "kotlin-stdlib-jdk7"}, mo11630k = 1, mo11631mv = {1, 5, 1})
/* renamed from: kotlin.io.path.PathRelativizer */
/* compiled from: PathUtils.kt */
final class PathRelativizer {
    public static final PathRelativizer INSTANCE = new PathRelativizer();
    private static final Path emptyPath = Paths.get("", new String[0]);
    private static final Path parentPath = Paths.get("..", new String[0]);

    private PathRelativizer() {
    }

    public final Path tryRelativeTo(Path path, Path base) {
        Path path2;
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(base, "base");
        Path bn = base.normalize();
        Path pn = path.normalize();
        Path rn = bn.relativize(pn);
        Intrinsics.checkNotNullExpressionValue(bn, "bn");
        int nameCount = bn.getNameCount();
        Intrinsics.checkNotNullExpressionValue(pn, "pn");
        int min = Math.min(nameCount, pn.getNameCount());
        int i = 0;
        while (i < min && !(!Intrinsics.areEqual((Object) bn.getName(i), (Object) parentPath))) {
            if (!(!Intrinsics.areEqual((Object) pn.getName(i), (Object) parentPath))) {
                i++;
            } else {
                throw new IllegalArgumentException("Unable to compute relative path");
            }
        }
        if (!(!Intrinsics.areEqual((Object) pn, (Object) bn)) || !Intrinsics.areEqual((Object) bn, (Object) emptyPath)) {
            String rnString = rn.toString();
            Intrinsics.checkNotNullExpressionValue(rn, "rn");
            FileSystem fileSystem = rn.getFileSystem();
            Intrinsics.checkNotNullExpressionValue(fileSystem, "rn.fileSystem");
            String separator = fileSystem.getSeparator();
            Intrinsics.checkNotNullExpressionValue(separator, "rn.fileSystem.separator");
            if (StringsKt.endsWith$default(rnString, separator, false, 2, (Object) null)) {
                FileSystem fileSystem2 = rn.getFileSystem();
                FileSystem fileSystem3 = rn.getFileSystem();
                Intrinsics.checkNotNullExpressionValue(fileSystem3, "rn.fileSystem");
                path2 = fileSystem2.getPath(StringsKt.dropLast(rnString, fileSystem3.getSeparator().length()), new String[0]);
            } else {
                path2 = rn;
            }
        } else {
            path2 = pn;
        }
        Path r = path2;
        Intrinsics.checkNotNullExpressionValue(r, "r");
        return r;
    }
}
