package com.Nextechbd.bizcards;


public class AppConfig {
    public static String BASE_URL="http://mitechbd.com/card/";

    public static RegistrationApi getAPIService() {

        return Client.getClient(BASE_URL).create(RegistrationApi.class);
    }
    public static Verifyapi getAPIService3() {

        return  Client.getClient(BASE_URL).create(Verifyapi.class);
    }
    public static Loginapi getAPIService7() {

        return  Client.getClient(BASE_URL).create(Loginapi.class);
    }
    public static Saveapi getAPIService10() {

        return  Client.getClient(BASE_URL).create(Saveapi.class);
    }
    public static Showapi getAPIService4() {

        return  Client.getClient(BASE_URL).create(Showapi.class);
    }
    public static creatapi getAPIService11()
    {
        return  Client.getClient(BASE_URL).create(creatapi.class);
    }
    public static Checkapi getAPIService12()
    {
        return  Client.getClient(BASE_URL).create(Checkapi.class);
    }
    public static Profileapi getAPIService13()
    {
        return  Client.getClient(BASE_URL).create(Profileapi.class);
    }
    public static Galaryfront getAPIService14()
    {
        return  Client.getClient(BASE_URL).create(Galaryfront.class);
    }
    public static Updateapi getAPIService15()
    {
        return  Client.getClient(BASE_URL).create(Updateapi.class);
    }
    public static Editapi getAPIService16()
    {
        return  Client.getClient(BASE_URL).create(Editapi.class);
    }
    public static Deleteapi getAPIService17()
    {
        return  Client.getClient(BASE_URL).create(Deleteapi.class);
    }
    /*
    public static ApiConfig getAPIService1() {

        return Client.getClient(BASE_URL).create(ApiConfig.class);
    }

    public static Apiservice getAPIService2() {

        return Client.getClient(BASE_URL).create(Apiservice.class);
    }
    public static Verifyapi getAPIService3() {

        return  Client.getClient(BASE_URL).create(Verifyapi.class);
    }
    public static Showapi getAPIService4() {

        return  Client.getClient(BASE_URL).create(Showapi.class);
    }
    public static Shareapi getAPIService5() {

        return  Client.getClient(BASE_URL).create(Shareapi.class);
    }
    public static Likeapi getAPIService6() {

        return  Client.getClient(BASE_URL).create(Likeapi.class);
    }
    public static Loginapi getAPIService7() {

        return  Client.getClient(BASE_URL).create(Loginapi.class);
    }
    public static Profileapi getAPIService8() {

        return  Client.getClient(BASE_URL).create(Profileapi.class);
    }
    public static Profile_contentapi getAPIService9() {

        return  Client.getClient(BASE_URL).create(Profile_contentapi.class);
    }*/
}
