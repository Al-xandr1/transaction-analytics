package com.rd.transaction.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class SimpleRead {

    public static void main(String[] args) throws Exception {

        Configuration config = HBaseConfiguration.create();
        HTable table = new HTable(config, "employee");

        // instantiate Get class
        Get g = new Get(Bytes.toBytes("row2001"));

        // get the Result object
        Result result = table.get(g);

        // read values from Result class object
        byte[] name = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("name"));
        byte[] age = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("age"));
        byte[] city = result.getValue(Bytes.toBytes("contactinfo"), Bytes.toBytes("city"));
        byte[] country = result.getValue(Bytes.toBytes("contactinfo"), Bytes.toBytes("country"));
        byte[] email = result.getValue(Bytes.toBytes("contactinfo"), Bytes.toBytes("email"));

        System.out.println("name: " + Bytes.toString(name));
        System.out.println("age: " + Bytes.toString(age));
        System.out.println("city: " + Bytes.toString(city));
        System.out.println("country: " + Bytes.toString(country));
        System.out.println("email: " + Bytes.toString(email));
    }
}
