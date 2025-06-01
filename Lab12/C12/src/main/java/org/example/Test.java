package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
adnotarea @Test - ca niste etichete pe care le poti pune pe clase, metode, campuri
                - nu fac nimic
                - pot fi detectate si procesate de alte programe (ex Reflection)
 */

@Retention(RetentionPolicy.RUNTIME) //aceasta adnotare va fi disponibila la runtime
@Target(ElementType.METHOD) //aceasta adnotare poate fi aplicata doar metodelor
public @interface Test {

}
