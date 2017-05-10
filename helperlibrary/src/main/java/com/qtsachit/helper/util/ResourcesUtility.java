package com.qtsachit.helper.util;

import android.content.Context;

/**
 * The class is Created by Sachit on 03/February/2017
 * <p>
 * Description-
 * <p>
 * Additional notes-
 */
public final class ResourcesUtility {

    public static final String POINT = "";
    public static final String R = "R";
    public static final String JOIN = "$";
    public static final String ANIM = "anim";
    public static final String ATTR = "attr";
    public static final String COLOR = "color";
    public static final String DIMEN = "dimen";
    public static final String DRAWABLE = "drawable";
    public static final String ID = "id";
    public static final String LAYOUT = "layout";
    public static final String MENU = "menu";
    public static final String RAW = "raw";
    public static final String STRING = "string";
    public static final String STYLE = "style";
    public static final String STYLEABLE = "styleable";

    /**
     * Don't let anyone instantiate this class.
     */
    private ResourcesUtility() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * This method will give the animation id
     *
     * @param context
     * @param name    name
     */
    public static int getAnim(Context context, String name) {
        try {
            return (Integer) Class
                    .forName(context.getPackageName() + POINT + R + JOIN + ANIM)
                    .getDeclaredField(name).get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method will give the attr id
     *
     * @param context
     * @param name    name
     */
    public static int getAttr(Context context, String name) {
        try {
            return (Integer) Class
                    .forName(context.getPackageName() + POINT + R + JOIN + ATTR)
                    .getDeclaredField(name).get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method will give the color id
     *
     * @param context
     * @param name    name
     */
    public static int getColor(Context context, String name) {
        try {
            return (Integer) Class
                    .forName(
                            context.getPackageName() + POINT + R + JOIN + COLOR)
                    .getDeclaredField(name).get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method will give the dimension id
     *
     * @param context
     * @param name    name
     */
    public static int getDimen(Context context, String name) {
        try {
            return (Integer) Class
                    .forName(
                            context.getPackageName() + POINT + R + JOIN + DIMEN)
                    .getDeclaredField(name).get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method will give the image id
     *
     * @param context
     * @param name    name
     */
    public static int getDrawable(Context context, String name) {
        try {
            return (Integer) Class
                    .forName(
                            context.getPackageName() + POINT + R + JOIN
                                    + DRAWABLE).getDeclaredField(name)
                    .get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * This method will give the id
     *
     * @param context
     * @param name    name
     */
    public static int getId(Context context, String name) {
        try {
            return (Integer) Class
                    .forName(context.getPackageName() + POINT + R + JOIN + ID)
                    .getDeclaredField(name).get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }



}