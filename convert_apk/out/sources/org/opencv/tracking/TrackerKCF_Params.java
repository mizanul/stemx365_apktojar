package org.opencv.tracking;

public class TrackerKCF_Params {
    protected final long nativeObj;

    private static native long TrackerKCF_Params_0();

    private static native void delete(long j);

    private static native boolean get_compress_feature_0(long j);

    private static native int get_compressed_size_0(long j);

    private static native int get_desc_npca_0(long j);

    private static native int get_desc_pca_0(long j);

    private static native float get_detect_thresh_0(long j);

    private static native float get_interp_factor_0(long j);

    private static native float get_lambda_0(long j);

    private static native int get_max_patch_size_0(long j);

    private static native float get_output_sigma_factor_0(long j);

    private static native float get_pca_learning_rate_0(long j);

    private static native boolean get_resize_0(long j);

    private static native float get_sigma_0(long j);

    private static native boolean get_split_coeff_0(long j);

    private static native boolean get_wrap_kernel_0(long j);

    private static native void set_compress_feature_0(long j, boolean z);

    private static native void set_compressed_size_0(long j, int i);

    private static native void set_desc_npca_0(long j, int i);

    private static native void set_desc_pca_0(long j, int i);

    private static native void set_detect_thresh_0(long j, float f);

    private static native void set_interp_factor_0(long j, float f);

    private static native void set_lambda_0(long j, float f);

    private static native void set_max_patch_size_0(long j, int i);

    private static native void set_output_sigma_factor_0(long j, float f);

    private static native void set_pca_learning_rate_0(long j, float f);

    private static native void set_resize_0(long j, boolean z);

    private static native void set_sigma_0(long j, float f);

    private static native void set_split_coeff_0(long j, boolean z);

    private static native void set_wrap_kernel_0(long j, boolean z);

    protected TrackerKCF_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static TrackerKCF_Params __fromPtr__(long addr) {
        return new TrackerKCF_Params(addr);
    }

    public TrackerKCF_Params() {
        this.nativeObj = TrackerKCF_Params_0();
    }

    public float get_detect_thresh() {
        return get_detect_thresh_0(this.nativeObj);
    }

    public void set_detect_thresh(float detect_thresh) {
        set_detect_thresh_0(this.nativeObj, detect_thresh);
    }

    public float get_sigma() {
        return get_sigma_0(this.nativeObj);
    }

    public void set_sigma(float sigma) {
        set_sigma_0(this.nativeObj, sigma);
    }

    public float get_lambda() {
        return get_lambda_0(this.nativeObj);
    }

    public void set_lambda(float lambda) {
        set_lambda_0(this.nativeObj, lambda);
    }

    public float get_interp_factor() {
        return get_interp_factor_0(this.nativeObj);
    }

    public void set_interp_factor(float interp_factor) {
        set_interp_factor_0(this.nativeObj, interp_factor);
    }

    public float get_output_sigma_factor() {
        return get_output_sigma_factor_0(this.nativeObj);
    }

    public void set_output_sigma_factor(float output_sigma_factor) {
        set_output_sigma_factor_0(this.nativeObj, output_sigma_factor);
    }

    public float get_pca_learning_rate() {
        return get_pca_learning_rate_0(this.nativeObj);
    }

    public void set_pca_learning_rate(float pca_learning_rate) {
        set_pca_learning_rate_0(this.nativeObj, pca_learning_rate);
    }

    public boolean get_resize() {
        return get_resize_0(this.nativeObj);
    }

    public void set_resize(boolean resize) {
        set_resize_0(this.nativeObj, resize);
    }

    public boolean get_split_coeff() {
        return get_split_coeff_0(this.nativeObj);
    }

    public void set_split_coeff(boolean split_coeff) {
        set_split_coeff_0(this.nativeObj, split_coeff);
    }

    public boolean get_wrap_kernel() {
        return get_wrap_kernel_0(this.nativeObj);
    }

    public void set_wrap_kernel(boolean wrap_kernel) {
        set_wrap_kernel_0(this.nativeObj, wrap_kernel);
    }

    public boolean get_compress_feature() {
        return get_compress_feature_0(this.nativeObj);
    }

    public void set_compress_feature(boolean compress_feature) {
        set_compress_feature_0(this.nativeObj, compress_feature);
    }

    public int get_max_patch_size() {
        return get_max_patch_size_0(this.nativeObj);
    }

    public void set_max_patch_size(int max_patch_size) {
        set_max_patch_size_0(this.nativeObj, max_patch_size);
    }

    public int get_compressed_size() {
        return get_compressed_size_0(this.nativeObj);
    }

    public void set_compressed_size(int compressed_size) {
        set_compressed_size_0(this.nativeObj, compressed_size);
    }

    public int get_desc_pca() {
        return get_desc_pca_0(this.nativeObj);
    }

    public void set_desc_pca(int desc_pca) {
        set_desc_pca_0(this.nativeObj, desc_pca);
    }

    public int get_desc_npca() {
        return get_desc_npca_0(this.nativeObj);
    }

    public void set_desc_npca(int desc_npca) {
        set_desc_npca_0(this.nativeObj, desc_npca);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
