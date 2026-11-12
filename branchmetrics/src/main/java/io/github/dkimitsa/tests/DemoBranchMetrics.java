package io.github.dkimitsa.tests;

import org.robovm.pods.branchmetrics.Branch;

public class DemoBranchMetrics {
    public static void demo() {
        double version = Branch.getVersionNumber();
        System.out.println("Branch SDK version: " + version);
    }
}
