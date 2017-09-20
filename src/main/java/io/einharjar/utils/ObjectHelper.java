package io.einharjar.utils;

public final class ObjectHelper {

    public static void checkNull(Object object){
        if(object == null){
            throw new IllegalArgumentException();
        }
    }

    public static void checkNull(Object object, String message){
        if(object == null){
            throw new IllegalArgumentException(message);
        }
    }
}
