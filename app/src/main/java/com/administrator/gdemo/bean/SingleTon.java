package com.administrator.gdemo.bean;

import java.io.ObjectStreamException;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/30 9:02
 * 修改人：Administrator
 * 修改时间：2016/12/30 9:02
 * 修改备注：
 */
public class SingleTon {
    private static SingleTon SINGLE_TON = null;

    private SingleTon() {
        if (SINGLE_TON != null) {
            throw new RuntimeException("");
        }
    }


    public SingleTon getInstance() {
        if (SINGLE_TON == null) {
            synchronized (SINGLE_TON) {
                SINGLE_TON = new SingleTon();
            }
        }
        return SINGLE_TON;
    }
    // 防止反序列化获取多个对象的漏洞。
    // 无论是实现Serializable接口，或是Externalizable接口，当从I/O流中读取对象时，readResolve()方法都会被调用到。
    // 实际上就是用readResolve()中返回的对象直接替换在反序列化过程中创建的对象。
    private Object readResolve() throws ObjectStreamException {
        return SINGLE_TON;
    }
}
