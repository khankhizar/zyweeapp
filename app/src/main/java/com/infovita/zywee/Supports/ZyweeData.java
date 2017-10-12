package com.infovita.zywee.Supports;

import android.net.Uri;

/**
 * Created by Khizarkhan on 02-02-2017.
 */
public class ZyweeData {
    public static int _p_c_id;

    public static Uri _prescription_image_cart_uri;

    public ZyweeData() {
    }

    //    Prescription Cart data
    public ZyweeData(int _p_c_id, Uri _prescription_image_cart_uri) {
        this._p_c_id = _p_c_id;
        this._prescription_image_cart_uri = _prescription_image_cart_uri;
    }

    public void setPrescriptionImageUri(Uri _prescription_image_cart_uri) {
        this._prescription_image_cart_uri = _prescription_image_cart_uri;
    }

    public Uri getPrescriptionImageUri() {
        return this._prescription_image_cart_uri;
    }

    /**
     * Manual entry data details
     */
    public static String _manual_description;
    public static String _manual_quantity;
    public static String _manual_notes;
    public static String _tag;

    public ZyweeData(String _tag, String _manual_description, String _manual_quantity, String _manual_notes) {
        this._manual_description = _manual_description;
        this._manual_quantity = _manual_quantity;
        this._manual_notes = _manual_notes;
        this._tag = _tag;
    }

    public static String get_tag() {
        return _tag;
    }

    public String get_manual_description() {
        return _manual_description;
    }

    public String get_manual_quantity() {
        return _manual_quantity;
    }

    public String get_manual_notes() {
        return _manual_notes;
    }

}
