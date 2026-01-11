package com.google.common.p007io;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/* renamed from: com.google.common.io.PatternFilenameFilter */
public final class PatternFilenameFilter implements FilenameFilter {
    private final Pattern pattern;

    public PatternFilenameFilter(String patternStr) {
        this(Pattern.compile(patternStr));
    }

    public PatternFilenameFilter(Pattern pattern2) {
        this.pattern = (Pattern) Preconditions.checkNotNull(pattern2);
    }

    public boolean accept(@Nullable File dir, String fileName) {
        return this.pattern.matcher(fileName).matches();
    }
}
