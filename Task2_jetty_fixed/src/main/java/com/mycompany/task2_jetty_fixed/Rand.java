package com.mycompany.task2_jetty_fixed;

import java.util.concurrent.ThreadLocalRandom;

public class Rand 
{
    public static int getRand(int min, int max)
    {
            return ThreadLocalRandom.current().nextInt(min, max);
    }
}
