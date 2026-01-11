package com.google.common.base;

public enum CaseFormat {
    LOWER_HYPHEN(CharMatcher.m11is('-'), "-"),
    LOWER_UNDERSCORE(CharMatcher.m11is('_'), "_"),
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), ""),
    UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), ""),
    UPPER_UNDERSCORE(CharMatcher.m11is('_'), "_");
    
    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    private CaseFormat(CharMatcher wordBoundary2, String wordSeparator2) {
        this.wordBoundary = wordBoundary2;
        this.wordSeparator = wordSeparator2;
    }

    /* renamed from: to */
    public String mo8219to(CaseFormat format, String s) {
        if (format == null) {
            throw null;
        } else if (s == null) {
            throw null;
        } else if (format == this) {
            return s;
        } else {
            int i = C03451.$SwitchMap$com$google$common$base$CaseFormat[ordinal()];
            if (i == 1) {
                int i2 = C03451.$SwitchMap$com$google$common$base$CaseFormat[format.ordinal()];
                if (i2 == 2) {
                    return Ascii.toUpperCase(s);
                }
                if (i2 == 3) {
                    return s.replace('_', '-');
                }
            } else if (i == 2) {
                int i3 = C03451.$SwitchMap$com$google$common$base$CaseFormat[format.ordinal()];
                if (i3 == 1) {
                    return Ascii.toLowerCase(s);
                }
                if (i3 == 3) {
                    return Ascii.toLowerCase(s.replace('_', '-'));
                }
            } else if (i == 3) {
                int i4 = C03451.$SwitchMap$com$google$common$base$CaseFormat[format.ordinal()];
                if (i4 == 1) {
                    return s.replace('-', '_');
                }
                if (i4 == 2) {
                    return Ascii.toUpperCase(s.replace('-', '_'));
                }
            }
            StringBuilder out = null;
            int i5 = 0;
            int j = -1;
            while (true) {
                int indexIn = this.wordBoundary.indexIn(s, j + 1);
                j = indexIn;
                if (indexIn == -1) {
                    break;
                }
                if (i5 == 0) {
                    out = new StringBuilder(s.length() + (this.wordSeparator.length() * 4));
                    out.append(format.normalizeFirstWord(s.substring(i5, j)));
                } else {
                    out.append(format.normalizeWord(s.substring(i5, j)));
                }
                out.append(format.wordSeparator);
                i5 = j + this.wordSeparator.length();
            }
            if (i5 == 0) {
                return format.normalizeFirstWord(s);
            }
            out.append(format.normalizeWord(s.substring(i5)));
            return out.toString();
        }
    }

    /* renamed from: com.google.common.base.CaseFormat$1 */
    static /* synthetic */ class C03451 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$base$CaseFormat = null;

        static {
            int[] iArr = new int[CaseFormat.values().length];
            $SwitchMap$com$google$common$base$CaseFormat = iArr;
            try {
                iArr[CaseFormat.LOWER_UNDERSCORE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$base$CaseFormat[CaseFormat.UPPER_UNDERSCORE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$common$base$CaseFormat[CaseFormat.LOWER_HYPHEN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$common$base$CaseFormat[CaseFormat.LOWER_CAMEL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$common$base$CaseFormat[CaseFormat.UPPER_CAMEL.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private String normalizeFirstWord(String word) {
        if (C03451.$SwitchMap$com$google$common$base$CaseFormat[ordinal()] != 4) {
            return normalizeWord(word);
        }
        return Ascii.toLowerCase(word);
    }

    private String normalizeWord(String word) {
        int i = C03451.$SwitchMap$com$google$common$base$CaseFormat[ordinal()];
        if (i == 1) {
            return Ascii.toLowerCase(word);
        }
        if (i == 2) {
            return Ascii.toUpperCase(word);
        }
        if (i == 3) {
            return Ascii.toLowerCase(word);
        }
        if (i == 4) {
            return firstCharOnlyToUpper(word);
        }
        if (i == 5) {
            return firstCharOnlyToUpper(word);
        }
        throw new RuntimeException("unknown case: " + this);
    }

    private static String firstCharOnlyToUpper(String word) {
        int length = word.length();
        if (length == 0) {
            return word;
        }
        StringBuilder sb = new StringBuilder(length);
        sb.append(Ascii.toUpperCase(word.charAt(0)));
        sb.append(Ascii.toLowerCase(word.substring(1)));
        return sb.toString();
    }
}
