package io.github.dkimitsa.tests;


import org.robovm.pods.adapty.Adapty;
import org.robovm.pods.adapty.AdaptyLogLevel;

public class DemoAdapty {

    // pre-requirements:
    // frameworks required:
    //   - Adapty.framework
    //
    // goals:
    // - check that pod is able to link/run with dependencies
    static {
        Adapty.setLogLevel(AdaptyLogLevel.All);
        Adapty.activate("PUBLIC_SDK_KEY",  "YOUR_USER_ID");
    }

    public static void demo() {
        Adapty.getPaywalls(true, (paywallModels, productModels, adaptyError) -> {
            System.out.println("paywallModels: " + paywallModels);
            System.out.println("productModels: " + productModels);
            System.out.println("adaptyError: " + adaptyError);
        });
        System.out.println("demo");
    }
}
