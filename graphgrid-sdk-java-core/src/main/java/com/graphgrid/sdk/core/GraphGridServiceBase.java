package com.graphgrid.sdk.core;

public abstract class GraphGridServiceBase
{
    // todo class should hold security config

    private GraphGridHttpClient client;

    private String baseUrl;

    @Deprecated
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
        this.client = new GraphGridHttpClient();
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
