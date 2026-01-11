package org.opencv.tracking;

public class TrackerCSRT_Params {
    protected final long nativeObj;

    private static native long TrackerCSRT_Params_0();

    private static native void delete(long j);

    private static native int get_admm_iterations_0(long j);

    private static native int get_background_ratio_0(long j);

    private static native float get_cheb_attenuation_0(long j);

    private static native float get_filter_lr_0(long j);

    private static native float get_gsl_sigma_0(long j);

    private static native int get_histogram_bins_0(long j);

    private static native float get_histogram_lr_0(long j);

    private static native float get_hog_clip_0(long j);

    private static native float get_hog_orientations_0(long j);

    private static native float get_kaiser_alpha_0(long j);

    private static native int get_num_hog_channels_used_0(long j);

    private static native int get_number_of_scales_0(long j);

    private static native float get_padding_0(long j);

    private static native float get_psr_threshold_0(long j);

    private static native float get_scale_lr_0(long j);

    private static native float get_scale_model_max_area_0(long j);

    private static native float get_scale_sigma_factor_0(long j);

    private static native float get_scale_step_0(long j);

    private static native float get_template_size_0(long j);

    private static native boolean get_use_channel_weights_0(long j);

    private static native boolean get_use_color_names_0(long j);

    private static native boolean get_use_gray_0(long j);

    private static native boolean get_use_hog_0(long j);

    private static native boolean get_use_rgb_0(long j);

    private static native boolean get_use_segmentation_0(long j);

    private static native float get_weights_lr_0(long j);

    private static native String get_window_function_0(long j);

    private static native void set_admm_iterations_0(long j, int i);

    private static native void set_background_ratio_0(long j, int i);

    private static native void set_cheb_attenuation_0(long j, float f);

    private static native void set_filter_lr_0(long j, float f);

    private static native void set_gsl_sigma_0(long j, float f);

    private static native void set_histogram_bins_0(long j, int i);

    private static native void set_histogram_lr_0(long j, float f);

    private static native void set_hog_clip_0(long j, float f);

    private static native void set_hog_orientations_0(long j, float f);

    private static native void set_kaiser_alpha_0(long j, float f);

    private static native void set_num_hog_channels_used_0(long j, int i);

    private static native void set_number_of_scales_0(long j, int i);

    private static native void set_padding_0(long j, float f);

    private static native void set_psr_threshold_0(long j, float f);

    private static native void set_scale_lr_0(long j, float f);

    private static native void set_scale_model_max_area_0(long j, float f);

    private static native void set_scale_sigma_factor_0(long j, float f);

    private static native void set_scale_step_0(long j, float f);

    private static native void set_template_size_0(long j, float f);

    private static native void set_use_channel_weights_0(long j, boolean z);

    private static native void set_use_color_names_0(long j, boolean z);

    private static native void set_use_gray_0(long j, boolean z);

    private static native void set_use_hog_0(long j, boolean z);

    private static native void set_use_rgb_0(long j, boolean z);

    private static native void set_use_segmentation_0(long j, boolean z);

    private static native void set_weights_lr_0(long j, float f);

    private static native void set_window_function_0(long j, String str);

    protected TrackerCSRT_Params(long addr) {
        this.nativeObj = addr;
    }

    public long getNativeObjAddr() {
        return this.nativeObj;
    }

    public static TrackerCSRT_Params __fromPtr__(long addr) {
        return new TrackerCSRT_Params(addr);
    }

    public TrackerCSRT_Params() {
        this.nativeObj = TrackerCSRT_Params_0();
    }

    public boolean get_use_hog() {
        return get_use_hog_0(this.nativeObj);
    }

    public void set_use_hog(boolean use_hog) {
        set_use_hog_0(this.nativeObj, use_hog);
    }

    public boolean get_use_color_names() {
        return get_use_color_names_0(this.nativeObj);
    }

    public void set_use_color_names(boolean use_color_names) {
        set_use_color_names_0(this.nativeObj, use_color_names);
    }

    public boolean get_use_gray() {
        return get_use_gray_0(this.nativeObj);
    }

    public void set_use_gray(boolean use_gray) {
        set_use_gray_0(this.nativeObj, use_gray);
    }

    public boolean get_use_rgb() {
        return get_use_rgb_0(this.nativeObj);
    }

    public void set_use_rgb(boolean use_rgb) {
        set_use_rgb_0(this.nativeObj, use_rgb);
    }

    public boolean get_use_channel_weights() {
        return get_use_channel_weights_0(this.nativeObj);
    }

    public void set_use_channel_weights(boolean use_channel_weights) {
        set_use_channel_weights_0(this.nativeObj, use_channel_weights);
    }

    public boolean get_use_segmentation() {
        return get_use_segmentation_0(this.nativeObj);
    }

    public void set_use_segmentation(boolean use_segmentation) {
        set_use_segmentation_0(this.nativeObj, use_segmentation);
    }

