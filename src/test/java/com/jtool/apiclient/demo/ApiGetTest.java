package com.jtool.apiclient.demo;

import com.alibaba.fastjson.JSON;
import com.jtool.apiclient.demo.model.People;
import com.jtool.apiclient.demo.model.ResponsePeople;
import com.jtool.apiclient.exception.StatusCodeNot200Exception;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jtool.apiclient.ApiClient.Api;

public class ApiGetTest {

    @Test
    public void getTest() throws Exception {

        Assert.assertEquals("{}", Api().get("http://chat.palm-chat.cn/TestServer/sentGet"));

        ResponsePeople responsePeople = JSON.parseObject(Api().get("http://chat.palm-chat.cn/TestServer/sentGet?name=中文名"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void getURLEncoderTest() throws Exception {
        People people = new People();
        people.setName("1+1");

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).get("http://chat.palm-chat.cn/TestServer/sentGet"), ResponsePeople.class);
        Assert.assertEquals("1+1", responsePeople.getName());
        Assert.assertNull(responsePeople.getAge());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getHeight());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void getTest2() throws Exception {
        People people = new People();
        people.setName("中文名");
        people.setAge(30);
        people.setHeight(1.73);

        ResponsePeople responsePeople = JSON.parseObject(Api().param(people).get("http://chat.palm-chat.cn/TestServer/sentGet"), ResponsePeople.class);
        Assert.assertEquals("中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(30), responsePeople.getAge());
        Assert.assertEquals(new Double(1.73), responsePeople.getHeight());
        Assert.assertNull(responsePeople.getGallery());
        Assert.assertNull(responsePeople.getArticle());
        Assert.assertNull(responsePeople.getAvatar());
    }

    @Test
    public void getTest3() throws Exception {
        ResponsePeople responsePeople = JSON.parseObject(Api().get("http://chat.palm-chat.cn/TestServer/sentGet?name=中文&age=22&height=1.66"), ResponsePeople.class);
        Assert.assertEquals("中文", responsePeople.getName());
        Assert.assertEquals(new Integer(22), responsePeople.getAge());
        Assert.assertEquals(new Double(1.66), responsePeople.getHeight());
    }

    @Test
    public void getTest4() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "map中文名");
        params.put("age", 31);
        params.put("height", 1.74);

        ResponsePeople responsePeople = JSON.parseObject(Api().param(params).get("http://chat.palm-chat.cn/TestServer/sentGet"), ResponsePeople.class);
        Assert.assertEquals("map中文名", responsePeople.getName());
        Assert.assertEquals(new Integer(31), responsePeople.getAge());
        Assert.assertEquals(new Double(1.74), responsePeople.getHeight());
    }

    @Test(expected= StatusCodeNot200Exception.class)
    public void get404() throws Exception {
        Api().get("http://chat.palm-chat.cn/TestServer/404");
    }

    @Test(expected= IOException.class)
    public void postIoException() throws Exception {
        Api().get("http://xxx.abc");
    }
}