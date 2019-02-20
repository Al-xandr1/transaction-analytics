package com.rd.transaction.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class SimpleInsert {

    public static void main(String[] args) throws IOException {

        Configuration config = HBaseConfiguration.create();
        System.out.println("Created config");

        HTable hTable = new HTable(config, "transaction");
        System.out.println("Created HTable");

        Put p = new Put(Bytes.toBytes("row2001"));
        System.out.println("Putted value");

        p.add(Bytes.toBytes("personal"), Bytes.toBytes("name"), Bytes.toBytes("Vivek"));
        System.out.println("Putted Vivek");
//        p.add(Bytes.toBytes("personal"), Bytes.toBytes("age"), Bytes.toBytes("17"));
//        System.out.println("Putted 17");
//        p.add(Bytes.toBytes("contactinfo"), Bytes.toBytes("city"), Bytes.toBytes("Bengaluru"));
//        System.out.println("Putted Bengaluru");
//        p.add(Bytes.toBytes("contactinfo"), Bytes.toBytes("country"), Bytes.toBytes("India"));
//        System.out.println("Putted India");
//        p.add(Bytes.toBytes("contactinfo"), Bytes.toBytes("email"), Bytes.toBytes("vivek@abcd.com"));
//        System.out.println("Putted vivek@abcd.com");
//
        hTable.put(p);
        System.out.println("data inserted successfully");

        hTable.close();
        System.out.println("HTable closed");
    }
}

