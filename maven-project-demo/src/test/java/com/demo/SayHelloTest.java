package com.demo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SayHelloTest {
    private SayHello hello;

    @Before
    public void setUp() {
        this.hello = new SayHello();
    }

    @Test
    public void testHello() {
        Assert.assertEquals("hello", this.hello.hello());
    }

    @Test
    public void testHelloWithName() {
        Assert.assertEquals("hello Tom", this.hello.hello("Tom"));
    }
}