    public String get_window_function() {
        return get_window_function_0(this.nativeObj);
    }

    public void set_window_function(String window_function) {
        set_window_function_0(this.nativeObj, window_function);
    }

    public float get_kaiser_alpha() {
        return get_kaiser_alpha_0(this.nativeObj);
    }

    public void set_kaiser_alpha(float kaiser_alpha) {
        set_kaiser_alpha_0(this.nativeObj, kaiser_alpha);
    }

    public float get_cheb_attenuation() {
        return get_cheb_attenuation_0(this.nativeObj);
    }

    public void set_cheb_attenuation(float cheb_attenuation) {
        set_cheb_attenuation_0(this.nativeObj, cheb_attenuation);
    }

    public float get_template_size() {
        return get_template_size_0(this.nativeObj);
    }

    public void set_template_size(float template_size) {
        set_template_size_0(this.nativeObj, template_size);
    }

    public float get_gsl_sigma() {
        return get_gsl_sigma_0(this.nativeObj);
    }

    public void set_gsl_sigma(float gsl_sigma) {
        set_gsl_sigma_0(this.nativeObj, gsl_sigma);
    }

    public float get_hog_orientations() {
        return get_hog_orientations_0(this.nativeObj);
    }

    public void set_hog_orientations(float hog_orientations) {
        set_hog_orientations_0(this.nativeObj, hog_orientations);
    }

    public float get_hog_clip() {
        return get_hog_clip_0(this.nativeObj);
    }

    public void set_hog_clip(float hog_clip) {
        set_hog_clip_0(this.nativeObj, hog_clip);
    }

    public float get_padding() {
        return get_padding_0(this.nativeObj);
    }

    public void set_padding(float padding) {
        set_padding_0(this.nativeObj, padding);
    }

    public float get_filter_lr() {
        return get_filter_lr_0(this.nativeObj);
    }

    public void set_filter_lr(float filter_lr) {
        set_filter_lr_0(this.nativeObj, filter_lr);
    }

    public float get_weights_lr() {
        return get_weights_lr_0(this.nativeObj);
    }

    public void set_weights_lr(float weights_lr) {
        set_weights_lr_0(this.nativeObj, weights_lr);
    }

    public int get_num_hog_channels_used() {
        return get_num_hog_channels_used_0(this.nativeObj);
    }

    public void set_num_hog_channels_used(int num_hog_channels_used) {
        set_num_hog_channels_used_0(this.nativeObj, num_hog_channels_used);
    }

    public int get_admm_iterations() {
        return get_admm_iterations_0(this.nativeObj);
    }

    public void set_admm_iterations(int admm_iterations) {
        set_admm_iterations_0(this.nativeObj, admm_iterations);
    }

    public int get_histogram_bins() {
        return get_histogram_bins_0(this.nativeObj);
    }

    public void set_histogram_bins(int histogram_bins) {
        set_histogram_bins_0(this.nativeObj, histogram_bins);
    }

    public float get_histogram_lr() {
        return get_histogram_lr_0(this.nativeObj);
    }

    public void set_histogram_lr(float histogram_lr) {
        set_histogram_lr_0(this.nativeObj, histogram_lr);
    }

    public int get_background_ratio() {
        return get_background_ratio_0(this.nativeObj);
    }

    public void set_background_ratio(int background_ratio) {
        set_background_ratio_0(this.nativeObj, background_ratio);
    }

    public int get_number_of_scales() {
        return get_number_of_scales_0(this.nativeObj);
    }

    public void set_number_of_scales(int number_of_scales) {
        set_number_of_scales_0(this.nativeObj, number_of_scales);
    }

    public float get_scale_sigma_factor() {
        return get_scale_sigma_factor_0(this.nativeObj);
    }

    public void set_scale_sigma_factor(float scale_sigma_factor) {
        set_scale_sigma_factor_0(this.nativeObj, scale_sigma_factor);
    }

    public float get_scale_model_max_area() {
        return get_scale_model_max_area_0(this.nativeObj);
    }

    public void set_scale_model_max_area(float scale_model_max_area) {
        set_scale_model_max_area_0(this.nativeObj, scale_model_max_area);
    }

    public float get_scale_lr() {
        return get_scale_lr_0(this.nativeObj);
    }

    public void set_scale_lr(float scale_lr) {
        set_scale_lr_0(this.nativeObj, scale_lr);
    }

    public float get_scale_step() {
        return get_scale_step_0(this.nativeObj);
    }

    public void set_scale_step(float scale_step) {
        set_scale_step_0(this.nativeObj, scale_step);
    }

    public float get_psr_threshold() {
        return get_psr_threshold_0(this.nativeObj);
    }

    public void set_psr_threshold(float psr_threshold) {
        set_psr_threshold_0(this.nativeObj, psr_threshold);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        delete(this.nativeObj);
    }
}
