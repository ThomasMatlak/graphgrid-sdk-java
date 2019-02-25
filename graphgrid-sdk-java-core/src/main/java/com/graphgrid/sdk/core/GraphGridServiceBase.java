package com.graphgrid.sdk.core;

public abstract class GraphGridServiceBase
{

    private GraphGridHttpClient client;

    private String baseUrl;

    public GraphGridServiceBase()
    {
        this.client = new GraphGridHttpClient();
    }

    public GraphGridServiceBase( GraphGridHttpClient client, String baseUrl )
    {
        this.client = client;
        this.baseUrl = baseUrl;
    }

    public GraphGridServiceBase( String baseUrl )
    {
        this.baseUrl = baseUrl;
    }

    public GraphGridHttpClient getClient()
    {
        return client;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }


}
